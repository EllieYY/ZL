package com.sk.zl.controller;

import com.sk.zl.entity.zheling.GenPowerEntity;
import com.sk.zl.entity.zheling.MeterCodeEntity;
import com.sk.zl.model.meter.MeterCode;
import com.sk.zl.model.meter.MeterData;
import com.sk.zl.model.meter.MeterGroupInfo;
import com.sk.zl.model.request.ReMeterCode;
import com.sk.zl.model.request.ReMeterData;
import com.sk.zl.model.result.ResultBean;
import com.sk.zl.service.ReportFormService;
import com.sk.zl.utils.DateUtil;
import com.sk.zl.utils.ResultBeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.unit.DataUnit;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description : 报表接口
 * @Author : Ellie
 * @Date : 2019/3/19
 */
@Api("报表接口")
@RestController
public class ReportFormController {
    @Resource
    private ReportFormService reportFormService;

    @ApiOperation(value = "报表-日电量-新增报表信息")
    @RequestMapping(value = "/points/report/meterData",  method = RequestMethod.POST)
    public ResultBean<List<GenPowerEntity>> setMeterCode(@RequestBody ReMeterCode meterCodes) {
        Date date = meterCodes.getTime();
        date = DateUtil.dateTimeToDate(date);
        for (MeterCode code : meterCodes.getMeterCodes()) {
            code.setTime(date);
        }

        return ResultBeanUtil.makeOkResp(reportFormService.entryMeterCode(meterCodes.getMeterCodes()));
    }

    @ApiOperation("报表-日电量-基础信息查询")
    @RequestMapping(value = "/points/report/dayPower", method = RequestMethod.POST)
    public ResultBean<List<MeterGroupInfo>> getMeterData(@RequestBody ReMeterData reMeterData) {
        return ResultBeanUtil.makeOkResp(reportFormService.getMetersInfo(
                reMeterData.getGroupId(), reMeterData.getDate()));
    }
}
