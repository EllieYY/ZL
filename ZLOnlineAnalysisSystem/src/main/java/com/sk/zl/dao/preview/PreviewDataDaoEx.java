package com.sk.zl.dao.preview;

import com.sk.zl.dao.meter.MeterRateDao;
import com.sk.zl.entity.zheling.MeterRateEntity;
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
    // TODO:最终数据表暂时未定，先使用电表倍率来进行测试
    @Autowired
    MeterRateDao meterRateDao;

    public List<PlantDataPreview> getPlantData(int plantId, int dataType, String keyword,
                                               Date startTime, Date endTime,
                                               Pageable pageable) {
        Specification<MeterRateEntity> specification = new Specification<MeterRateEntity>() {
            @Override
            public Predicate toPredicate(Root<MeterRateEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                // TODO:添加查询条件

                return criteriaQuery.getRestriction();
            }
        };

        // TODO：对查询结果进行转换
        Page<MeterRateEntity> list = meterRateDao.findAll(specification, pageable);
        System.out.println(list.getContent());

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
