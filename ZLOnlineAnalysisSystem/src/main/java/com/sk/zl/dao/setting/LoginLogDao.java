package com.sk.zl.dao.setting;

import com.sk.zl.entity.LoginLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginLogDao extends JpaRepository<LoginLogEntity, Integer> {
}
