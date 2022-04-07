package com.company;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;


public class Schedule {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();
        String[] strMass = str.split("\\.");
        int year = Integer.parseInt(strMass[2]);
        int month = Integer.parseInt(strMass[1]);
        int day = Integer.parseInt(strMass[0]);
        try {
            LocalDate localDate = LocalDate.of(year, month, day);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            String formattedString = localDate.format(formatter);
            System.out.println(formattedString);
        } catch (Exception e) {
            System.out.println("Date is not correct");
            return;
        }
        Calendar localDate = new GregorianCalendar(year, month - 1, day);
        Calendar finalDate = new GregorianCalendar(year, month, day - 3);
        while (localDate.before(finalDate)) {
            try {
                localDate.add(Calendar.DATE, 4);
                if (localDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    localDate.add(Calendar.DATE, 1);
                }
                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                String formattedString = formatter.format(localDate.getTime());
                System.out.println(formattedString);
            } catch (Exception e) {
                System.out.println("");
            }
        }
    }
}
