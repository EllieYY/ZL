package com.sk.zl.entity.skalarm;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
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


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Id
    @Column(name = "stime", nullable = false)
    public Date getStime() {
        return stime;
    }

    public void setStime(Date stime) {
        this.stime = stime;
    }

    @Id
    @Column(name = "cpid", nullable = false, length = 64)
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
