package com.sk.zl.dao.meter.impl;

import com.sk.zl.dao.meter.MeterCodeDao;
import com.sk.zl.entity.GenPowerEntity;
import com.sk.zl.entity.MeterCodeEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description : TODO
 * @Author : Ellie
 * @Date : 2019/3/5
 */
@Repository
public class MeterCodeDaoEx {
    @Resource
    MeterCodeDao meterCodeDao;

    public List<MeterCodeEntity> findByMeterAndTimeDec(int meterId, Date startTime, Date endTime) {
        List<MeterCodeEntity> entities = meterCodeDao.findAll(new Specification<MeterCodeEntity>() {
            @Override
            public Predicate toPredicate(Root<MeterCodeEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("time"), startTime));
                predicates.add(criteriaBuilder.lessThan(root.get("time"), endTime));
                predicates.add(criteriaBuilder.equal(root.get("meterId"), meterId));

                Predicate[] pred = new Predicate[predicates.size()];
                criteriaQuery.where(criteriaBuilder.and(predicates.toArray(pred)));
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("time")));
                return criteriaQuery.getRestriction();
            }
        });

        return entities;
    }

    public List<MeterCodeEntity> saveAll(List<MeterCodeEntity> entities) {
        return meterCodeDao.saveAll(entities);
    }
}
