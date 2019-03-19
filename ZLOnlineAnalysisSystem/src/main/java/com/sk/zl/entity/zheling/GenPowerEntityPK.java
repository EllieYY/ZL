package com.sk.zl.entity.zheling;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @Description : 每日电量联合主键
 * @Author : Ellie
 * @Date : 2019/2/22
 */
public class GenPowerEntityPK implements Serializable {
    private Date time;
    private int meterNodeId;

    @Column(name = "time", nullable = false)
    @Id
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Column(name = "meterNodeId", nullable = false)
    @Id
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
        GenPowerEntityPK that = (GenPowerEntityPK) o;
        return meterNodeId == that.meterNodeId &&
                Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {

        return Objects.hash(time, meterNodeId);
    }
}
