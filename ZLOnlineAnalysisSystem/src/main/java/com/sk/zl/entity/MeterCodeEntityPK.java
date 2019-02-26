package com.sk.zl.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @Description : TODO
 * @Author : Ellie
 * @Date : 2019/2/26
 */
public class MeterCodeEntityPK implements Serializable {
    private int meterId;
    private Timestamp time;

    @Column(name = "meterId", nullable = false)
    @Id
    public int getMeterId() {
        return meterId;
    }

    public void setMeterId(int meterId) {
        this.meterId = meterId;
    }

    @Column(name = "time", nullable = false)
    @Id
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeterCodeEntityPK that = (MeterCodeEntityPK) o;
        return meterId == that.meterId &&
                Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {

        return Objects.hash(meterId, time);
    }
}
