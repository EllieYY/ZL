package com.sk.zl.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sk.zl.model.plant.PlantEffectiveHours;
import com.sk.zl.model.plant.PlantGenCapacityComparison;
import com.sk.zl.model.plant.PlantGenerateCapacity;
import com.sk.zl.model.plant.PlantPointSnapshot;
import com.sk.zl.model.result.RespEntity;
import com.sk.zl.model.station.StationAlarmNum;
import com.sk.zl.service.PlantService;
import com.sk.zl.service.StationService;
import com.sk.zl.utils.RespUtil;
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
    @RequestMapping(value = "/product",  method = RequestMethod.POST)
    public RespEntity<PowerStationSnapshot> getStationSnapshot(@RequestBody String jsonStr) {
        try {
            return stationService.getStationSnapshot();
        } catch (Exception e) {
            e.printStackTrace();
            RespUtil.makeInnerErrResp();
        }

        return RespUtil.makeCustomErrResp("后台未知错误。");
    }

    @ApiOperation(value = "机组测点数据")
    @RequestMapping(value = "/product/device",  method = RequestMethod.POST)
    public RespEntity<List<PlantPointSnapshot>> getPlantPointSnapshot(@RequestBody String jsonStr) {
        try {
            return plantService.getPointSnapshot();
        } catch (Exception e) {
            e.printStackTrace();
            RespUtil.makeInnerErrResp();
        }

        return RespUtil.makeCustomErrResp("后台未知错误。");
    }

    @ApiOperation(value = "水情信息")
    @RequestMapping(value = "/product/water",  method = RequestMethod.POST)
    public RespEntity<HydrologicalInfo> getHydrologicalInfo(@RequestBody String jsonStr) {
        try {
            return stationService.getHydrologicalInfo();
        } catch (Exception e) {
            e.printStackTrace();
            RespUtil.makeInnerErrResp();
        }

        return RespUtil.makeCustomErrResp("后台未知错误。");
    }

    @ApiOperation(value = "发电量完成情况")
    @RequestMapping(value = "/product/power/generatePower",  method = RequestMethod.POST)
    public RespEntity<AnnualCapacityInfo> getAnnualCapacityInfo(@RequestBody String jsonStr) {
        try {
            return stationService.getAnnualCapacityInfo();
        } catch (Exception e) {
            e.printStackTrace();
            RespUtil.makeInnerErrResp();
        }

        return RespUtil.makeCustomErrResp("后台未知错误。");
    }

    @ApiOperation(value = "上网电量")
    @RequestMapping(value = "/product/power/ongrid",  method = RequestMethod.POST)
    public RespEntity<List<Double>> getAnnualOngridPowerInfo(@RequestBody String jsonStr) {
        try {
            return stationService.getOngridCapacityInfo();
        } catch (Exception e) {
            e.printStackTrace();
            RespUtil.makeInnerErrResp();
        }

        return RespUtil.makeCustomErrResp("后台未知错误。");
    }

    @ApiOperation(value = "机组发电量排名")
    @RequestMapping(value = "/product/rank/power",  method = RequestMethod.POST)
    public RespEntity<List<PlantGenerateCapacity>> getPlantGenerateCapacity(@RequestBody String jsonStr) {
        try {
            return stationService.getGenCapacityRank();
        } catch (Exception e) {
            e.printStackTrace();
            RespUtil.makeInnerErrResp();
        }

        return RespUtil.makeCustomErrResp("后台未知错误。");
    }

    @ApiOperation(value = "机组月利用小时数排名")
    @RequestMapping(value = "/product/rank/hour",  method = RequestMethod.POST)
    public RespEntity<List<PlantEffectiveHours>> getPlantEffectiveHours(@RequestBody String jsonStr) {
        try {
            return stationService.getEffectiveHoursRank();
        } catch (Exception e) {
            e.printStackTrace();
            RespUtil.makeInnerErrResp();
        }

        return RespUtil.makeCustomErrResp("后台未知错误。");
    }

    @ApiOperation(value = "机组发电量信息比对")
    @RequestMapping(value = "/product/compare",  method = RequestMethod.POST)
    public RespEntity<List<PlantGenCapacityComparison>> getPlantComparison(@RequestBody String jsonStr) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(jsonStr);
            long sTime1 = rootNode.path("fTimeFrom").asLong();
            long eTime1 = rootNode.path("fTimeTo").asLong();
            long sTime2 = rootNode.path("sTimeFrom").asLong();
            long eTime2 = rootNode.path("sTimeTo").asLong();

            return stationService.getPlantComparison(sTime1, eTime1, sTime2, eTime2);
        } catch (Exception e) {
            e.printStackTrace();
            RespUtil.makeInnerErrResp();
        }

        return RespUtil.makeCustomErrResp("后台未知错误。");
    }

    @ApiOperation(value = "全厂故障信息条数")
    @RequestMapping(value = "/product/alarm",  method = RequestMethod.POST)
    public RespEntity<StationAlarmNum> getStationAlarmInfo(@RequestBody String jsonStr) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return stationService.getAlarmNum();
        } catch (Exception e) {
            e.printStackTrace();
            RespUtil.makeInnerErrResp();
        }

        return RespUtil.makeCustomErrResp("后台未知错误。");
    }


}
