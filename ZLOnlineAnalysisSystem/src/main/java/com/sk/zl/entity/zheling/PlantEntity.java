package com.sk.zl.entity.zheling;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

/**
 * @Description : 机组基础信息
 * @Author : Ellie
 * @Date : 2019/2/22
 */
@Entity
@Table(name = "tbl_plant", catalog = "")
public class PlantEntity {
    private int id;
    private String name;
    private Double capacity;
    private Byte maintaining;
    private Date updateTime;
    private MeterEntity meter;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name="meterId")
    public MeterEntity getMeter() {
        return meter;
    }

    public void setMeter(MeterEntity meter) {
        this.meter = meter;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "capacity", nullable = true, precision = 0)
    public Double getCapacity() {
        return capacity;
    }

    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }

    @Basic
    @Column(name = "maintaining", nullable = true)
    public Byte getMaintaining() {
        return maintaining;
    }

    public void setMaintaining(Byte maintaining) {
        this.maintaining = maintaining;
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
        PlantEntity that = (PlantEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(capacity, that.capacity) &&
                Objects.equals(maintaining, that.maintaining) &&
                Objects.equals(updateTime, that.updateTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, capacity, maintaining, updateTime);
    }
}
