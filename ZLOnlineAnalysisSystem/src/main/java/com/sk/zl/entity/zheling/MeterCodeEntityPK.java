package com.sk.zl.entity.zheling;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @Description : 表码值联合主键
 * @Author : Ellie
 * @Date : 2019/2/26
 */
public class MeterCodeEntityPK implements Serializable {
    private int meterNodeId;
    private Date time;

    @Column(name = "meterNodeId", nullable = false)
    @Id
    public int getMeterNodeId() {
        return meterNodeId;
    }

    public void setMeterNodeId(int meterNodeId) {
        this.meterNodeId = meterNodeId;
    }

    @Column(name = "time", nullable = false)
    @Id
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeterCodeEntityPK that = (MeterCodeEntityPK) o;
        return meterNodeId == that.meterNodeId &&
                Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {

        return Objects.hash(meterNodeId, time);
    }
}
