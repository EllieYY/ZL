package com.sk.zl.service.impl;

import com.sk.zl.config.skdb.AnalogPoints;
import com.sk.zl.config.skdb.PlantsAnalog;
import com.sk.zl.dao.preview.impl.PreviewDataDaoEx;
import com.sk.zl.dao.skdb.PointInfoDao;
import com.sk.zl.model.plant.PlantDataPreview;
import com.sk.zl.model.plant.PlantFaultPointsStat;
import com.sk.zl.model.plant.PlantRunningAnalysis;
import com.sk.zl.model.plant.PlantTrend;
import com.sk.zl.model.request.ReDataPreview;
import com.sk.zl.model.request.RePlantAnalogs;
import com.sk.zl.model.request.RePlantTrend;
import com.sk.zl.model.request.ReRunningTimeAnalysis;
import com.sk.zl.service.DataPreviewService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description : 数据预览类服务
 * @Author : Ellie
 * @Date : 2019/3/7
 */
@Service
public class DataPreviewServiceImpl implements DataPreviewService {
    @Resource
    PreviewDataDaoEx previewDataDaoEx;

    @Resource
    private PlantsAnalog plantsAnalog;

    @Resource
    private PointInfoDao pointInfoDao;

    @Override
    public List<PlantDataPreview> getWarningData(ReDataPreview condition) {
        int pageNo = condition.getPageNo();
        int pageRows = condition.getPageRows();
        pageNo = pageNo < 0 ? 0 : pageNo - 1;
        pageRows = pageRows < 0 ? 5 : pageRows;
        Pageable pageable = PageRequest.of(pageNo, pageRows);

        System.out.println(condition);

        return previewDataDaoEx.getPlantData(
                condition.getId(),
                condition.getDataType(),
                condition.getKeyword(),
                condition.getStartTime(),
                condition.getEndTime(),
                pageable);
    }

    @Override
    public List<PlantFaultPointsStat> getPlantFaultsStat() {
        return pointInfoDao.findPlantFaultPoints();
    }

    @Override
    public List<String> getAnalogPointsById(RePlantAnalogs rePlantAnalogs) {
        /** 计算分页范围 */
        int pageNo = rePlantAnalogs.getPageNo();
        pageNo = pageNo < 1 ? 1 : pageNo;
        int pageRows = rePlantAnalogs.getPageRows();
        pageRows = pageRows < 1 ? 5 : pageRows;

        int startPage = (pageNo - 1) * pageRows;
        int endPage = pageNo * pageRows;

        /** 按分页范围添加内容
         * 将所有内容添加到list中，然后再求子集这种方式比较消耗空间，
         * 在添加集合到结果集的时候进行判断，只添加处在分页范围内的结果 */
        List<String> result = new ArrayList<String>();
        int id = rePlantAnalogs.getId();
        int startSize = 0;
        int endSize = 0;
        for (AnalogPoints plant: plantsAnalog.getPlants()) {
            if (startSize > endPage) {
                break;
            }

            if (plant.getId() == id || id == 0) {
                List<String> cpids = plant.getPoints();
                endSize = startSize + cpids.size();

                /** 求当前集合与分页集合范围的交集 */
                int startIdx = startPage > startSize ? startPage : startSize;
                int endIdx = endPage < endSize ? endPage : endSize;

                if (startIdx >= endIdx) {
                    startIdx = 0;
                    endIdx = 0;
                }

                result.addAll(cpids.subList(startIdx, endIdx));
                startSize = endSize;
            }
        }

        return result;
    }

    @Override
    public List<PlantTrend> getPlantTrend(RePlantTrend condition) {
        return pointInfoDao.findPlantTrendByCondition(condition);
    }

    @Override
    public List<PlantRunningAnalysis> getRunningTimeAnalysis(ReRunningTimeAnalysis condition) {
        return pointInfoDao.findRunningTimeInfoByCondition(condition);
    }
}
