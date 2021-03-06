package com.sk.zl.dao.meter;

import com.sk.zl.entity.zheling.MeterRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface MeterRateDao extends JpaRepository<MeterRateEntity, Integer>, JpaSpecificationExecutor<MeterRateEntity> {
    List<MeterRateEntity> findByMeterId(int meterId);
    MeterRateEntity findById(int id);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "update MeterRateEntity model set model.rate=:rate, model.startTime=:startTime, model.endTime=:endTime where model.id=:id")
    int updateRateById(@Param("id") int id, @Param("rate") double rate, @Param("startTime") Date startTime, @Param("endTime") Date endTime);


}
