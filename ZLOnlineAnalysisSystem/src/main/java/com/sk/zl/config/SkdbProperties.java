package com.sk.zl.config;

import com.sk.zl.model.plant.PlantPointSnapshot;
import com.sk.zl.model.skRest.PlantSnapshotCpid;
import com.sk.zl.model.skRest.PointsCpid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Description : 数据库的基础连接信息
 * @Author : Ellie
 * @Date : 2019/2/20
 */
@Configuration
@ConfigurationProperties(prefix = "skdb")
@Data
public class SkdbProperties {
    String uri;
    String userName;
    String passWord;
    List<String> realTimeActivePowerPoints;
    List<PlantSnapshotCpid> plantsSnapshotPoints;

    @Value("${skdb.stationAlarmPoints.accident}")
    String accidentPoints;

    @Value("${skdb.stationAlarmPoints.glitches}")
    String glitchesPoints;
}
