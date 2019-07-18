package com.sk.zl.entity.zheling;


import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * @Description : 机组基础信息
 * @Author : Ellie
 * @Date : 2019/2/22
 */
@Entity
@Table(name = "tbl_plant", catalog = "")
public class PlantLiteEntity {
    private int id;
    private String name;
    private Byte maintaining;

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


    @Basic
    @Column(name = "maintaining", nullable = true)
    public Byte getMaintaining() {
        return maintaining;
    }

    public void setMaintaining(Byte maintaining) {
        this.maintaining = maintaining;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlantLiteEntity that = (PlantLiteEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(maintaining, that.maintaining) ;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, maintaining);
    }

    @Override
    public String toString() {
        return "PlantEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", maintaining=" + maintaining +
                '}';
    }
}
