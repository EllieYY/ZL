package com.sk.zl.controller;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sk.zl.model.meter.Meter;
import com.sk.zl.model.meter.MeterRate;
import com.sk.zl.model.setting.LoginLog;
import com.sk.zl.model.setting.PlanPower;
import com.sk.zl.model.setting.Plant;
import com.sk.zl.model.req.RespEntity;
import com.sk.zl.model.req.RespUtil;
import com.sk.zl.service.SettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
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
    SettingService settingService;

    @ApiOperation(value = "机组检修状态设置和更新")
    @RequestMapping(value = "/checkState",  method = RequestMethod.POST)
    public RespEntity<List<Plant>> operatePlanPowerInfo(@RequestBody String jsonStr) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(jsonStr);
            JsonNode typeNode = rootNode.path("type");
            String type = typeNode.asText();
            if (type.equals("get")) {
                List<Plant> plants = settingService.getPlantInfo();
                return RespUtil.makeOkResp(plants);
            } else if (type.equals("update")) {
                int id = rootNode.path("id").intValue();
                Byte state = Byte.valueOf(rootNode.path("state").asText());
                settingService.updatePlants(id, state);
                return RespUtil.makeOkResp();
            } else {
                return RespUtil.makeParamErrResp();
            }

        } catch (IOException e) {
            e.printStackTrace();
            RespUtil.makeInnerErrResp();
        }

        return RespUtil.makeOkResp();
    }

    @ApiOperation(value = "添加系统日志")
    @RequestMapping(value = "/loginLog",  method = RequestMethod.POST)
    public RespEntity<LoginLog> addLoginLog(@RequestBody String jsonStr) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(jsonStr);
            String user = rootNode.path("user").asText();
            String group = rootNode.path("group").asText();
            Timestamp timestamp = new Timestamp(rootNode.path("loginTime").asLong());

            LoginLog log = new LoginLog();
            log.setUser(user);
            log.setGroup(group);
            log.setLoginTime(timestamp);

            settingService.addLog(log);

            return RespUtil.makeOkResp(log);
        } catch (Exception e) {
            e.printStackTrace();
            RespUtil.makeInnerErrResp();
        }

        return RespUtil.makeOkResp();
    }

    @ApiOperation(value = "电表属性信息获取")
    @RequestMapping(value = "/rate/info",  method = RequestMethod.POST)
    public RespEntity<List<Meter>> getMeterInfo(@RequestBody String jsonStr) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(jsonStr);
            String type = rootNode.path("type").asText();
            if (type.equals("get")) {
                List<Meter> meters = settingService.getMeterInfo();
                return RespUtil.makeOkResp(meters);
            } else {
                return RespUtil.makeParamErrResp();
            }

        } catch (Exception e) {
            e.printStackTrace();
            RespUtil.makeInnerErrResp();
        }

        return RespUtil.makeOkResp();
    }

    @ApiOperation(value = "电表倍率值修改")
    @RequestMapping(value = "/rate/value",  method = RequestMethod.POST)
    public RespEntity<List<MeterRate>> operateMeterRate(@RequestBody String jsonStr) {
        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            JsonNode rootNode = mapper.readTree(jsonStr);
            String type = rootNode.path("type").asText();
            /** 获取电表倍率信息 */
            if (type.equals("get")) {
                int id = rootNode.path("id").asInt();
                List<MeterRate> meterRates = settingService.getMeterRate(id);
                return RespUtil.makeOkResp(meterRates);


            } else if (type.equals("add")) {   /** 添加电表倍率 */
                int meterId = rootNode.path("id").asInt();
                String rates = rootNode.path("rate").toString();
                JavaType jt = mapper.getTypeFactory().constructParametricType(ArrayList.class, MeterRate.class);
                List<MeterRate> meterRates = mapper.readValue(rates, jt);

                settingService.addMeterRate(meterId, meterRates);
                return RespUtil.makeOkResp();

            } else if (type.equals("update")) {  /** 更新电表倍率 */
                MeterRate rate = mapper.readValue(rootNode.toString(), MeterRate.class);
                settingService.updateMeterRate(rate);
                return RespUtil.makeOkResp();

            } else if (type.equals("delete")) {  /** 删除电表倍率 */
                int rateId = rootNode.path("id").asInt();
                settingService.deleteMeterRate(rateId);
                return RespUtil.makeOkResp();
            } else {
                return RespUtil.makeParamErrResp();
            }

        } catch (Exception e) {
            e.printStackTrace();
            RespUtil.makeInnerErrResp();
        }

        return RespUtil.makeOkResp();
    }


    @ApiOperation(value = "计划电量CURD")
    @RequestMapping(value = "/planPower",  method = RequestMethod.POST)
    public RespEntity<List<PlanPower>> operatePlanPower(@RequestBody String jsonStr) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(jsonStr);
            String type = rootNode.path("type").asText();

            if (type.equals("get")) {   /** 获取计划电量信息 */
                List<PlanPower> rePlanPowers = settingService.getPlanPower();
                return RespUtil.makeOkResp(rePlanPowers);

            } else if (type.equals("add")) {   /** 添加计划电量 */
                String param = rootNode.path("powers").toString();
                JavaType jt = mapper.getTypeFactory().constructParametricType(ArrayList.class, PlanPower.class);
                List<PlanPower> planPowers = mapper.readValue(param, jt);
                settingService.addPlanPowers(planPowers);
                return RespUtil.makeOkResp();

            } else if (type.equals("update")) {  /** 更新计划电量 */
                String param = rootNode.path("powers").toString();
                JavaType jt = mapper.getTypeFactory().constructParametricType(ArrayList.class, PlanPower.class);
                List<PlanPower> planPowers = mapper.readValue(param, jt);
                settingService.updatePlanPowers(planPowers);
                return RespUtil.makeOkResp();

            } else if (type.equals("delete")) {  /** 删除计划电量 */
                String param = rootNode.path("powers").toString();
                JavaType jt = mapper.getTypeFactory().constructParametricType(ArrayList.class, PlanPower.class);
                List<PlanPower> planPowers = mapper.readValue(param, jt);
                settingService.deletePlanPowers(planPowers);
                return RespUtil.makeOkResp();

            } else {
                return RespUtil.makeParamErrResp();
            }

        } catch (Exception e) {
            e.printStackTrace();
            RespUtil.makeInnerErrResp();
        }

        return RespUtil.makeOkResp();
    }
}
