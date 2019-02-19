package com.sk.zl.dao.statistic.impl;

import com.sk.zl.dao.statistic.PointInfoDao;
import com.sk.zl.entity.PointInfo;
import com.sk.zl.utils.SkRestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class PonitInfoDaoImpl implements PointInfoDao {

    @Autowired
    SkRestUtils skRestUtils;

    @Override
    public List<PointInfo> queryPointInfoByInsertTimeAndType(Date insertTime, String type) {
        // TODO
        return null;
    }
}
