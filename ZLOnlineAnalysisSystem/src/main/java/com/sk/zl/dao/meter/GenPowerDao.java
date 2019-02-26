package com.sk.zl.dao.meter;

import com.sk.zl.entity.GenPowerEntity;
import com.sk.zl.entity.GenPowerEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenPowerDao extends JpaRepository<GenPowerEntity, GenPowerEntityPK> {
}
