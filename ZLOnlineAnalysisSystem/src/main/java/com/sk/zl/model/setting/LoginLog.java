package com.sk.zl.model.setting;

import com.sk.zl.entity.LoginLogEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @Description : 登录日志
 * @Author : Ellie
 * @Date : 2019/2/25
 */
@Data
public class LoginLog {
    private String group;
    private String user;
    private Timestamp loginTime;

    public static LoginLog fromEntity(LoginLogEntity entity) {
        LoginLog model = new LoginLog();
        model.setGroup(entity.getGroup());
        model.setUser(entity.getUser());
        model.setLoginTime(entity.getLoginTime());
        return model;
    }

    public LoginLogEntity toEntity() {
        LoginLogEntity entity = new LoginLogEntity();
        entity.setGroup(group);
        entity.setUser(user);
        entity.setLoginTime(loginTime);

        return entity;
    }
}
