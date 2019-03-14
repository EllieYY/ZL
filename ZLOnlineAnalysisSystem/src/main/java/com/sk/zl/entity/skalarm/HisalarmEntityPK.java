package com.sk.zl.entity.skalarm;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

/**
 * @Description : TODO
 * @Author : Ellie
 * @Date : 2019/3/14
 */
public class HisalarmEntityPK implements Serializable {
    private int id;
    private Date stime;
    private String cpid;

    @Column(name = "id", nullable = false)
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "stime", nullable = false)
    @Id
    public Date getStime() {
        return stime;
    }

    public void setStime(Date stime) {
        this.stime = stime;
    }

    @Column(name = "cpid", nullable = false, length = 64)
    @Id
    public String getCpid() {
        return cpid;
    }

    public void setCpid(String cpid) {
        this.cpid = cpid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HisalarmEntityPK that = (HisalarmEntityPK) o;
        return id == that.id &&
                Objects.equals(stime, that.stime) &&
                Objects.equals(cpid, that.cpid);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, stime, cpid);
    }
}
