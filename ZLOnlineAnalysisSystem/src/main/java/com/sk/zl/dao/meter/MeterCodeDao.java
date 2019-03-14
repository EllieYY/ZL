package com.sk.zl.dao.meter;

import com.sk.zl.entity.zheling.MeterCodeEntity;
import com.sk.zl.entity.zheling.MeterCodeEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MeterCodeDao extends JpaRepository<MeterCodeEntity, MeterCodeEntityPK>,
        JpaSpecificationExecutor<MeterCodeEntity> {
}
