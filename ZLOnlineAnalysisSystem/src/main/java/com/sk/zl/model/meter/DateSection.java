package com.sk.zl.model.meter;

import com.sk.zl.aop.excption.ServiceException;
import lombok.Data;

import javax.sql.rowset.serial.SerialException;
import java.util.Date;

/**
 * @Description : 时间区间
 * @Author : Ellie
 * @Date : 2019/3/6
 */
@Data
public class DateSection implements Comparable<DateSection> {
    private Date start;
    private Date end;

    public DateSection(Date start, Date end) {
        if (start.after(end)) {
            throw new ServiceException("生成了无效的时间段。");
        }

        this.start = start;
        this.end = end;
    }

    @Override
    public int compareTo(DateSection o) {
        if (start.equals(o.getStart()) && end.equals(o.getEnd())) {
            return 0;
        } else if (end.after(o.getEnd())) {
            return 1;
        } else {
            return -1;
        }
    }

    public DateSection union(DateSection ex) {
        Date maxStart = (start.after(ex.getStart()) ? start : ex.getStart());
        Date minEnd = (end.before(ex.getEnd()) ? end : ex.getEnd());

        if (maxStart.before(minEnd)) {
            return new DateSection(maxStart, end);
        } else {
            return null;
        }
    }

    /** 返回null表示差集不存在,所有属于A但不属于B的集合 */
    public static DateSection differenceSet(DateSection aSection, DateSection bSection) {
        if (aSection == null || bSection == null) {
            return null;
        }

        // 求交集
        DateSection union = aSection.union(bSection);
        if (null == union) {
            return aSection;
        }

        int ret = aSection.compareTo(union);
        if (ret == 0) {
            return null;
        } else if (ret == 1) {
            return new DateSection(union.getEnd(), aSection.getEnd());
        } else {
            return new DateSection(aSection.getStart(), union.getStart());
        }
    }
}
