package com.sk.zl.controller;


import com.sk.zl.model.meter.MeterInfo;
import com.sk.zl.model.meter.MeterRate;
import com.sk.zl.model.meter.NewName;
import com.sk.zl.model.meter.ReNewName;
import com.sk.zl.model.plant.PlantState;
import com.sk.zl.model.request.ReBasic;
import com.sk.zl.model.request.ReMeterRate;
import com.sk.zl.model.request.RePlanPower;
import com.sk.zl.model.request.RePlantMaintaing;
import com.sk.zl.model.result.RespCode;
import com.sk.zl.model.station.LoginLog;
import com.sk.zl.model.station.PlanPower;
import com.sk.zl.model.result.ResultBean;
import com.sk.zl.service.PlantService;
import com.sk.zl.utils.ResultBeanUtil;
import com.sk.zl.service.StationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description : 系统设置功能，主要负责静态信息的录入和获取
 * @Author : Ellie
 * @Date : 2019/2/25
 */
@Api("系统设置接口")
@RestController
public class SettingController {
    @Resource
    private StationService stationService;

    @Resource
    private PlantService plantService;

    @ApiOperation(value = "机组检修状态设置和更新")
    @RequestMapping(value = "/checkState",  method = RequestMethod.POST)
    public ResultBean<List<PlantState>> operatePlanPowerInfo(@RequestBody RePlantMaintaing rePlantMaintaing) {
        String type = rePlantMaintaing.getType();
        if (type.equals("get")) {
            /** 机组检修状态信息获取 */
            return ResultBeanUtil.makeOkResp(plantService.getPlantsState());

        } else if (type.equals("update")) {
            /** 机组检修状态更新 */
            List<PlantState> plantStates = rePlantMaintaing.getPlantStates();
            return ResultBeanUtil.makeOkResp(plantService.updatePlantsState(plantStates));

        } else {
            return ResultBeanUtil.makeParamErrResp();
        }
    }

    @ApiOperation(value = "添加系统日志")
    @RequestMapping(value = "/loginLog",  method = RequestMethod.POST)
    public ResultBean<List<LoginLog>> addLoginLog(@RequestBody LoginLog log) {
        String type = log.getType();
        if (type.equals("add")) {
            stationService.addLog(log);
            return ResultBeanUtil.makeOkResp(Arrays.asList(log));
        } else if (type.equals("get")) {
            return ResultBeanUtil.makeOkResp(stationService.getLog(log));
        } else {
            return ResultBeanUtil.makeParamErrResp();
        }
    }

    @ApiOperation(value = "电表属性信息获取")
    @RequestMapping(value = "/rate/info",  method = RequestMethod.POST)
    public ResultBean<List<MeterInfo>> getMeterInfo(@RequestBody ReBasic reBasic) {
        String type = reBasic.getType();
        if (type.equals("get")) {
            return ResultBeanUtil.makeOkResp(stationService.getMeterInfo());
        } else {
            return ResultBeanUtil.makeParamErrResp();
        }
    }

    @ApiOperation(value = "电表名称修改")
    @RequestMapping(value = "/meter/newname",  method = RequestMethod.POST)
    public ResultBean<ReNewName> meterRename(@RequestBody NewName newName) {
        ReNewName name = stationService.setMeterName(newName.getId(), newName.getNewName());
        if (name == null) {
            return ResultBeanUtil.makeResp(RespCode.FAIL.getCode(), "id不存在。");
        }
        return ResultBeanUtil.makeOkResp(name);
    }

    @ApiOperation(value = "电表分组名称修改")
    @RequestMapping(value = "/meterGroup/newname",  method = RequestMethod.POST)
    public ResultBean<ReNewName> meterGroupRename(@RequestBody NewName newName) {
        ReNewName name = stationService.setMeterGroupName(newName.getId(), newName.getNewName());
        if (name == null) {
            return ResultBeanUtil.makeResp(RespCode.FAIL.getCode(), "id不存在。");
        }
        return ResultBeanUtil.makeOkResp(name);
    }

