package com.sk.zl.dao.setting;

import com.sk.zl.entity.zheling.PlantEntity;
import com.sk.zl.entity.zheling.PlantDto;
import com.sk.zl.entity.zheling.PlantLiteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PlantDao extends JpaRepository<PlantEntity, Integer> {
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "update PlantEntity model set model.maintaining=:state where model.id=:id")
    int updateStateById(@Param("id") int id, @Param("state") Byte state);

    List<PlantEntity> findByMaintainingEquals(Byte maintaining);


    List<PlantDto> findAllByCapacity(double capacity);
//    List<PlantDto> findDistinctById();

}
