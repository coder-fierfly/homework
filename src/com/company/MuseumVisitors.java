package com.company;

/* В музее регистрируется в течение дня время прихода и ухода каждого посетителя. Таким образом за день получены N пар
значений, где первое значение в паре показывает время прихода посетителя и второе значения - время его ухода.
Найти промежуток времени, в течение которого в музее одновременно находилось максимальное число посетителей.
Пример:
Входные данные:
9:00 17:15
10:00 11:00
Ожидаемый результат
10:00 11:00*/

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.OptionalInt;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.stream.IntStream;

public class MuseumVisitors {
    private static final int YEAR = 1970;
    private static final int MONTH = 0;
    private static final int DAY = 1;
    private static final int THREE = 3;
    private static final int MIN_TO_HOURS = 60;
    private static final int MILL_TO_MIN = 60000;
    private static final int TIMEZONE = 180;
    private static final int ALL_MINUTES = 1440;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int[] allMinutes = new int[ALL_MINUTES];
        ArrayList<String> time = new ArrayList<>();
        while (in.hasNextLine()) {
            String buff = in.nextLine();
            if (buff.isEmpty()) {
                break;
            }
            time.add(buff);
        }
        for (int i = 0; i < time.size(); i++) {
            String[] buff = time.get(i).split("(\\:| )");
            int sHour = Integer.parseInt(buff[0]);
            int sMinutes = Integer.parseInt(buff[1]);
            int eHour = Integer.parseInt(buff[2]);
            int eMinutes = Integer.parseInt(buff[THREE]);
            checkDate(YEAR, MONTH, DAY, sHour, sMinutes);
            Calendar startCalendar = new GregorianCalendar(YEAR, MONTH, DAY, sHour, sMinutes);
            checkDate(YEAR, MONTH, DAY, eHour, eMinutes);
            Calendar endCalendar = new GregorianCalendar(YEAR, MONTH, DAY, eHour, eMinutes);

            int start = (int) ((startCalendar.getTimeInMillis()) / MILL_TO_MIN + TIMEZONE);
            int end = (int) ((endCalendar.getTimeInMillis()) / MILL_TO_MIN + TIMEZONE);
            for (int j = start; j < end; j++) {
                allMinutes[j] = allMinutes[j] + 1;
            }
        }

        IntStream intStream = Arrays.stream(allMinutes);
        OptionalInt optionalInt = intStream.max();
        int maxVisitors = optionalInt.getAsInt();
        System.out.println(maxVisitors);

        int startMax = 0;
        int endMax = 0;
        for (int i = 0; i < allMinutes.length; i++) {
            if (allMinutes[i] == maxVisitors) {
                startMax = i;
                break;
            }
        }
        System.out.println(startMax);
        for (int i = startMax + 1; i < allMinutes.length; i++) {
            if (allMinutes[i] != maxVisitors) {
                endMax = i;
                break;
            }
        }
        int startMaxHour = startMax / MIN_TO_HOURS;
        System.out.println(startMaxHour);
        int startMaxMinutes = startMax - (startMaxHour * MIN_TO_HOURS);
        System.out.println(startMaxMinutes);
        int endMaxHour = endMax / MIN_TO_HOURS;
        int endMaxMinutes = endMax - (endMaxHour * MIN_TO_HOURS);
        StringBuilder sb = new StringBuilder();
        Date sDate = new Date(YEAR, MONTH, DAY, startMaxHour, startMaxMinutes);
        Date eDate = new Date(YEAR, MONTH, DAY, endMaxHour, endMaxMinutes);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        sb.append(format.format(sDate));
        sb.append(" ");
        sb.append(format.format(eDate));
        System.out.println(sb);
    }

    public static void checkDate(int year, int month, int day, int hour, int minuets) {
        try {
            Calendar localDate = new GregorianCalendar(year, month, hour, day, minuets);
            Date str = localDate.getTime();
        } catch (Exception e) {
            System.out.println("Date is not correct");
            System.exit(0);
        }
    }
}
