package com.sk.zl.controller;

import com.sk.zl.test.TestPoint;
import com.sk.zl.entity.zheling.MeterCodeEntity;
import com.sk.zl.model.meter.MeterCode;
import com.sk.zl.model.plant.PagePlantDataPreview;
import com.sk.zl.model.request.ReDataPreview;
import com.sk.zl.model.result.RespCode;
import com.sk.zl.model.result.ResultBean;
import com.sk.zl.service.DataPreviewService;
import com.sk.zl.service.ReportFormService;
import com.sk.zl.utils.ResultBeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;


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

    @Resource
    private ReportFormService reportFormService;
    @Resource
    TestPoint testPoint;

    @ApiOperation("一览表查询")
    @RequestMapping(value = "/points/view")
    public ResultBean<PagePlantDataPreview> plantDataPreview(@RequestBody ReDataPreview reDataPreview) {
        if (reDataPreview.getIds().size() == 0) {
            return ResultBeanUtil.makeResp(RespCode.PARAM_ERR.getCode(), "设备id为空。");
        }
        return ResultBeanUtil.makeOkResp(dataPreviewService.getWarningData(reDataPreview));
    }

    @ApiOperation("报警查询")
    @RequestMapping(value = "/points/alarm")
    public ResultBean<PagePlantDataPreview> warningDataPreview(@RequestBody ReDataPreview reDataPreview) {
        if (reDataPreview.getIds().size() == 0) {
            return ResultBeanUtil.makeResp(RespCode.PARAM_ERR.getCode(), "设备id为空。");
        }
        return ResultBeanUtil.makeOkResp(dataPreviewService.getWarningData(reDataPreview));
    }

    @ApiOperation("实时报警")
    @RequestMapping(value = "/points/nowalarm")
    public ResultBean<PagePlantDataPreview> nowAlarmDataPreview(@RequestBody ReDataPreview reDataPreview) {
        if (reDataPreview.getIds().size() == 0) {
            return ResultBeanUtil.makeResp(RespCode.PARAM_ERR.getCode(), "设备id为空。");
        }

        return ResultBeanUtil.makeOkResp(dataPreviewService.getNowAlarm(reDataPreview));
    }

    @ApiOperation("测试 metercode entity")
    @RequestMapping(value = "/test")
    public ResultBean<MeterCodeEntity> test1(@RequestBody MeterCodeEntity entity) {
        reportFormService.entryMeterCode(Arrays.asList(MeterCode.fromEntity(entity)));
        return ResultBeanUtil.makeOkResp(entity);
    }

    @ApiOperation("测试 meter code")
    @RequestMapping(value = "/test1")
    public ResultBean<MeterCode> test2(@RequestBody MeterCode entity) {
        reportFormService.entryMeterCode(Arrays.asList(entity));
        return ResultBeanUtil.makeOkResp(entity);
    }

    @ApiOperation("测试 配置文件")
    @RequestMapping(value = "/config")
    public ResultBean<String> testConfig() {
        return ResultBeanUtil.makeOkResp(testPoint.toString());
    }
}
