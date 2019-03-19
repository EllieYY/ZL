package com.sk.zl.dao.meter.impl;

import com.sk.zl.dao.meter.GenPowerDao;
import com.sk.zl.entity.zheling.GenPowerEntity;
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

    public List<GenPowerEntity> findByNodeIdsAndTime(List<Integer> nodeIds, Date startTime, Date endTime) {
        List<GenPowerEntity> genPowerEntities = genPowerDao.findAll(new Specification<GenPowerEntity>() {
            @Override
            public Predicate toPredicate(Root<GenPowerEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate p1 = criteriaBuilder.greaterThanOrEqualTo(root.get("time"), startTime);
                Predicate p2 = criteriaBuilder.lessThan(root.get("time"), endTime);

                CriteriaBuilder.In<Integer> inIds = criteriaBuilder.in(root.get("meterNodeId"));
                for (Integer id : nodeIds) {
                    inIds.value(id);
                }

                return criteriaBuilder.and(p1, p2, inIds);
            }
        });
        return genPowerEntities;
    }

//    public List<GenPowerEntity> findByMeterIdAndTime(int meterId, Date startTime, Date endTime) {
//        List<GenPowerEntity> genPowerEntities = genPowerDao.findAll(new Specification<GenPowerEntity>() {
//            @Override
//            public Predicate toPredicate(Root<GenPowerEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
//                Predicate p1 = criteriaBuilder.greaterThanOrEqualTo(root.get("time"), startTime);
//                Predicate p2 = criteriaBuilder.lessThan(root.get("time"), endTime);
////                Predicate p3 = criteriaBuilder.equal(root.get("meterId"), meterId);
//
//                CriteriaBuilder.In<Integer> inIds = criteriaBuilder.in(root.get("meterNodeId"));
//                Set<MeterNodeEntity> nodes = meterDao.findById(meterId).get().getNodeSet();
//                for (MeterNodeEntity node : nodes) {
//                    inIds.value(node.getId());
//                }
//
//
//                return criteriaBuilder.and(p1, p2, inIds);
//            }
//        });
//        return genPowerEntities;
//    }

    public GenPowerEntity saveOne(double value, Date time, int meterNodeId) {
        GenPowerEntity entity = new GenPowerEntity();
        entity.setMeterNodeId(meterNodeId);
        entity.setTime(time);
        entity.setValue(value);

        return genPowerDao.save(entity);
    }

}
