package com.chat.demo.util;

import java.sql.Timestamp;

/**
 * @author lsf
 * @date 2023年04月23日 6:01 PM
 */
public class ExpireTimeUtils {

    //1周2月3年
    public static final int TYPE_DAY = 0;
    public static final int TYPE_DAY_MILL = 1000 * 60 * 60 * 24;
    public static final int TYPE_WEEK = 1;
    public static final int TYPE_WEEK_MILL = 1000 * 60 * 60 * 24 * 7;
    public static final int TYPE_MONTH = 2;
    public static final int TYPE_MONTH_MILL = 1000 * 60 * 60 * 24 * 31;
    public static final int TYPE_YEAR = 3;
    public static final int TYPE_YEAR_MILL = 1000 * 60 * 60 * 24 * 365;

    public static final int DEFAULT_SEND_COUNT = 10;



    /**
     * true 过期了
     * false 没有过期
     *
     * @param createTime
     * @param type
     * @return
     */
    public static boolean isExpire(Timestamp createTime, int type) {
        if (TYPE_WEEK == type) {
            return createTime.getTime() + TYPE_WEEK_MILL < System.currentTimeMillis();
        }

        if (TYPE_MONTH == type) {
            return createTime.getTime() + TYPE_MONTH_MILL < System.currentTimeMillis();
        }

        if (TYPE_YEAR == type) {
            return createTime.getTime() + TYPE_YEAR_MILL < System.currentTimeMillis();
        }

        return false;

    }


    public static Timestamp getEndTime(int type) {
        if (TYPE_DAY == type) {
            return new Timestamp(TYPE_DAY_MILL + System.currentTimeMillis());
        }
        if (TYPE_WEEK == type) {
            return new Timestamp(TYPE_WEEK_MILL + System.currentTimeMillis());
        }

        if (TYPE_MONTH == type) {
            return new Timestamp(TYPE_MONTH_MILL + System.currentTimeMillis());
        }

        if (TYPE_YEAR == type) {
            return new Timestamp(TYPE_YEAR_MILL + System.currentTimeMillis());
        }

        return new Timestamp(System.currentTimeMillis());
    }


}
