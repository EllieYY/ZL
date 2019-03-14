package com.sk.zl.entity.zheling;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

/**
 * @Description : TODO
 * @Author : Ellie
 * @Date : 2019/2/22
 */
@Entity
@Table(name = "tbl_loginlog", catalog = "")
public class LoginLogEntity {

    private int id;
    private String group;
    private String user;
    private Date loginTime;
    private Date updateTime;
    private Byte deleted;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "[group]", nullable = false, length = 255)
    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Basic
    @Column(name = "user", nullable = false, length = 255)
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }


    @Basic
    @Column(name = "loginTime", nullable = false)
    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    @Basic
    @UpdateTimestamp
    @Column(name = "updateTime", nullable = false)
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "deleted", nullable = true)
    public Byte getDeleted() {
        return deleted;
    }

    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginLogEntity that = (LoginLogEntity) o;
        return id == that.id &&
                Objects.equals(group, that.group) &&
                Objects.equals(user, that.user) &&
                Objects.equals(loginTime, that.loginTime) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(deleted, that.deleted);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, group, user, loginTime, updateTime, deleted);
    }


//    @Override
//    public String toString() {
//        return "{" +
//                "id=" + id +
//                ", user='" + user + '\'' +
//                ", group=" + group +
//                ", updatetime=" + updateTime +
//                '}';
//    }


}
