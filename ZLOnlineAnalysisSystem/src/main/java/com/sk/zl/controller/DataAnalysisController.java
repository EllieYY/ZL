package com.sk.zl.controller;

import com.sk.zl.model.plant.PagePlantAnalogPoints;
import com.sk.zl.model.plant.PlantFaultPointsStat;
import com.sk.zl.model.plant.PlantRunningAnalysis;
import com.sk.zl.model.plant.PlantTrend;
import com.sk.zl.model.request.RePlantAnalogs;
import com.sk.zl.model.request.ReDataAnalysis;
import com.sk.zl.model.result.ResultBean;
import com.sk.zl.service.DataPreviewService;
import com.sk.zl.utils.ResultBeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description : 生产信息页面
 * @Author : Ellie
 * @Date : 2019/3/7
 */
@Api("生产信息")
@RestController
public class DataAnalysisController {
    @Resource
    DataPreviewService dataPreviewService;

    @ApiOperation(value = "测点分析-报警点预览")
    @RequestMapping(value = "/product/point")
    public ResultBean<List<PlantFaultPointsStat>> getFaultStatistical() {
        return ResultBeanUtil.makeOkResp(dataPreviewService.getPlantFaultsStat());
    }

    @ApiOperation(value = "测点分析-报警点明细")
    @RequestMapping(value = "/product/point/detail",  method = RequestMethod.POST)
    public ResultBean<PagePlantAnalogPoints> getPlantAnalogPoints(@RequestBody RePlantAnalogs rePlantAnalogs) {
        return ResultBeanUtil.makeOkResp(dataPreviewService.getAnalogPointsById(rePlantAnalogs));
    }

    @ApiOperation(value = "趋势分析")
    @RequestMapping(value = "/product/trend")
    public ResultBean<List<PlantTrend>> getPlantTrend(@RequestBody ReDataAnalysis condition) {
        return ResultBeanUtil.makeOkResp(dataPreviewService.getPlantTrend(condition));
    }

    @ApiOperation(value = "开停机分析")
    @RequestMapping(value = "/points/state")
    public ResultBean<List<PlantRunningAnalysis>> getRunningTimeAnalysis(@RequestBody ReDataAnalysis condition) {
        return ResultBeanUtil.makeOkResp(dataPreviewService.getRunningTimeAnalysis(condition));
    }
}
