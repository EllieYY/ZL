package com.sk.zl.entity.zheling;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @Description : 电表分组信息
 * @Author : Ellie
 * @Date : 2019/2/26
 */
@Entity
@Table(name = "tbl_metergroup", catalog = "")
public class MeterGroupEntity {
    private int id;
    private String name;
    private Set<MeterEntity> meterSet = new HashSet<MeterEntity>();

    @OneToMany(mappedBy = "group")
    public Set<MeterEntity> getMeterSet() {
        return meterSet;
    }

    public void setMeterSet(Set<MeterEntity> meterSet) {
        this.meterSet = meterSet;
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
    @Column(name = "name", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeterGroupEntity that = (MeterGroupEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }
}
