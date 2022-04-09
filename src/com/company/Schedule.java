package com.company;

/* Задана дата. Сгенерировать расписание с этой даты на следующий месяц по такому принципу: сутки через трое.
Если рабочий день приходится на воскресенье, то он переносится на понедельник.
В итоге необходимо вывести даты рабочих дней. Напрмер, если введено 4 февраля 2021,
то необходимо сгенерировать расписание по 4 марта вклюительно.
Примечание: Если через месяц от введенного дня день попадает на рабочий, его тоже надо выводить.
21.2.2021 - попадает на воскресенье, значит следующий рабочий день - 22.2.2021*/

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Schedule {
    private static final int THREE = 3;
    private static final int FOUR = 4;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();
        String[] strMass = str.split("\\.");
        int year = Integer.parseInt(strMass[2]);
        int month = Integer.parseInt(strMass[1]);
        int day = Integer.parseInt(strMass[0]);
        try {
            LocalDate localDate = LocalDate.of(year, month, day);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
            String formattedString = localDate.format(formatter);
            System.out.println(formattedString);
        } catch (Exception e) {
            System.out.println("Date is not correct");
            return;
        }
        Calendar localDate = new GregorianCalendar(year, month - 1, day);
        Calendar finalDate = new GregorianCalendar(year, month, day - THREE);
        while (localDate.before(finalDate)) {
            try {
                localDate.add(Calendar.DATE, FOUR);
                if (localDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    localDate.add(Calendar.DATE, 1);
                }
                SimpleDateFormat formatter = new SimpleDateFormat("d.M.yyyy");
                String formattedString = formatter.format(localDate.getTime());
                System.out.println(formattedString);
            } catch (Exception e) {
                System.out.println("");
            }
        }
    }
}
