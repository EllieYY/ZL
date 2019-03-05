package com.sk.zl.dao.skdb.impl;

import com.sk.zl.config.SkdbProperties;
import com.sk.zl.dao.skdb.PointInfoDao;
import com.sk.zl.model.plant.PlantPointSnapshot;
import com.sk.zl.model.skRest.PlantSnapshotCpid;
import com.sk.zl.model.skRest.PointInfo;
import com.sk.zl.model.skRest.PointsCpid;
import com.sk.zl.model.skRest.PointsCpidWrap;
import com.sk.zl.utils.SkRestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

/**
 * @Description : 测点信息获取
 * @Author : Ellie
 * @Date : 2019/2/28
 */
@Repository
public class PointInfoDaoImpl implements PointInfoDao {
    @Autowired
    SkRestUtil skRestUtil;
    @Autowired
    SkdbProperties skdbProperties;


    @Override
    public List<PointInfo> findRealTimeActivePowerPoints() {
        return skRestUtil.getNowValue(skdbProperties.getRealTimeActivePowerPoints());
    }

    @Override
    public List<PointInfo> findPlantSnapshotPointsById(int plantId) {
        for (PlantSnapshotCpid plants: skdbProperties.getPlantsSnapshotPoints()) {
            if (plants.getId() != plantId) {
                continue;
            }

            // 控制顺序
            List<String> cpids = Arrays.asList(plants.getActivePower(),
                    plants.getReactivePower(),
                    plants.getGuideVaneOpening());
            return skRestUtil.getNowValue(cpids);
        }
        return null;
    }

    @Override
    public List<PointInfo> findStationAlarmPoints() {
        // 控制顺序
        List<String> cpids = Arrays.asList(skdbProperties.getAccidentPoint(),
                skdbProperties.getGlitchesPoint());
        System.out.println(cpids);
        return skRestUtil.getNowValue(cpids);
    }
}
