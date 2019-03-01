package com.sk.zl.dao.setting;

import com.sk.zl.entity.LoginLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface LoginLogDao extends JpaRepository<LoginLogEntity, Integer> {
    List<LoginLogEntity> findByGroupAndUserAndLoginTimeIsBetween(
            String group,
            String user,
            Date startTime,
            Date endTime);
}
