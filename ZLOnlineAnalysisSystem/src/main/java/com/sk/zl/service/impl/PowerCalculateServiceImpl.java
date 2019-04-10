package com.sk.zl.service.impl;

import com.sk.zl.dao.meter.MeterDao;
import com.sk.zl.dao.meter.MeterGroupDao;
import com.sk.zl.dao.meter.impl.GenPowerDaoEx;
import com.sk.zl.entity.zheling.GenPowerEntity;
import com.sk.zl.entity.zheling.MeterEntity;
import com.sk.zl.entity.zheling.MeterGroupEntity;
import com.sk.zl.service.PowerCalculateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description : TODO
 * @Author : Ellie
 * @Date : 2019/4/4
 */
@Service
public class PowerCalculateServiceImpl implements PowerCalculateService {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private MeterDao meterDao;
    @Resource
    private GenPowerDaoEx genPowerDaoEx;
    @Resource
    private MeterGroupDao meterGroupDao;


    @Override
    public double calculateGenPowerByGroup(int groupId, Date startTime, Date endTime) {
        MeterGroupEntity group = meterGroupDao.findById(groupId);
        List<Integer> nodeIds = new ArrayList<>();
        for (MeterEntity meter: group.getMeterSet()) {
            // 获取meter下node的id集合
            List<Integer> ids = meter.getNodeSet().stream().collect(ArrayList::new, (list, item) -> {
                list.add(item.getId());
            }, ArrayList::addAll);

            nodeIds.addAll(ids);
        }

        List<GenPowerEntity> results = genPowerDaoEx.findByNodeIdsAndTime(nodeIds, startTime, endTime);

        double total = 0;
        for (GenPowerEntity entity: results) {
            total += entity.getValue();
        }
        return total;
    }

    @Override
    public double calculateGenPowerByMeter(int meterId, Date startTime, Date endTime) {
        // 获取meter下node的id集合
        MeterEntity meter = meterDao.findById(meterId).get();
        List<Integer> ids = meter.getNodeSet().stream().collect(ArrayList::new, (list, item) -> {
            list.add(item.getId());
        }, ArrayList::addAll);

        return calculateGenPowerByMeterNode(ids, startTime, endTime);
    }

    @Override
    public double calculateGenPowerByMeterNode(List<Integer> meterNodeIds, Date startTime, Date endTime) {
        List<GenPowerEntity> results = genPowerDaoEx.findByNodeIdsAndTime(meterNodeIds, startTime, endTime);

        double total = 0;
        for (GenPowerEntity entity: results) {
            total += entity.getValue();
        }
        return total;
    }
}
