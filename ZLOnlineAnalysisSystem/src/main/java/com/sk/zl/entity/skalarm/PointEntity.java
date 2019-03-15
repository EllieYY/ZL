package com.sk.zl.entity.skalarm;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * @Description : TODO
 * @Author : Ellie
 * @Date : 2019/3/15
 */
@Entity
@Table(name = "point", catalog = "")
public class PointEntity implements Serializable {
    private String cpid;
    private String name;
    private int devid;
    private byte level;
    private int alarmno;
    private byte type;
    private Integer kindId;

    @Id
    @Column(name = "cpid", nullable = false, length = 64)
    public String getCpid() {
        return cpid;
    }

    public void setCpid(String cpid) {
        this.cpid = cpid;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 128)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "devid", nullable = false)
    public int getDevid() {
        return devid;
    }

    public void setDevid(int devid) {
        this.devid = devid;
    }

    @Basic
    @Column(name = "level", nullable = false)
    public byte getLevel() {
        return level;
    }

    public void setLevel(byte level) {
        this.level = level;
    }

    @Basic
    @Column(name = "alarmno", nullable = false)
    public int getAlarmno() {
        return alarmno;
    }

    public void setAlarmno(int alarmno) {
        this.alarmno = alarmno;
    }

    @Basic
    @Column(name = "type", nullable = false)
    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    @Basic
    @Column(name = "kindId", nullable = true)
    public Integer getKindId() {
        return kindId;
    }

    public void setKindId(Integer kindId) {
        this.kindId = kindId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointEntity that = (PointEntity) o;
        return devid == that.devid &&
                level == that.level &&
                alarmno == that.alarmno &&
                type == that.type &&
                Objects.equals(cpid, that.cpid) &&
                Objects.equals(name, that.name) &&
                Objects.equals(kindId, that.kindId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(cpid, name, devid, level, alarmno, type, kindId);
    }

    @Override
    public String toString() {
        return "PointEntity{" +
                "cpid='" + cpid + '\'' +
                ", name='" + name + '\'' +
                ", devid=" + devid +
                ", level=" + level +
                ", alarmno=" + alarmno +
                ", type=" + type +
                ", kindId=" + kindId +
                '}';
    }
}
