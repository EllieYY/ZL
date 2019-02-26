package com.sk.zl.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @Description : TODO
 * @Author : Ellie
 * @Date : 2019/2/22
 */
@Entity
@Table(name = "tbl_genpower", catalog = "")
@IdClass(GenPowerEntityPK.class)
public class GenPowerEntity {
    private Timestamp time;
    private Double value;
    private int meterId;

    @Id
    @Column(name = "time", nullable = false)
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Basic
    @Column(name = "value", nullable = true, precision = 0)
    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Id
    @Column(name = "meterId", nullable = false)
    public int getMeterId() {
        return meterId;
    }

    public void setMeterId(int meterId) {
        this.meterId = meterId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenPowerEntity that = (GenPowerEntity) o;
        return meterId == that.meterId &&
                Objects.equals(time, that.time) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {

        return Objects.hash(time, value, meterId);
    }
}