    @ApiOperation(value = "电表倍率值修改")
    @RequestMapping(value = "/rate/value",  method = RequestMethod.POST)
    public ResultBean<List<MeterRate>> operateMeterRate(@RequestBody ReMeterRate reMeterRate) {
        String type = reMeterRate.getType();
        /** 获取电表倍率信息 */
        if (type.equals("get")) {
            int meterId = reMeterRate.getMeterId();
            return ResultBeanUtil.makeOkResp(stationService.getMeterRate(meterId));

        } else if (type.equals("add")) {
            /** 添加电表倍率 */
            /** 电表信息检查 */
            int meterId = reMeterRate.getMeterId();
            List<MeterRate> meterRates = reMeterRate.getRates();
            if (!stationService.checkValidMeterId(meterId)) {
                return ResultBeanUtil.makeResp(-1, "id="+ meterId + "电表不存在。", meterRates);
            }

            /** 冲突检查 */
            List<MeterRate> conflictRates = stationService.checkRateConflicts(meterId, meterRates);
            if (conflictRates.size() != 0) {
                return ResultBeanUtil.makeResp(-2, "电表倍率有效期存在冲突。", conflictRates);
            }

            List<MeterRate> newRates = stationService.addMeterRate(meterId, meterRates);
            return ResultBeanUtil.makeOkResp(newRates);

        } else if (type.equals("update")) {
            /** 更新电表倍率 */
            List<MeterRate> original = reMeterRate.getRates();
            if (original.size() == 0) {
                return ResultBeanUtil.makeResp(RespCode.SUCCESS.getCode(),"参数为空，无更新。");
            }

            /** id检查 */
            List<MeterRate> invalidRates = stationService.checkValidRate(original);
            if (invalidRates.size() != 0) {
                return ResultBeanUtil.makeResp(-1, "电表倍率id不存在。", invalidRates);
            }

            /** 记录更新时间检查 */
            List<MeterRate> updatedRates = stationService.checkRateUpdateTime(original);
            if (updatedRates.size() != 0) {
                return ResultBeanUtil.makeResp(-3, "倍率信息已发生变化，请重新获取最新倍率。", updatedRates);
            }

            /** 冲突检查 */
            int meterId = stationService.getMeterIdForRate(original);
            List<MeterRate> conflictRates = stationService.checkRateConflicts(meterId, original);
            if (conflictRates.size() != 0) {
                return ResultBeanUtil.makeResp(-2, "电表倍率有效期存在冲突。", conflictRates);
            }

            List<MeterRate> newRates = stationService.updateMeterRate(meterId, original);
            return ResultBeanUtil.makeOkResp(newRates);

        } else if (type.equals("delete")) {
            /** 删除电表倍率 */
            List<MeterRate> meterRates = reMeterRate.getRates();
            return ResultBeanUtil.makeOkResp(stationService.deleteMeterRate(meterRates));

        } else {
            return ResultBeanUtil.makeParamErrResp();
        }
    }


    @ApiOperation(value = "计划电量CURD")
    @RequestMapping(value = "/planPower",  method = RequestMethod.POST)
    public ResultBean<List<PlanPower>> operatePlanPower(@RequestBody RePlanPower rePlanPower) {
        String type = rePlanPower.getType();
        if (type.equals("get")) {
            /** 获取计划电量信息 */
            return  ResultBeanUtil.makeOkResp(stationService.getPlanPower());

        } else if (type.equals("add")) {
            /** 添加计划电量 */
            List<PlanPower> planPowers = rePlanPower.getPlanPowerList();
            return ResultBeanUtil.makeOkResp(stationService.addPlanPowers(planPowers));

        } else if (type.equals("update")) {
            /** 更新计划电量 */
            List<PlanPower> planPowers = rePlanPower.getPlanPowerList();
            return ResultBeanUtil.makeOkResp(stationService.updatePlanPowers(planPowers));

        } else if (type.equals("delete")) {
            /** 删除计划电量 */
            List<PlanPower> planPowers = rePlanPower.getPlanPowerList();
            return ResultBeanUtil.makeOkResp(stationService.deletePlanPowers(planPowers));

        } else {
            return ResultBeanUtil.makeParamErrResp();
        }
    }
}
