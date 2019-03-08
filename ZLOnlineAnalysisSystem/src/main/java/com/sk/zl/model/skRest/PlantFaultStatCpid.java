package com.sk.zl.model.skRest;

import lombok.Data;

/**
 * @Description : 机组系统测点和故障测点cpid
 * @Author : Ellie
 * @Date : 2019/3/7
 */
@Data
public class PlantFaultStatCpid {
    private int id;
    private String faultStatPtCpid;
    private String systemStatPtCpid;
}
