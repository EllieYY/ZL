package com.sk.zl.dao.preview;

import com.sk.zl.entity.skalarm.HisalarmEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HisalarmDao extends JpaRepository<HisalarmEntity, Integer>, JpaSpecificationExecutor<HisalarmEntity> {
}
