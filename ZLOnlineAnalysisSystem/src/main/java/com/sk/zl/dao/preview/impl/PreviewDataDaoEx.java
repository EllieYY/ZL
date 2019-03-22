package com.sk.zl.dao.preview.impl;


import com.sk.zl.dao.preview.HisalarmDao;
import com.sk.zl.dao.preview.NowAlarmDao;
import com.sk.zl.entity.skalarm.HisalarmEntity;
import com.sk.zl.entity.skalarm.KindEntity;
import com.sk.zl.entity.skalarm.NowAlarmEntity;
import com.sk.zl.model.meter.MeterRate;
import com.sk.zl.model.plant.PagePlantDataPreview;
import com.sk.zl.model.plant.PlantDataPreview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description : 预览数据的Dao层
 * @Author : Ellie
 * @Date : 2019/3/7
 */
@Repository
public class PreviewDataDaoEx {
    @Autowired
    HisalarmDao hisalarmDao;

    @Autowired
    NowAlarmDao nowAlarmDao;

    public PagePlantDataPreview getPlantData(List<Integer> plantIds, List<Integer> dataTypes, String keyword,
                                             Date startTime, Date endTime,
                                             Pageable pageable) {

        Specification<HisalarmEntity> specification = new Specification<HisalarmEntity>() {
            @Override
            public Predicate toPredicate(Root<HisalarmEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(criteriaBuilder.between(root.get("stime"), startTime, endTime));

                CriteriaBuilder.In<Integer> inIds = criteriaBuilder.in(root.get("devid"));
                for (Integer id : plantIds) {
                    inIds.value(id);
                }
                predicates.add(inIds);

                CriteriaBuilder.In<Integer> inTypes = criteriaBuilder.in(root.get("kindid"));
                for (Integer type : dataTypes) {
                    inTypes.value(type);
                }
                predicates.add(inTypes);

//                if (dataTypes.get(0) != 0) {
//                    predicates.add(criteriaBuilder.equal(root.get("kindid"), dataTypes.get(0)));
//                }

                predicates.add(criteriaBuilder.like(root.get("point").get("name").as(String.class), "%" + keyword + "%"));

                Predicate[] pred = new Predicate[predicates.size()];
                criteriaQuery.where(criteriaBuilder.and(predicates.toArray(pred)));
                return criteriaQuery.getRestriction();
            }
        };

        // 对查询结果进行转换
        Page<HisalarmEntity> page = hisalarmDao.findAll(specification, pageable);
        List<HisalarmEntity> entities = page.getContent();

        List<PlantDataPreview> models = entities.stream().collect(ArrayList::new, (list, item) -> {
            list.add(PlantDataPreview.fromEntity(item));
        }, ArrayList::addAll);

        PagePlantDataPreview result = new PagePlantDataPreview();
        result.setData(models);
        result.setTotalNum(page.getTotalElements());
        return result;
    }

    public List<PlantDataPreview> getNowAlarmByType(List<Integer> plantIds, List<Integer> dataTypes, String keyword) {
        Specification<NowAlarmEntity> specification = new Specification<NowAlarmEntity>() {
            @Override
            public Predicate toPredicate(Root<NowAlarmEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();

                CriteriaBuilder.In<Integer> inIds = criteriaBuilder.in(root.get("devid"));
                for (Integer id : plantIds) {
                    inIds.value(id);
                }
                predicates.add(inIds);

                CriteriaBuilder.In<Integer> inTypes = criteriaBuilder.in(root.get("kindId"));
                for (Integer type : dataTypes) {
                    inTypes.value(type);
                }
                predicates.add(inTypes);


                predicates.add(criteriaBuilder.like(root.get("point").get("name").as(String.class), "%" + keyword + "%"));

                Predicate[] pred = new Predicate[predicates.size()];
                criteriaQuery.where(criteriaBuilder.and(predicates.toArray(pred)));
                return criteriaQuery.getRestriction();
            }
        };

        List<NowAlarmEntity> entities = nowAlarmDao.findAll(specification);

        List<PlantDataPreview> models = entities.stream().collect(ArrayList::new, (list, item) -> {
            list.add(PlantDataPreview.fromEntity(item));
        }, ArrayList::addAll);

        return models;
    }
}
