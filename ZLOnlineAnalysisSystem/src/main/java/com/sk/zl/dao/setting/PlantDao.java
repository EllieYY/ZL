package com.sk.zl.dao.setting;

import com.sk.zl.entity.PlantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PlantDao extends JpaRepository<PlantEntity, Integer> {
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "update PlantEntity model set model.maintaining=:state where model.id=:id")
    int updateStateById(@Param("id") int id, @Param("state") Byte state);
}
