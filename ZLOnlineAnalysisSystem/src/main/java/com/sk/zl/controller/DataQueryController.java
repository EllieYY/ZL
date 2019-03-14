package com.sk.zl.controller;

import com.sk.zl.model.meter.MeterData;
import com.sk.zl.model.meter.MeterGroup;
import com.sk.zl.model.plant.PlantDataPreview;
import com.sk.zl.model.request.ReDataPreview;
import com.sk.zl.model.request.ReMeterData;
import com.sk.zl.model.result.ResultBean;
import com.sk.zl.service.DataPreviewService;
import com.sk.zl.utils.ResultBeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description : 数据查询接口
 * @Author : Ellie
 * @Date : 2019/3/7
 */
@Api("数据查询接口")
@RestController
public class DataQueryController {
    @Resource
    DataPreviewService dataPreviewService;

    @ApiOperation("一览表查询")
    @RequestMapping(value = "/points/view")
    public ResultBean<List<PlantDataPreview>> plantDataPreview(@RequestBody ReDataPreview reDataPreview) {
        return ResultBeanUtil.makeOkResp(dataPreviewService.getPlantData(reDataPreview));
    }

    @ApiOperation("报警查询")
    @RequestMapping(value = "/points/alarm")
    public ResultBean<List<PlantDataPreview>> warningDataPreview(@RequestBody ReDataPreview reDataPreview) {
        return ResultBeanUtil.makeOkResp(dataPreviewService.getWarningData(reDataPreview));
    }

    @ApiOperation("报表-日电量-基础信息查询")
    @RequestMapping(value = "/points/report/dayPower", method = RequestMethod.POST)
    public ResultBean<List<MeterGroup>> getMeterData(@RequestBody ReMeterData reMeterData) {
        List<MeterGroup> groups = new ArrayList<MeterGroup>();
        groups.add(new MeterGroup("一号机组", Arrays.asList(
                new MeterData(1001,"峰", 24, 34, 2.0),
                new MeterData(1002,"平", 24, 34, 2.0),
                new MeterData(1003,"谷", 24, 34, 2.0),
                new MeterData(1004,"总", 24, 34, 2.0))));
        groups.add(new MeterGroup("二号机组", Arrays.asList(
                new MeterData(1005,"峰", 24, 34, 2.0),
                new MeterData(1006,"平", 24, 34, 2.0),
                new MeterData(1007,"谷", 24, 34, 2.0),
                new MeterData(1008,"总", 24, 34, 2.0))));

        groups.add(new MeterGroup("柘青1E", Arrays.asList(
                new MeterData(2001,"正向有功总（+A）", 24, 34, 2.0),
                new MeterData(2002,"反向有功总（+A）", 24, 34, 2.0))));
        groups.add(new MeterGroup("柘青2E", Arrays.asList(
                new MeterData(2003,"正向有功总（+A）", 24, 34, 2.0),
                new MeterData(2004,"反向有功总（+A）", 24, 34, 2.0))));

        groups.add(new MeterGroup("一号厂用变", Arrays.asList(
                new MeterData(3001,"一号厂用变", 24, 34, 2.0))));
        groups.add(new MeterGroup("二号厂用变", Arrays.asList(
                new MeterData(3002,"二号厂用变", 24, 34, 2.0))));

        return ResultBeanUtil.makeOkResp(groups);
    }
}
