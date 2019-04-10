package com.sk.zl.service.impl;

import com.sk.zl.dao.meter.MeterCodeDao;
import com.sk.zl.dao.meter.MeterDao;
import com.sk.zl.dao.meter.impl.MeterCodeDaoEx;
import com.sk.zl.dao.meter.impl.MeterRateDaoEx;
import com.sk.zl.entity.zheling.GenPowerEntity;
import com.sk.zl.entity.zheling.MeterCodeEntity;
import com.sk.zl.entity.zheling.MeterEntity;
import com.sk.zl.entity.zheling.MeterNodeEntity;
import com.sk.zl.model.meter.MeterCode;
import com.sk.zl.model.meter.MeterData;
import com.sk.zl.model.meter.MeterGroupInfo;
import com.sk.zl.service.PowerCalculateService;
import com.sk.zl.service.ReportFormService;
import com.sk.zl.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Description : 报表服务
 * @Author : Ellie
 * @Date : 2019/3/19
 */
@Service
public class ReportFormServiceImpl implements ReportFormService {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private MeterCodeDaoEx meterCodeDaoEx;

    @Resource
    private AsyncTaskService taskService;

    @Resource
    private MeterDao meterDao;

    @Resource
    private MeterRateDaoEx meterRateDaoEx;

    @Autowired
    private PowerCalculateService powerCalculateService;

    @Override
    public List<GenPowerEntity> entryMeterCode(List<MeterCode> models) {
        //#1 存到数据库
        List<MeterCodeEntity> entities = models.stream().collect(ArrayList::new, (list, item) -> {
            list.add(item.toEntity());
        }, ArrayList::addAll);

        meterCodeDaoEx.saveAll(entities);

        //#2 计算电量——任务线程
        return taskService.meterCodeUpdate(models);
    }


    @Override
    public List<MeterGroupInfo> getMetersInfo(int groupId, Date time) {
        List<MeterGroupInfo> groups = new ArrayList<MeterGroupInfo>();

        Date curDay = time;
        curDay = DateUtil.dateTimeToDate(curDay);
        Date preDay = DateUtil.dateAddDays(curDay, -1, false);
        Date nextDay = DateUtil.dateAddDays(curDay, 1, false);
        Date monthStart = DateUtil.getFirstDateOfMonth(curDay);
        Date yearStart = DateUtil.getFirstDateOfYear(curDay);

        //#1 查询当前group下所有电表分组，控制id增序。
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        List<MeterEntity> meters = meterDao.findByGroup_Id(groupId, sort);
        for (MeterEntity meter : meters) {
            // 电表基础信息
            String meterName = meter.getName();

            // 电表倍率
            int meterId = meter.getId();
            double rate = meterRateDaoEx.findByMeterIdAndTime(meterId, time);

            List<MeterData> data = new ArrayList<MeterData>();
            List<MeterNodeEntity> nodes = meter.getNodeSet();
            for (MeterNodeEntity node : nodes) {
                // 基础信息
                int nodeId = node.getId();
                String nodeName = node.getName();

                // 表码信息
                // 前一天表码
                double preCode = -1;
                //# 若一天录入多个表码值，取时间最大的
                List<MeterCodeEntity> codeEntities = meterCodeDaoEx.findByMeterAndTimeDec(
                        nodeId, preDay, curDay);
                if (codeEntities.size() >= 1) {
                    preCode = codeEntities.get(0).getCode();
                }

                // 当天表码
                double curCode = -1;
                codeEntities = meterCodeDaoEx.findByMeterAndTimeDec(
                        nodeId, curDay, nextDay);
                if (codeEntities.size() >= 1) {
                    curCode = codeEntities.get(0).getCode();
                }

                // 增加当月电量信息和当年电量信息。
                List<Integer> id = Arrays.asList(nodeId);
                double monthPower = powerCalculateService.calculateGenPowerByMeterNode(id, monthStart, curDay);
                double yearPower = powerCalculateService.calculateGenPowerByMeterNode(id, yearStart, curDay);

                data.add(new MeterData(nodeId, nodeName, preCode, curCode, rate, monthPower, yearPower));
            }

            groups.add(new MeterGroupInfo(meterName, data));
        }

        return groups;
    }
}
