package com.sk.zl.dao.meter;

import com.sk.zl.entity.PlanPowerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanPowerDao extends JpaRepository<PlanPowerEntity, Integer> {
    List<PlanPowerEntity> findByYear(int year);
}
