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
 * @Description : 电表表码值
 * @Author : Ellie
 * @Date : 2019/2/26
 */
@Entity
@Table(name = "tbl_metercode", catalog = "")
@IdClass(MeterCodeEntityPK.class)
public class MeterCodeEntity {
    private int meterNodeId;
    private Date time;
    private Double code;

    @Id
    @Column(name = "meterNodeId", nullable = false)
    public int getMeterNodeId() {
        return meterNodeId;
    }

    public void setMeterNodeId(int meterNodeId) {
        this.meterNodeId = meterNodeId;
    }

    @Id
    @Column(name = "time", nullable = false)
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Basic
    @Column(name = "code", nullable = true, precision = 0)
    public Double getCode() {
        return code;
    }

    public void setCode(Double code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeterCodeEntity that = (MeterCodeEntity) o;
        return meterNodeId == that.meterNodeId &&
                Objects.equals(time, that.time) &&
                Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {

        return Objects.hash(meterNodeId, time, code);
    }

    @Override
    public String toString() {
        return "MeterCodeEntity{" +
                "meterNodeId=" + meterNodeId +
                ", time=" + time +
                ", code=" + code +
                '}';
    }
}
