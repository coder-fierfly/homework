package com.company;

/* Дан массив периодов дат (временные затраты сотрудника на выполнение задач проекта).
Например,
[{"start": "02.10.2021 10:12:11", "end":"02.10.2021 15:20:11}, {"start":"03.10.2021 13:12:11", "end":"03.10.2021 16:40:40"}]
То есть это массив, каждый элемент - словарь с двумя датами в формате ‘DD.MM.YY HH:MM:SS’.
Найти, сколько всего часов потрачено сотрудником. Ответ вывести в в формате "N-K", где N - количество часов, K-количество минут.
Например,
Ввод:
[{"start": "25.01.2021 10:00:11", "end":"25.01.2021 15:00:09"}, {"start":"25.01.2021 15:10:11", "end":"25.01.2021 16:40:40"}];
Вывод:
6-30
Примечание:
Секунды округляются всегда вниз.
Если работа велась ночью/в выходные необходимо учитывать весь период. */

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class TimeManager {

    private static final int THREE = 3;
    private static final int FOUR = 4;
    private static final int MINUTES_TO_HOUR = 60;
    private static final String REGULAR = "(\"start\"\\:\s?)+|(\"end\"\\:\")+|\\]+|\\[+|\"+|\\{+|\\}+|\\{+|\\;+";

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String workingHours = in.nextLine();
        String[] buff = workingHours
                .replaceAll(REGULAR, "").split(", ");
        long sum = 0;

        int[] sDay = new int[buff.length / 2];
        int[] sMonth = new int[buff.length / 2];
        int[] sYear = new int[buff.length / 2];
        int[] sHour = new int[buff.length / 2];
        int[] sMinuets = new int[buff.length / 2];
        int[] eDay = new int[buff.length / 2];
        int[] eMonth = new int[buff.length / 2];
        int[] eYear = new int[buff.length / 2];
        int[] eHour = new int[buff.length / 2];
        int[] eMinuets = new int[buff.length / 2];
        int j = 0;
        for (int i = 0; i < buff.length; i++) {
            String[] startMass = buff[i].split("(\\.|\\:| )+");
            sDay[j] = Integer.parseInt(startMass[0]);
            sMonth[j] = Integer.parseInt(startMass[1]);
            sYear[j] = Integer.parseInt(startMass[2]);
            sHour[j] = Integer.parseInt(startMass[THREE]);
            sMinuets[j] = Integer.parseInt(startMass[FOUR]);
            j++;
            i++;
        }

        j = 0;
        for (int i = 1; i < buff.length; i++) {
            String[] endMass = buff[i].split("(\\.|\\:| )+");
            eDay[j] = Integer.parseInt(endMass[0]);
            eMonth[j] = Integer.parseInt(endMass[1]);
            eYear[j] = Integer.parseInt(endMass[2]);
            eHour[j] = Integer.parseInt(endMass[THREE]);
            eMinuets[j] = Integer.parseInt(endMass[FOUR]);
            j++;
            i++;
        }
        for (int i = 0; i < sYear.length; i++) {
            checkDate(sYear[i], sMonth[i] - 1, sDay[i], sHour[i], sMinuets[i]);
            Calendar sCalendar = new GregorianCalendar(sYear[i], sMonth[i] - 1, sDay[i], sHour[i], sMinuets[i]);
            checkDate(eYear[i], eMonth[i] - 1, eDay[i], eHour[i], eMinuets[i]);
            Calendar eCalendar = new GregorianCalendar(eYear[i], eMonth[i] - 1, eDay[i], eHour[i], eMinuets[i]);
            long b = (long) (eCalendar.getTimeInMillis() - sCalendar.getTimeInMillis());
            sum = sum + b;
        }
        int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(sum);
        int hours = (minutes / MINUTES_TO_HOUR);
        minutes = minutes - (hours * MINUTES_TO_HOUR);
        StringBuilder sb = new StringBuilder();
        sb.append(hours);
        sb.append("-");
        sb.append(minutes);
        System.out.println(sb);
    }

    public static void checkDate(int year, int month, int day, int hour, int minutes) {
        try {
            Calendar localDate = new GregorianCalendar(year, month, hour, day, minutes);
            Date str = localDate.getTime();
        } catch (Exception e) {
            System.out.println("Date is not correct");
            System.exit(0);
        }
    }
}
