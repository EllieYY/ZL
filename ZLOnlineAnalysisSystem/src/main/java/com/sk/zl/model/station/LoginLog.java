package com.sk.zl.model.station;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sk.zl.entity.zheling.LoginLogEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * @Description : 登录日志
 * @Author : Ellie
 * @Date : 2019/2/25
 */
@Data
@NoArgsConstructor
public class LoginLog {
    @JsonProperty("type")
    private String type;
    @JsonProperty("group")
    private String group;
    @JsonProperty("user")
    private String user;
    @JsonProperty("loginTime")
    private Date loginTime;
    @JsonProperty("endTime")
    private Date logoutTime;

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

    @Override
    public String toString() {
        return "LoginLog{" +
                "type='" + type + '\'' +
                ", group='" + group + '\'' +
                ", user='" + user + '\'' +
                ", loginTime=" + loginTime +
                ", logoutTime=" + logoutTime +
                '}';
    }
}
