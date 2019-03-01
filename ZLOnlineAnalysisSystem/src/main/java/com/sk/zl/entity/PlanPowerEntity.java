package com.sk.zl.entity;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

/**
 * @Description : TODO
 * @Author : Ellie
 * @Date : 2019/2/22
 */
@Entity
@Table(name = "tbl_planpower", catalog = "")
public class PlanPowerEntity {
    private int id;
    private int year;
    private double planPower;
    private Date updateTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "year", nullable = false)
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Basic
    @Column(name = "planPower", nullable = false, precision = 0)
    public double getPlanPower() {
        return planPower;
    }

    public void setPlanPower(double planPower) {
        this.planPower = planPower;
    }

    @Basic
    @UpdateTimestamp
    @Column(name = "updateTime", nullable = false)
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlanPowerEntity that = (PlanPowerEntity) o;
        return id == that.id &&
                year == that.year &&
                Double.compare(that.planPower, planPower) == 0 &&
                Objects.equals(updateTime, that.updateTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, year, planPower, updateTime);
    }
}
