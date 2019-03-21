package com.sk.zl.model.skRest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description : 测点详细信息
 * @Author : Ellie
 * @Date : 2019/3/20
 */
@Data
@NoArgsConstructor
public class PointDetail {
    @JsonProperty("ipid")
    private long ipid;

    @JsonProperty("time")
    private Date time;

    @JsonProperty("v")
    private double vlaue;

    @JsonProperty("s")
    private int state;

    @JsonProperty("isLLL")
    private boolean isLLL;

    @JsonProperty("isLL")
    private boolean isLL;

    @JsonProperty("isL")
    private boolean isL;

    @JsonProperty("isH")
    private boolean isH;

    @JsonProperty("isHH")
    private boolean isHH;

    @JsonProperty("isHHH")
    private boolean isHHH;


    public long getIpid() {
        return ipid;
    }

    public void setIpid(long ipid) {
        this.ipid = ipid;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }


    public boolean getIsLLL() {
        return isLLL;
    }

    public void setIsLLL(boolean LLL) {
        isLLL = LLL;
    }

    public boolean getIsLL() {
        return isLL;
    }

    public void setIsLL(boolean LL) {
        isLL = LL;
    }

    public boolean getIsL() {
        return isL;
    }

    public void setIsL(boolean l) {
        isL = l;
    }

    public boolean getIsH() {
        return isH;
    }

    public void setIsH(boolean h) {
        isH = h;
    }

    public boolean getIsHH() {
        return isHH;
    }

    public void setIsHH(boolean HH) {
        isHH = HH;
    }

    public boolean getIsHHH() {
        return isHHH;
    }

    public void setIsHHH(boolean HHH) {
        isHHH = HHH;
    }

    @JsonIgnore
    public boolean isWarnOn() {
        return (isLLL || isLL || isL || isH || isHH || isHHH);
    }
}
