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
 * @Date : 2019/2/26
 */
@Entity
@Table(name = "tbl_metercode", catalog = "")
@IdClass(MeterCodeEntityPK.class)
public class MeterCodeEntity {
    private int meterId;
    private Timestamp time;
    private Double code;

    @Id
    @Column(name = "meterId", nullable = false)
    public int getMeterId() {
        return meterId;
    }

    public void setMeterId(int meterId) {
        this.meterId = meterId;
    }

    @Id
    @Column(name = "time", nullable = false)
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
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
        return meterId == that.meterId &&
                Objects.equals(time, that.time) &&
                Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {

        return Objects.hash(meterId, time, code);
    }
}
