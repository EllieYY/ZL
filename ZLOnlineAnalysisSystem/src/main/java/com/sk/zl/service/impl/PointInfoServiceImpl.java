package com.sk.zl.service.impl;

import com.sk.zl.dao.statistic.PointInfoDao;
import com.sk.zl.entity.PointInfo;
import com.sk.zl.service.PointInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PointInfoServiceImpl implements PointInfoService {

    @Autowired
    PointInfoDao pointInfoDao;

    @Override
    public List<PointInfo> getPointCurrentStatistic(String type) {

        List<PointInfo> pointInfos = pointInfoDao.queryPointInfoByInsertTimeAndType(new Date(), type);
        // 实现业务逻辑
        return pointInfos.stream().filter(pointInfo -> pointInfo.getStatus() == 0).collect(Collectors.toList());
    }

    @Override
    public void addPointInfo(PointInfo pointInfo) {
        pointInfoDao.savePointInfo(pointInfo);
    }


}
