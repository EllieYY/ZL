package com.sk.zl.entity.skalarm;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

/**
 * @Description : TODO
 * @Author : Ellie
 * @Date : 2019/3/22
 */
@Entity
@Table(name = "nowalarm", catalog = "")
public class NowAlarmEntity {
    private int id;
    private Date stime;
    private Date etime;
    private short year;
    private short month;
    private long day;
    private String cpid;
    private String message;
    private int devid;
    private int alarmno;
    private int kindId;

    /** 外键关联查询 */
    private PointEntity point;
    @ManyToOne
    @JoinColumn(name = "cpid",insertable = false, updatable = false)
    public PointEntity getPoint() {
        return point;
    }

    public void setPoint(PointEntity point) {
        this.point = point;
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "stime", nullable = false)
    public Date getStime() {
        return stime;
    }

    public void setStime(Date stime) {
        this.stime = stime;
    }

    @Basic
    @Column(name = "etime", nullable = true)
    public Date getEtime() {
        return etime;
    }

    public void setEtime(Date etime) {
        this.etime = etime;
    }

    @Basic
    @Column(name = "year", nullable = false)
    public short getYear() {
        return year;
    }

    public void setYear(short year) {
        this.year = year;
    }

    @Basic
    @Column(name = "month", nullable = false)
    public short getMonth() {
        return month;
    }

    public void setMonth(short month) {
        this.month = month;
    }

    @Basic
    @Column(name = "day", nullable = false)
    public long getDay() {
        return day;
    }

    public void setDay(long day) {
        this.day = day;
    }

    @Basic
    @Column(name = "cpid", nullable = false, length = 64)
    public String getCpid() {
        return cpid;
    }

    public void setCpid(String cpid) {
        this.cpid = cpid;
    }

    @Basic
    @Column(name = "message", nullable = false, length = 255)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
    @Column(name = "alarmno", nullable = false)
    public int getAlarmno() {
        return alarmno;
    }

    public void setAlarmno(int alarmno) {
        this.alarmno = alarmno;
    }

    @Basic
    @Column(name = "kindId", nullable = false)
    public int getKindId() {
        return kindId;
    }

    public void setKindId(int kindId) {
        this.kindId = kindId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NowAlarmEntity that = (NowAlarmEntity) o;
        return id == that.id &&
                year == that.year &&
                month == that.month &&
                devid == that.devid &&
                alarmno == that.alarmno &&
                kindId == that.kindId &&
                Objects.equals(stime, that.stime) &&
                Objects.equals(etime, that.etime) &&
                Objects.equals(day, that.day) &&
                Objects.equals(cpid, that.cpid) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, stime, etime, year, month, day, cpid, message, devid, alarmno, kindId);
    }
}
