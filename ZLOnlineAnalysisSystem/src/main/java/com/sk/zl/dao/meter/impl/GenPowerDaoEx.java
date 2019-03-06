package com.sk.zl.dao.meter.impl;

import com.sk.zl.dao.meter.GenPowerDao;
import com.sk.zl.entity.GenPowerEntity;
import org.hibernate.internal.CriteriaImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

/**
 * @Description : 电量查询的扩展实现，复杂查询
 * @Author : Ellie
 * @Date : 2019/3/5
 */
@Repository
public class GenPowerDaoEx {
    @Autowired
    private GenPowerDao genPowerDao;

    public List<GenPowerEntity> findByMeterIdAndTime(List<Integer> meterIds, Date startTime, Date endTime) {
        List<GenPowerEntity> genPowerEntities = genPowerDao.findAll(new Specification<GenPowerEntity>() {
            @Override
            public Predicate toPredicate(Root<GenPowerEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate p1 = criteriaBuilder.greaterThanOrEqualTo(root.get("time"), startTime);
                Predicate p2 = criteriaBuilder.lessThan(root.get("time"), endTime);

                CriteriaBuilder.In<Integer> inIds = criteriaBuilder.in(root.get("meterId"));
                for (Integer id : meterIds) {
                    inIds.value(id);
                }

                return criteriaBuilder.and(p1, p2, inIds);
            }
        });
        return genPowerEntities;
    }

    public List<GenPowerEntity> findByMeterIdAndTime(int meterId, Date startTime, Date endTime) {
        List<GenPowerEntity> genPowerEntities = genPowerDao.findAll(new Specification<GenPowerEntity>() {
            @Override
            public Predicate toPredicate(Root<GenPowerEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate p1 = criteriaBuilder.greaterThanOrEqualTo(root.get("time"), startTime);
                Predicate p2 = criteriaBuilder.lessThan(root.get("time"), endTime);
                Predicate p3 = criteriaBuilder.equal(root.get("meterId"), meterId);

                return criteriaBuilder.and(p1, p2, p3);
            }
        });
        return genPowerEntities;
    }

    public GenPowerEntity saveOne(double value, Date time, int meterId) {
        GenPowerEntity entity = new GenPowerEntity();
        entity.setMeterId(meterId);
        entity.setTime(time);
        entity.setValue(value);

        return genPowerDao.save(entity);
    }

}
