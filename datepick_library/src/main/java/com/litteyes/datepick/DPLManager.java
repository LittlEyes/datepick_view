package com.litteyes.datepick;

import java.util.Locale;

/**
 * Created by ${hyyx} on 2016/6/24 0024.
 */
public abstract class DPLManager {

    private static DPLManager sLanguage;





    /**
     * 月份标题显示
     *
     * Titles of month
     *
     * @return 长度为12的月份标题数组 Array in 12 length of month titles
     */
    public abstract String[] titleMonth();

    /**
     * 确定按钮文本
     *
     * Text of ensure button
     *
     * @return Text of ensure button
     */
    public abstract String titleEnsure();

    /**
     * 公元前文本
     *
     * Text of B.C.
     *
     * @return Text of B.C.
     */
    public abstract String titleBC();

    /**
     * 星期标题显示
     *
     * Titles of week
     *
     * @return 长度为7的星期标题数组 Array in 7 length of week titles
     */
    public abstract String[] titleWeek();
}


