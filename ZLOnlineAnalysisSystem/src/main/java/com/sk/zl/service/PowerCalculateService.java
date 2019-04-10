package com.sk.zl.service;

import java.util.Date;
import java.util.List;

public interface PowerCalculateService {
    double calculateGenPowerByGroup(int groupId, Date startTime, Date endTime);
    double calculateGenPowerByMeter(int meterId, Date startTime, Date endTime);
    double calculateGenPowerByMeterNode(List<Integer> meterNodeIds, Date startTime, Date endTime);
}
