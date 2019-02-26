package com.sk.zl.dao.meter;

import com.sk.zl.entity.MeterEntity;
import com.sk.zl.model.meter.Meter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description : TODO
 * @Author : Ellie
 * @Date : 2019/2/22
 */
public interface MeterDao extends JpaRepository<MeterEntity, Integer> {

//    @Modifying(clearAutomatically = true)
//    @Transactional
//    @Query(value = "select new com.sk.zl.model.meter.Meter(m, mg) from MeterEntity m, MeterGroupEntity mg where m.groupId=mg.id")
//    public List<Meter> findMeterInfo();
}
