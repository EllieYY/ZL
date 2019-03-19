package com.sk.zl.dao.meter;

import com.sk.zl.entity.zheling.MeterEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description :
 * @Author : Ellie
 * @Date : 2019/2/22
 */
public interface MeterDao extends JpaRepository<MeterEntity, Integer> {

//    MeterEntity findById(int id);
    List<MeterEntity> findByGroup_Id(int groupId, Sort sort);

//    @Modifying(clearAutomatically = true)
//    @Transactional
//    @Query(value = "select new com.sk.zl.model.meter.Meter(m, mg) from MeterEntity m, MeterGroupEntity mg where m.groupId=mg.id")
//    public List<Meter> findMeterInfo();
}
