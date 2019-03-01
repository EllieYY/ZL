package com.sk.zl.model.skRest;

import lombok.Data;

/**
 * @Description : 机组状态信息对应测点结构
 * @Author : Ellie
 * @Date : 2019/3/1
 */
@Data
public class PlantSnapshotCpid {
    private int id;
    private String activePower;
    private String reactivePower;
    private String guideVaneOpening;
}
