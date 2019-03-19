package com.sk.zl.entity.zheling;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

/**
 * @Description : 每日电量
 * @Author : Ellie
 * @Date : 2019/2/22
 */
@Entity
@Table(name = "tbl_genpower", catalog = "")
@IdClass(GenPowerEntityPK.class)
public class GenPowerEntity {
    private Date time;
    private Double value;
    private int meterNodeId;

    @Id
    @Column(name = "time", nullable = false)
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
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
    @Column(name = "meterNodeId", nullable = false)
    public int getMeterNodeId() {
        return meterNodeId;
    }

    public void setMeterNodeId(int meterNodeId) {
        this.meterNodeId = meterNodeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenPowerEntity that = (GenPowerEntity) o;
        return meterNodeId == that.meterNodeId &&
                Objects.equals(time, that.time) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {

        return Objects.hash(time, value, meterNodeId);
    }

    @Override
    public String toString() {
        return "GenPowerEntity{" +
                "time=" + time +
                ", value=" + value +
                ", meterNodeId=" + meterNodeId +
                '}';
    }
}
