package com.sk.zl.dao.meter;

import com.sk.zl.entity.MeterGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeterGroup extends JpaRepository<MeterGroupEntity, Integer> {
}
