package com.sk.zl.service.impl;

import com.sk.zl.dao.preview.PreviewDataDaoEx;
import com.sk.zl.model.plant.PlantDataPreview;
import com.sk.zl.model.request.ReDataPreview;
import com.sk.zl.service.DataPreviewService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description : 数据预览
 * @Author : Ellie
 * @Date : 2019/3/7
 */
@Service
public class DataPreviewServiceImpl implements DataPreviewService {
    @Resource
    PreviewDataDaoEx previewDataDaoEx;

    @Override
    public List<PlantDataPreview> getPlantData(ReDataPreview condition) {
        int pageNo = condition.getPageNo();
        int pageRows = condition.getPageRows();
        pageNo = pageNo < 0 ? 0 : pageNo - 1;
        pageRows = pageRows < 0 ? 5 : pageRows;
        Pageable pageable = PageRequest.of(pageNo, pageRows);

        return previewDataDaoEx.getPlantData(
                condition.getId(),
                condition.getDataType(),
                condition.getKeyword(),
                condition.getStartTime(),
                condition.getEndTime(),
                pageable);
    }

    @Override
    public List<PlantDataPreview> getWarningData(ReDataPreview condition) {
        int pageNo = condition.getPageNo();
        int pageRows = condition.getPageRows();
        pageNo = pageNo < 0 ? 0 : pageNo - 1;
        pageRows = pageRows < 0 ? 5 : pageRows;
        Pageable pageable = PageRequest.of(pageNo, pageRows);

        return previewDataDaoEx.getWarningData(
                condition.getId(),
                condition.getDataType(),
                condition.getKeyword(),
                condition.getStartTime(),
                condition.getEndTime(),
                pageable);
    }
}
