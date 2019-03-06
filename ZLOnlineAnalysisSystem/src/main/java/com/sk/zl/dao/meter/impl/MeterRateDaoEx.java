package com.sk.zl.dao.meter.impl;

import com.sk.zl.aop.excption.DataDaoException;
import com.sk.zl.dao.meter.MeterRateDao;
import com.sk.zl.entity.MeterRateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Description : Dao扩展
 * @Author : Ellie
 * @Date : 2019/3/5
 */
@Repository
public class MeterRateDaoEx {
    @Autowired
    MeterRateDao meterRateDao;

    public double findByMeterIdAndTime(int meterId, Date time) {
        List<MeterRateEntity> rateEntities = meterRateDao.findAll(new Specification<MeterRateEntity>() {
            @Override
            public Predicate toPredicate(Root<MeterRateEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("endTime"), time));
                predicates.add(criteriaBuilder.lessThan(root.get("startTime"), time));
                predicates.add(criteriaBuilder.equal(root.get("meter").get("id"), meterId));

                Predicate[] pred = new Predicate[predicates.size()];
                criteriaQuery.where(criteriaBuilder.and(predicates.toArray(pred)));
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("updateTime")));
                return criteriaQuery.getRestriction();
            }
        });

        if (rateEntities.size() < 1) {
            return Double.MAX_VALUE;
        }

        return rateEntities.get(0).getRate();
    }

    public MeterRateEntity save(MeterRateEntity entity) {
        return meterRateDao.save(entity);
    }

    public MeterRateEntity findById(int id) {
        return meterRateDao.findById(id);
    }

    public int updateById(int id, double rate, Date start, Date end) {
        return meterRateDao.updateRateById(id, rate, start, end);
    }
}
