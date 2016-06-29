package com.litteyes.datepick;

import android.content.Context;

/**
 * Created by ${hyyx} on 2016/6/24 0024.
 */
public class MeasureUtil {

    public static int dp2px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int px2dp(Context context, float px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

}
