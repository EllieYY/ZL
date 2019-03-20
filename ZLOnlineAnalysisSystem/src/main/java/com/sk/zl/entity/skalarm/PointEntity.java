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
                Objects.equals(cpid, that.cpid) &&
                Objects.equals(name, that.name) &&
                Objects.equals(kindId, that.kindId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(cpid, name, devid, kindId);
    }

    @Override
    public String toString() {
        return "PointEntity{" +
                "cpid='" + cpid + '\'' +
                ", name='" + name + '\'' +
                ", devid=" + devid +
                ", kindId=" + kindId +
                '}';
    }
}
