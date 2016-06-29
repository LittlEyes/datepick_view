package com.litteyes.datepick;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.litteyes.datepick.veiw.DatePickerController;
import com.litteyes.datepick.veiw.DayPickerView;
import com.litteyes.datepick.veiw.SimpleMonthAdapter;

import java.util.HashMap;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * datepick的一个包裹控件，在datepick的基础上添加了星期 标头
 */
public class DatePickerView extends LinearLayout implements DatePickerController {

    private DayPickerView dayPickerView;

    private String num = "日一二三四五六";

    public DatePickerView(Context context) {
        super(context, null);
    }

    public DatePickerView(Context context, AttributeSet attrs) {
        super(context, attrs);


        // 设置排列方向为竖向
        setOrientation(VERTICAL);

        LayoutParams llParams =
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        // 周视图根布局
        LinearLayout llWeek = new LinearLayout(context);
        llWeek.setOrientation(HORIZONTAL);
        int llWeekPadding = MeasureUtil.dp2px(context, 5);
        llWeek.setPadding(0, llWeekPadding, 0, llWeekPadding);
        LayoutParams lpWeek = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpWeek.weight = 1;

        for (int i = 0; i < 7; i++) {
            TextView tvWeek = new TextView(context);
            tvWeek.setText("周" + num.charAt(i));
            tvWeek.setGravity(Gravity.CENTER);
            tvWeek.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            tvWeek.setTextColor(getResources().getColor(R.color.wordcolor_middle));
            llWeek.addView(tvWeek, lpWeek);
        }
        addView(llWeek, llParams);


        //一条分割线
        LayoutParams lineViewParams = new LayoutParams(LayoutParams.MATCH_PARENT, 1);
        View lineView = new View(context);
        lineView.setBackgroundColor(getResources().getColor(R.color.normal_month));
        addView(lineView, lineViewParams);


        //datepick view
        LayoutParams pickViewParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        LinearLayout llMoth = new LinearLayout(context);
        dayPickerView = new DayPickerView(context);
        LayoutParams lpMoth = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        dayPickerView.setController(this);
        dayPickerView.setBackgroundColor(Color.WHITE);
        llMoth.addView(dayPickerView, lpMoth);
        addView(llMoth, pickViewParams);


        postInvalidate();
    }


    @Override
    public int getMaxYear() {
        return 0;
    }

    @Override
    public void onDayOfMonthSelected(int year, int month, int day) {

    }

    @Override
    public void onDateRangeSelected(HashMap<String, SimpleMonthAdapter.CalendarDay> selectedDays) {

    }

    public DayPickerView getDayPickerView() {
        return dayPickerView;
    }

}
