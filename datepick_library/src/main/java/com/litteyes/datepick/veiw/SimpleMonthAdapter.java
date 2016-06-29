package com.litteyes.datepick.veiw;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;

import com.litteyes.datepick.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class SimpleMonthAdapter extends RecyclerView.Adapter<SimpleMonthAdapter.ViewHolder> implements SimpleMonthView.OnDayClickListener {
    protected static final int MONTHS_IN_YEAR = 12;
    private final TypedArray typedArray;
    private final Context mContext;
    private final DatePickerController mController;
    private final Calendar calendar;
    //    private final SelectedDays<CalendarDay> selectedDays;
    private final HashMap<String, CalendarDay> mSelectedDays;
    private int firstMonth;
    private final Integer lastMonth;
    private boolean isDragging = false;
    private List<CalendarDay> days = new ArrayList<>();
    private List<CalendarMonth> months = new ArrayList<>();
//    private HashMap<CalendarDay, Integer> countMap;
//    private HashMap<CalendarMonth, HashMap<CalendarDay, Integer>> monthCountMap =
//            new HashMap<>();


    public SimpleMonthAdapter(Context context, DatePickerController datePickerController, TypedArray typedArray) {
        this.typedArray = typedArray;
        calendar = Calendar.getInstance();
        firstMonth = typedArray.getInt(R.styleable.DayPickerView_firstMonth, 0);
        lastMonth = typedArray.getInt(R.styleable.DayPickerView_lastMonth, 11);
//        selectedDays = new SelectedDays<>();
        mSelectedDays = new HashMap<>();
        mContext = context;
        mController = datePickerController;
        init();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final SimpleMonthView simpleMonthView = new SimpleMonthView(mContext, typedArray);
        return new ViewHolder(simpleMonthView, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final SimpleMonthView v = viewHolder.simpleMonthView;
        final HashMap<String, Integer> drawingParams = new HashMap<String, Integer>();
        CalendarMonth calendarMonth = months.get(position);
//        int month;
//        int year;

//        month = (firstMonth + (position % MONTHS_IN_YEAR)) % MONTHS_IN_YEAR;
//        year = position / MONTHS_IN_YEAR - 1 + calendar.get(Calendar.YEAR);

//        System.out.println("position:" + position + year + "年" + month + "月");
        //获取被选中的天数
        Iterator iterator = mSelectedDays.entrySet().iterator();
        List<CalendarDay> calendarDayList = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            CalendarDay calendarDay = (CalendarDay) entry.getValue();
            if (calendarDay.getYear() == calendarMonth.year && calendarDay.getMonth() == calendarMonth.month) {
                calendarDayList.add(calendarDay);
            }
        }
        v.reuse();

        drawingParams.put(SimpleMonthView.VIEW_PARAMS_YEAR, calendarMonth.year);
        drawingParams.put(SimpleMonthView.VIEW_PARAMS_MONTH, calendarMonth.month);
        drawingParams.put(SimpleMonthView.VIEW_PARAMS_WEEK_START, calendar.getFirstDayOfWeek());
        v.setSelectDayList(calendarDayList);
        v.setCanClickDays(days);
        v.setMonthParams(drawingParams);
        v.showMothInfo(isDragging);

//        CalendarMonth calendarMonth = new CalendarMonth(year, month);
//        if (monthCountMap.containsKey(calendarMonth)) {
//            v.setEventSymbols(monthCountMap.get(calendarMonth));
//        }
        v.invalidate();
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return months == null ? 0 : months.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final SimpleMonthView simpleMonthView;

        public ViewHolder(View itemView, SimpleMonthView.OnDayClickListener onDayClickListener) {
            super(itemView);
            simpleMonthView = (SimpleMonthView) itemView;
            simpleMonthView.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            simpleMonthView.setClickable(true);
            simpleMonthView.setOnDayClickListener(onDayClickListener);
        }
    }

    protected void init() {
        if (typedArray.getBoolean(R.styleable.DayPickerView_currentDaySelected, false)) {
            onDayTapped(new CalendarDay(System.currentTimeMillis()));
        }
    }


    public void onDayClick(SimpleMonthView simpleMonthView, CalendarDay calendarDay) {
        if (calendarDay != null) {
            onDayTapped(calendarDay);
        }
    }

    protected void onDayTapped(CalendarDay calendarDay) {
        if (days.contains(calendarDay)) {
            mController.onDayOfMonthSelected(calendarDay.year, calendarDay.month, calendarDay.day);
            setSelectedDay(calendarDay);
        }
    }

    public void clearSelectDay() {
        mSelectedDays.clear();
    }

    public void setSelectedDay(CalendarDay calendarDay) {
//        selectedDays.setFirst(calendarDay);
        if (mSelectedDays.containsValue(calendarDay)) {
            mSelectedDays.remove(calendarDay.toString());
        } else {
            mSelectedDays.put(calendarDay.toString(), calendarDay);
        }

        notifyDataSetChanged();
    }

    public void setSelectedDay(HashMap<String, CalendarDay> selectedDays) {
        mSelectedDays.clear();
        mSelectedDays.putAll(selectedDays);
        notifyDataSetChanged();
    }

    public void setDragging(boolean isDragging) {
        this.isDragging = isDragging;
        notifyDataSetChanged();
    }

//    public SelectedDays<CalendarDay> getSelectedDays() {
//        return selectedDays;
//    }

    public HashMap<String, CalendarDay> getSelectedDays() {
        return mSelectedDays;
    }

    public void setCountMap(List<CalendarDay> list) {
//        this.months = countMap;
//        monthCountMap.clear();
//        Iterator it = countMap.entrySet().iterator();
//        while (it.hasNext()) {
//            HashMap.Entry<CalendarDay, Integer> entry = (HashMap.Entry<CalendarDay, Integer>) it.next();
//            CalendarMonth calendarMonth = new CalendarMonth(entry.getKey());
//            if (monthCountMap.containsKey(calendarMonth)) {
//                HashMap<CalendarDay, Integer> tempMap = monthCountMap.get(calendarMonth);
//                tempMap.put(entry.getKey(), entry.getValue());
//            } else {
//                HashMap<CalendarDay, Integer> tempMap = new HashMap<>();
//                tempMap.put(entry.getKey(), entry.getValue());
//                monthCountMap.put(calendarMonth, tempMap);
//            }
//        }
        this.days = list;
        months.clear();
        for (int i = 0; i < days.size(); i++) {
            CalendarMonth month = new CalendarMonth(days.get(i));
            if (!months.contains(month)) {
                months.add(month);

            }
        }

        notifyDataSetChanged();
    }

    public static class CalendarMonth implements Serializable {
        private Calendar calendar = Calendar.getInstance();

        int month;
        int year;

        public CalendarMonth(CalendarDay day) {
            month = day.month;
            year = day.year;
            calendar.set(Calendar.MONTH, day.month);
            calendar.set(Calendar.YEAR, day.year);
        }

        public CalendarMonth(int year, int month) {
            this.year = year;
            this.month = month;
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, year);
        }

        @Override
        public String toString() {
            final StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{ year: ");
            stringBuilder.append(year);
            stringBuilder.append(" month: ");
            stringBuilder.append(month);
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof CalendarMonth) {
                CalendarMonth other = (CalendarMonth) o;
                if (other.year == this.year
                        && other.month == this.month) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public int hashCode() {
            return calendar.hashCode() + month * 37 + year;
        }
    }

    public static class CalendarDay implements Serializable {
        private static final long serialVersionUID = -5456695978688356202L;
        private Calendar calendar = Calendar.getInstance();

        int day;
        int month;
        int year;

        public CalendarDay() {
            setTime(System.currentTimeMillis());
        }

        public CalendarDay(int year, int month, int day) {
            setDay(year, month, day);
        }

        public CalendarDay(long timeInMillis) {
            setTime(timeInMillis);
        }

        public CalendarDay(Calendar calendar) {
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }

        private void setTime(long timeInMillis) {
            if (calendar == null) {
                calendar = Calendar.getInstance();
            }
            calendar.setTimeInMillis(timeInMillis);
            month = this.calendar.get(Calendar.MONTH);
            year = this.calendar.get(Calendar.YEAR);
            day = this.calendar.get(Calendar.DAY_OF_MONTH);
        }

        public void set(CalendarDay calendarDay) {
            year = calendarDay.year;
            month = calendarDay.month;
            day = calendarDay.day;
        }

        public void setDay(int year, int month, int day) {
            this.year = year;
            this.month = month;
            this.day = day;
        }

        public Date getDate() {
            if (calendar == null) {
                calendar = Calendar.getInstance();
            }
            calendar.set(year, month, day);
            return calendar.getTime();
        }

        public int getDay() {
            return day;
        }

        public int getMonth() {
            return month;
        }

        public int getYear() {
            return year;
        }

        @Override
        public String toString() {
            final StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{ year: ");
            stringBuilder.append(year);
            stringBuilder.append(", month: ");
            stringBuilder.append(month);
            stringBuilder.append(", day: ");
            stringBuilder.append(day);
            stringBuilder.append(" }");

            return stringBuilder.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof CalendarDay) {
                CalendarDay other = (CalendarDay) o;
                if (other.year == this.year
                        && other.month == this.month
                        && other.day == this.day) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public int hashCode() {
            return calendar.hashCode() + 17 * day + 31 * month + year;
        }
    }
}