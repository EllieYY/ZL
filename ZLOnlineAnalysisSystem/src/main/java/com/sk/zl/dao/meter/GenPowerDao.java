package com.sk.zl.dao.meter;

import com.sk.zl.entity.GenPowerEntity;
import com.sk.zl.entity.GenPowerEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface GenPowerDao extends JpaRepository<GenPowerEntity, GenPowerEntityPK> {
    List<GenPowerEntity> findByMeterIdInAndTimeBetween(
            List<Integer> meterIds,
            Date satrtTime,
            Date endTime);

    List<GenPowerEntity> findByMeterIdAndTimeBetween(
            int meterid,
            Date startTime,
            Date endTime);
}
