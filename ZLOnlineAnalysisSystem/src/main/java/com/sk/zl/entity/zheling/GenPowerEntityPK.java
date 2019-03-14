package com.sk.zl.entity.zheling;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @Description : TODO
 * @Author : Ellie
 * @Date : 2019/2/22
 */
public class GenPowerEntityPK implements Serializable {
    private Date time;
    private int meterId;

    @Column(name = "time", nullable = false)
    @Id
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Column(name = "meterId", nullable = false)
    @Id
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
        GenPowerEntityPK that = (GenPowerEntityPK) o;
        return meterId == that.meterId &&
                Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {

        return Objects.hash(time, meterId);
    }
}
