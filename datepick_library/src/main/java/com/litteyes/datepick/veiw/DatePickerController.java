package com.litteyes.datepick.veiw;


import java.util.HashMap;

public interface DatePickerController {
    public abstract int getMaxYear();

    public abstract void onDayOfMonthSelected(int year, int month, int day);

    public abstract void onDateRangeSelected(final HashMap<String, SimpleMonthAdapter.CalendarDay> selectedDays);

}