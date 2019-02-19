package com.sk.zl.service;

import com.sk.zl.entity.PointInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PointInfoService {

    List<PointInfo> getPointCurrentStatistic(String type);

    void addPointInfo(PointInfo pointInfo);

}
