package com.sk.zl.entity.skalarm;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

/**
 * @Description : TODO
 * @Author : Ellie
 * @Date : 2019/3/15
 */
@Entity
@Table(name = "hisalarm", catalog = "")
@IdClass(HisalarmEntityPK.class)
public class HisalarmEntity {

    private int id;
    private Date stime;
    private Date etime;
    private short year;
    private short month;
    private long day;
    private int ipid;
    private String cpid;
    private byte type;
    private byte subtype;
    private String message;
    private short despid;
    private int devid;
    private int alarmno;
    private int kindid;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Id
    @Column(name = "stime", nullable = false)
    public Date getStime() {
        return stime;
    }

    public void setStime(Date stime) {
        this.stime = stime;
    }

    @Id
    @Column(name = "cpid", nullable = false, length = 64)
    public String getCpid() {
        return cpid;
    }

    public void setCpid(String cpid) {
        this.cpid = cpid;
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
    @Column(name = "ipid", nullable = false)
    public int getIpid() {
        return ipid;
    }

    public void setIpid(int ipid) {
        this.ipid = ipid;
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
    @Column(name = "subtype", nullable = false)
    public byte getSubtype() {
        return subtype;
    }

    public void setSubtype(byte subtype) {
        this.subtype = subtype;
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
    @Column(name = "despid", nullable = false)
    public short getDespid() {
        return despid;
    }

    public void setDespid(short despid) {
        this.despid = despid;
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
    @Column(name = "kindid", nullable = false)
    public int getKindid() {
        return kindid;
    }

    public void setKindid(int kindid) {
        this.kindid = kindid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HisalarmEntity that = (HisalarmEntity) o;
        return id == that.id &&
                year == that.year &&
                month == that.month &&
                ipid == that.ipid &&
                type == that.type &&
                subtype == that.subtype &&
                despid == that.despid &&
                devid == that.devid &&
                alarmno == that.alarmno &&
                kindid == that.kindid &&
                Objects.equals(stime, that.stime) &&
                Objects.equals(etime, that.etime) &&
                Objects.equals(day, that.day) &&
                Objects.equals(cpid, that.cpid) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, cpid, stime, etime, year, month, day, ipid, type, subtype, message, despid, devid, alarmno, kindid);
    }


    @Override
    public String toString() {
        return "HisalarmEntity{" +
                "id=" + id +
                ", stime=" + stime +
                ", etime=" + etime +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", ipid=" + ipid +
                ", cpid='" + cpid + '\'' +
                ", type=" + type +
                ", subtype=" + subtype +
                ", message='" + message + '\'' +
                ", despid=" + despid +
                ", devid=" + devid +
                ", alarmno=" + alarmno +
                ", kindid=" + kindid +
                ", point=" + point +
                '}';
    }
}
