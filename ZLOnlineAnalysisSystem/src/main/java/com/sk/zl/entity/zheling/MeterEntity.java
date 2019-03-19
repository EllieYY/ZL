package com.sk.zl.entity.zheling;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @Description : 电表小分组
 * @Author : Ellie
 * @Date : 2019/2/26
 */
@Entity
@Table(name = "tbl_meter", catalog = "")
public class MeterEntity {
    private int id;
    private String name;
    private Date updateTime;
    private MeterGroupEntity group;
    private List<MeterNodeEntity> nodeSet = new ArrayList<MeterNodeEntity>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupId")
    public MeterGroupEntity getGroup() {
        return group;
    }

    public void setGroup(MeterGroupEntity group) {
        this.group = group;
    }

    @OneToMany(mappedBy = "meter", fetch = FetchType.EAGER)
    @OrderBy("id ASC")
    public List<MeterNodeEntity> getNodeSet() {
        return nodeSet;
    }

    public void setNodeSet(List<MeterNodeEntity> nodeSet) {
        this.nodeSet = nodeSet;
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
        MeterEntity that = (MeterEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(updateTime, that.updateTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, updateTime);
    }
}
