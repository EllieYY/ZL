package com.sk.zl.controller;

import com.sk.zl.entity.PointInfo;
import com.sk.zl.service.PointInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/pointInfo")
@RestController
public class PointInfoController {

    @Autowired
    PointInfoService pointInfoService;

    @GetMapping("/statistic/current/{type}")
    public List<PointInfo> currentStatistic(@PathVariable String type){
        return pointInfoService.getPointCurrentStatistic(type);
    }

    @PostMapping("")
    public void addPointInfo(@RequestBody PointInfo pointInfo) {
        pointInfoService.addPointInfo(pointInfo);
    }
}
