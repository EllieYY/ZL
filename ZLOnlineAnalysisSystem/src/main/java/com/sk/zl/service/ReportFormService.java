package com.sk.zl.service;

import com.sk.zl.entity.zheling.GenPowerEntity;
import com.sk.zl.entity.zheling.MeterCodeEntity;
import com.sk.zl.model.meter.MeterCode;
import com.sk.zl.model.meter.MeterGroupInfo;

import java.util.Date;
import java.util.List;

public interface ReportFormService {
    /** 电表码值录入 */
    List<GenPowerEntity> entryMeterCode(List<MeterCode> meterCodes);

    /** 电表信息查询 */
    List<MeterGroupInfo> getMetersInfo(int groupId, Date time);
}
