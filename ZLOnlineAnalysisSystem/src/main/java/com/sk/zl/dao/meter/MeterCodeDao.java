package com.sk.zl.dao.meter;

import com.sk.zl.entity.MeterCodeEntity;
import com.sk.zl.entity.MeterCodeEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeterCodeDao extends JpaRepository<MeterCodeEntity, MeterCodeEntityPK> {
}
