package com.sk.zl.controller;

import com.sk.zl.model.plant.PlantDataPreview;
import com.sk.zl.model.request.ReDataPreview;
import com.sk.zl.model.result.ResultBean;
import com.sk.zl.service.DataPreviewService;
import com.sk.zl.utils.ResultBeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
    public ResultBean<List<PlantDataPreview>> plantDataPreview(Model m, @RequestBody ReDataPreview reDataPreview) {
        return ResultBeanUtil.makeOkResp(dataPreviewService.getPlantData(reDataPreview));
    }

    @ApiOperation("报警查询")
    @RequestMapping(value = "/points/alarm")
    public ResultBean<List<PlantDataPreview>> warningDataPreview(@RequestBody ReDataPreview reDataPreview) {
        return ResultBeanUtil.makeOkResp(dataPreviewService.getWarningData(reDataPreview));
    }
}
