package com.sk.zl.dao.preview;

import com.sk.zl.entity.skalarm.NowAlarmEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NowAlarmDao extends JpaRepository<NowAlarmEntity, Integer>, JpaSpecificationExecutor<NowAlarmEntity> {
}
