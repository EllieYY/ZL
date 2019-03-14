package com.sk.zl.dao.preview.impl;


import com.sk.zl.entity.skalarm.HisalarmEntity;
import com.sk.zl.model.plant.PlantDataPreview;
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
//    @Autowired
//    HisalarmDao hisalarmDao;

    public List<PlantDataPreview> getPlantData(int plantId, int dataType, String keyword,
                                               Date startTime, Date endTime,
                                               Pageable pageable) {
        Specification<HisalarmEntity> specification = new Specification<HisalarmEntity>() {
            @Override
            public Predicate toPredicate(Root<HisalarmEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(criteriaBuilder.between(root.get("stime"), startTime, endTime));

                if (plantId != 0) {
                    predicates.add(criteriaBuilder.equal(root.get("devid"), plantId));
                }

                if (dataType != 0) {
                    predicates.add(criteriaBuilder.equal(root.get("kindid"), dataType));
                }

                predicates.add(criteriaBuilder.like(root.get("point").get("name").as(String.class), "%" + keyword + "%"));

                Predicate[] pred = new Predicate[predicates.size()];
                criteriaQuery.where(criteriaBuilder.and(predicates.toArray(pred)));
                return criteriaQuery.getRestriction();
            }
        };

        // TODO：对查询结果进行转换
//        Page<HisalarmEntity> list = hisalarmDao.findAll(specification, pageable);
//        System.out.println(list.getContent());

        // test result
        List<PlantDataPreview> result = new ArrayList<PlantDataPreview>();
        result.add(new PlantDataPreview());
        result.add(new PlantDataPreview());

        return result;
    }


    public List<PlantDataPreview> getWarningData(int plantId, int dataType, String keyword,
                                                 Date startTime, Date endTime,
                                                 Pageable pageable) {
        // TODO: 条件筛选

        // test result
        List<PlantDataPreview> result = new ArrayList<PlantDataPreview>();
        result.add(new PlantDataPreview());
        result.add(new PlantDataPreview());

        return result;
    }


}
