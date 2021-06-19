package com.example.health.util;

import java.sql.Timestamp;
import java.util.Calendar;
import static java.util.Calendar.*;

public class TimeUtil {

    public static Calendar getEndOfDayTimeFromNow(int offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(YEAR), calendar.get(MONTH), calendar.get(DAY_OF_MONTH),
                23, 59, 59);
        calendar.add(DAY_OF_YEAR, offset);
        return calendar;
    }
}
