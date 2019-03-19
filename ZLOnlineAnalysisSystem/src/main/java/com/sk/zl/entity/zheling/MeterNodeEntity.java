package com.sk.zl.entity.zheling;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

/**
 * @Description : 电表小分组节点
 * @Author : Ellie
 * @Date : 2019/3/19
 */
@Entity
@Table(name = "tbl_meternode", catalog = "")
public class MeterNodeEntity {
    private int id;
    private String name;
    private String updateTime;
    private int type;

    private MeterEntity meter;
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "meterId")
    public MeterEntity getMeter() {
        return meter;
    }

    public void setMeter(MeterEntity meter) {
        this.meter = meter;
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "updateTime", nullable = false, length = 30)
    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "type", nullable = false)
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeterNodeEntity that = (MeterNodeEntity) o;
        return id == that.id &&
                type == that.type &&
                Objects.equals(name, that.name) &&
                Objects.equals(updateTime, that.updateTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, updateTime, type);
    }
}
