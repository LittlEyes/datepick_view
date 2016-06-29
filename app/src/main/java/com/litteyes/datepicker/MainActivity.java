package com.litteyes.datepicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.litteyes.datepick.DatePickerView;
import com.litteyes.datepick.veiw.SimpleMonthAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatePickerView datePickerView;

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.show_btn);
        datePickerView = (DatePickerView) findViewById(R.id.date_picker);
        //给adapter 设置数据
        datePickerView.getDayPickerView().setCountMap(getCanAdvanceDate(70));

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * @param preDay 天数  这里指的是 可选择的天数，而不是显示天数，比如：可选择天数跨越3个月，那么显示天数为 这三个月所有的天数，不可选择的天 灰色显示
     */
    public List<SimpleMonthAdapter.CalendarDay> getCanAdvanceDate(int preDay) {
        List<SimpleMonthAdapter.CalendarDay> retList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        long startTime = calendar.getTimeInMillis();//初始可选择时间，可根据需求修改
        for (int i = 0; i < preDay; i++) {
            SimpleMonthAdapter.CalendarDay calendarDay = new SimpleMonthAdapter.CalendarDay(startTime);
            retList.add(calendarDay);
            startTime += 24 * 60 * 60 * 1000;
        }
        return retList;
    }
}
