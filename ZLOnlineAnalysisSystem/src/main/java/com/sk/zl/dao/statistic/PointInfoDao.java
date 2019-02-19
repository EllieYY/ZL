package com.sk.zl.dao.statistic;

import com.sk.zl.entity.PointInfo;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PointInfoDao {

    List<PointInfo> queryPointInfoByInsertTimeAndType(Date insertTime, String type);

    // 这里返回一个存储后的对象，来处理一些存储时生成的字段（比如自动生成的id）
    PointInfo savePointInfo(PointInfo pointInfo);

}
