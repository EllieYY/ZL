package com.sk.zl.dao.meter;

import com.sk.zl.entity.GenPowerEntity;
import com.sk.zl.entity.GenPowerEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;

public interface GenPowerDao extends JpaRepository<GenPowerEntity, GenPowerEntityPK>,
        JpaSpecificationExecutor<GenPowerEntity> {
}
