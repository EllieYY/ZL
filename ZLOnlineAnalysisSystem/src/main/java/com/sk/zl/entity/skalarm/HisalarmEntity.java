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
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

/**
 * @Description : 历史报警
 * @Author : Ellie
 * @Date : 2019/3/14
 */
@Entity
@Table(name = "hisalarm", catalog = "")
@IdClass(HisalarmEntityPK.class)
public class HisalarmEntity {
    private int id;
    private Date stime;
    private Date etime;
    private PointInfoEntity point;
    private byte type;
    private byte subtype;
    private int devid;
    private int kindid;

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
    @ManyToOne
    @JoinColumn(name = "cpid", nullable = false)
    public PointInfoEntity getPoint() {
        return point;
    }

    public void setPoint(PointInfoEntity point) {
        this.point = point;
    }

    @Basic
    @Column(name = "etime", nullable = false)
    public Date getEtime() {
        return etime;
    }

    public void setEtime(Date etime) {
        this.etime = etime;
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
    @Column(name = "devid", nullable = false)
    public int getDevid() {
        return devid;
    }

    public void setDevid(int devid) {
        this.devid = devid;
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
                type == that.type &&
                subtype == that.subtype &&
                devid == that.devid &&
                kindid == that.kindid &&
                Objects.equals(stime, that.stime) &&
                Objects.equals(etime, that.etime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stime, point, type, subtype, devid, etime, kindid);
    }
}
