package com.sk.zl.dao.setting;

import com.sk.zl.entity.LoginLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface LoginLogDao extends JpaRepository<LoginLogEntity, Integer> {
    /** 按分组、用户名、时间范围查询 */
    List<LoginLogEntity> findByGroupAndUserAndLoginTimeIsBetween(
            String group,
            String user,
            Date startTime,
            Date endTime);

    /** 按分组和时间范围查询 */
    List<LoginLogEntity> findByGroupAndLoginTimeIsBetween(
            String group,
            Date startTime,
            Date endTime);

    /** 按用户名和时间范围查询 */
    List<LoginLogEntity> findByUserAndLoginTimeIsBetween(
            String user,
            Date startTime,
            Date endTime);

    /** 按时间范围查询 */
    List<LoginLogEntity> findByLoginTimeIsBetween(
            Date startTime,
            Date endTime);
}
