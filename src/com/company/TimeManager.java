package com.company;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class TimeManager {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String workingHours = in.nextLine();
        String[] buff = workingHours
                .replaceAll("(‘start’:)+|(‘end’:’)+|\\]+|\\[+|\\’+|\\'\\{+|\\}+|\\{+|\\'+", "")
                .split(", ");
        System.out.println(buff[0]);
        System.out.println(buff[1]);
        System.out.println(buff[2]);
        System.out.println(buff[3]);
        System.out.println(buff.length);
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
            sHour[j] = Integer.parseInt(startMass[3]);
            sMinuets[j] = Integer.parseInt(startMass[4]);
            j++;
            i++;
        }

        j = 0;
        for (int i = 1; i < buff.length; i++) {
            String[] endMass = buff[i].split("(\\.|\\:| )+");
            eDay[j] = Integer.parseInt(endMass[0]);
            eMonth[j] = Integer.parseInt(endMass[1]);
            eYear[j] = Integer.parseInt(endMass[2]);
            eHour[j] = Integer.parseInt(endMass[3]);
            eMinuets[j] = Integer.parseInt(endMass[4]);
            j++;
            i++;
        }
        for (int i = 0; i < sYear.length; i++) {
            Calendar sCalendar = new GregorianCalendar(sYear[i], sMonth[i] - 1, sDay[i], sHour[i], sMinuets[i]);
            Calendar eCalendar = new GregorianCalendar(eYear[i], eMonth[i] - 1, eDay[i], eHour[i], eMinuets[i]);
            long b = (long) (eCalendar.getTimeInMillis() - sCalendar.getTimeInMillis());
            sum = sum + b;
        }
        long minutes = TimeUnit.MILLISECONDS.toMinutes(sum);
        int hours = (int) (minutes / 60);
        minutes = minutes - (hours * 60L);
        StringBuilder sb = new StringBuilder();
        sb.append(hours);
        sb.append("-");
        sb.append(minutes);
        System.out.println(sb);
    }
}
