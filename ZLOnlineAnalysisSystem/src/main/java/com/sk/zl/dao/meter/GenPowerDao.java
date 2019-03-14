package com.sk.zl.dao.meter;

import com.sk.zl.entity.zheling.GenPowerEntity;
import com.sk.zl.entity.zheling.GenPowerEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GenPowerDao extends JpaRepository<GenPowerEntity, GenPowerEntityPK>,
        JpaSpecificationExecutor<GenPowerEntity> {
}
