package com.sk.zl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sk.zl.model.plant.PlantEffectiveHours;
import com.sk.zl.model.plant.PlantGenCapacityComparison;
import com.sk.zl.model.plant.PlantGenerateCapacity;
import com.sk.zl.model.plant.PlantPointSnapshot;
import com.sk.zl.model.request.ReTimeSlots;
import com.sk.zl.model.result.ResultBean;
import com.sk.zl.model.station.StationAlarmNum;
import com.sk.zl.service.PlantService;
import com.sk.zl.service.StationService;
import com.sk.zl.utils.ResultBeanUtil;
import com.sk.zl.model.station.AnnualCapacityInfo;
import com.sk.zl.model.station.HydrologicalInfo;
import com.sk.zl.model.station.PowerStationSnapshot;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description : 处理首页请求
 * @Author : Ellie
 * @Date : 2019/2/26
 */
@Api("首页展示")
@RestController
public class HomePageController {
    @Resource
    StationService stationService;
    @Resource
    PlantService plantService;

    @ApiOperation(value = "发电厂信息快照")
    @RequestMapping(value = "/product")
    public ResultBean<PowerStationSnapshot> getStationSnapshot() {
        return ResultBeanUtil.makeOkResp(stationService.getStationSnapshot());
    }

    @ApiOperation(value = "机组测点数据")
    @RequestMapping(value = "/product/device")
    public ResultBean<List<PlantPointSnapshot>> getPlantPointSnapshot() {
        return ResultBeanUtil.makeOkResp(plantService.getPointSnapshot());
    }

    @ApiOperation(value = "水情信息")
    @RequestMapping(value = "/product/water")
    public ResultBean<HydrologicalInfo> getHydrologicalInfo() {
        return ResultBeanUtil.makeOkResp(stationService.getHydrologicalInfo());
    }

    @ApiOperation(value = "发电量完成情况")
    @RequestMapping(value = "/product/power/generatePower")
    public ResultBean<AnnualCapacityInfo> getAnnualCapacityInfo() {
        return ResultBeanUtil.makeOkResp(stationService.getAnnualCapacityInfo());
    }

    @ApiOperation(value = "上网电量")
    @RequestMapping(value = "/product/power/ongrid")
    public ResultBean<List<Double>> getAnnualOngridPowerInfo() {
        return ResultBeanUtil.makeOkResp(stationService.getOngridCapacityInfo());
    }

    @ApiOperation(value = "机组发电量排名")
    @RequestMapping(value = "/product/rank/power")
    public ResultBean<List<PlantGenerateCapacity>> getPlantGenerateCapacity() {
        return ResultBeanUtil.makeOkResp(plantService.getGenCapacityRank());
    }

    @ApiOperation(value = "机组月利用小时数排名")
    @RequestMapping(value = "/product/rank/hour")
    public ResultBean<List<PlantEffectiveHours>> getPlantEffectiveHours() {
        return ResultBeanUtil.makeOkResp(plantService.getEffectiveHoursRank());
    }

    @ApiOperation(value = "机组发电量信息比对")
    @RequestMapping(value = "/product/compare",  method = RequestMethod.POST)
    public ResultBean<List<PlantGenCapacityComparison>> getPlantComparison(@RequestBody ReTimeSlots timeSlots) {
            return ResultBeanUtil.makeOkResp(plantService.getPlantComparison(timeSlots));
    }

    @ApiOperation(value = "全厂故障信息条数")
    @RequestMapping(value = "/product/alarm")
    public ResultBean<StationAlarmNum> getStationAlarmInfo() {
        return ResultBeanUtil.makeOkResp(stationService.getAlarmNum());
    }

}
