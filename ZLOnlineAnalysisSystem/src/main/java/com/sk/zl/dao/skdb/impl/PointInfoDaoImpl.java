package com.sk.zl.dao.skdb.impl;

import com.sk.zl.config.skdb.SkdbProperties;
import com.sk.zl.dao.skdb.PointInfoDao;
import com.sk.zl.model.plant.PlantFaultPointsStat;
import com.sk.zl.model.plant.PlantRunningTimeAnalysis;
import com.sk.zl.model.plant.PlantTrend;
import com.sk.zl.model.request.RePlantTrend;
import com.sk.zl.model.request.ReRunningTimeAnalysis;
import com.sk.zl.model.skRest.PlantFaultStatCpid;
import com.sk.zl.model.skRest.PlantSnapshotCpid;
import com.sk.zl.model.skRest.PointInfo;
import com.sk.zl.utils.SkRestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Description : 测点信息获取
 * @Author : Ellie
 * @Date : 2019/2/28
 */
@Repository
public class PointInfoDaoImpl implements PointInfoDao {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SkRestUtil skRestUtil;
    @Autowired
    SkdbProperties skdbProperties;

    @Override
    public List<PointInfo> findRealTimeActivePowerPoints() {
        List<String> cpids = skdbProperties.getRealTimeActivePowerPoints();
        List<PointInfo> points = skRestUtil.getNowValue(cpids);

        log.debug("#skdb# RealTimeActivePowerPoints: " + cpids + "\n" +
                "points: " + points);

        return points;
    }

    @Override
    public List<PointInfo> findPlantSnapshotPointsById(int plantId) {
        log.debug("plant#" + plantId + " snapshot");

        List<PlantSnapshotCpid> params = skdbProperties.getPlantsSnapshotPoints();
        for (PlantSnapshotCpid plants: params) {
            if (plants.getId() != plantId) {
                log.debug(" no plant id find in properties. #" + plantId);
                continue;
            }

            // 控制顺序
            List<String> cpids = Arrays.asList(plants.getActivePower(),
                    plants.getReactivePower(),
                    plants.getGuideVaneOpening());
            List<PointInfo> points = skRestUtil.getNowValue(cpids);

            log.debug("plant snapshot points: " + points);
            return points;
        }
        return null;
    }

    @Override
    public List<PointInfo> findStationAlarmPoints() {
        // 控制顺序
        List<String> cpids = Arrays.asList(skdbProperties.getAccidentPoint(),
                skdbProperties.getGlitchesPoint());

        System.out.println(cpids);

        log.debug("station alarm cpids: " + cpids);

        List<PointInfo> points = skRestUtil.getNowValue(cpids);

        System.out.println(points);
        log.debug("station alarm cpid:" + cpids + "\n" + "values: " + points);

        return points;
    }

    @Override
    public List<PlantFaultPointsStat> findPlantFaultPoints() {
        List<PlantFaultPointsStat> stat = new ArrayList<PlantFaultPointsStat>();
        List<PlantFaultStatCpid> params = skdbProperties.getPlantFaultStatCpids();

        log.debug("#findPlantFaultPoints# params: " + params);

        for (PlantFaultStatCpid plant: params) {
            // 控制顺序
            List<String> cpids = Arrays.asList(
                    plant.getFaultStatPtCpid(),
                    plant.getSystemStatPtCpid());
            List<PointInfo> points = skRestUtil.getNowValue(cpids);

            if (points.size() != 2) {
                // TODO: 未获取到测点值时，是否给0值
                log.debug("fail to get value for " + cpids);
                continue;
            }

            int faultNum = new Double(points.get(0).getValue()).intValue();
            int systemNum = new Double(points.get(1).getValue()).intValue();
            stat.add(new PlantFaultPointsStat(plant.getId(), faultNum, systemNum));
        }

        log.debug("stat: " + stat);

        return stat;
    }


    @Override
    public List<PlantTrend> findPlantTrendByCondition(RePlantTrend condition) {
        // TODO:待扩充cgi接口
        List<PlantTrend> result = new ArrayList<PlantTrend>();
        result.add(new PlantTrend("name1", 2, 3, 1));
        result.add(new PlantTrend("name2", 2, 3, 1));
        result.add(new PlantTrend("name3", 2, 3, 1));
        result.add(new PlantTrend("name4", 2, 3, 1));
        result.add(new PlantTrend("name5", 2, 3, 1));
        result.add(new PlantTrend("name6", 2, 3, 1));
        result.add(new PlantTrend("name7", 2, 3, 1));

        return result;
    }


    @Override
    public List<PlantRunningTimeAnalysis> findRunningTimeInfoByCondition(ReRunningTimeAnalysis condition) {
        // TODO:待扩充cgi接口

        List<PlantRunningTimeAnalysis> result = new ArrayList<PlantRunningTimeAnalysis>();
        result.add(new PlantRunningTimeAnalysis("name1", 2, 3000, new Date(), new Date(), 1));
        result.add(new PlantRunningTimeAnalysis("name2", 1, 3000, new Date(), new Date(), 1));
        result.add(new PlantRunningTimeAnalysis("name3", 1, 3000, new Date(), new Date(), 1));
        result.add(new PlantRunningTimeAnalysis("name4", 3, 3000, new Date(), new Date(), 1));
        result.add(new PlantRunningTimeAnalysis("name5", 3, 3000, new Date(), new Date(), 1));
        result.add(new PlantRunningTimeAnalysis("name6", 3, 3000, new Date(), new Date(), 1));

        return result;
    }
}
