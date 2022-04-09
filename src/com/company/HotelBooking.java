package com.company;

/* В одной системе бронирования отелей вам надо написать один небольшой модуль.
Вам дан массив дат бронирования номера в отеле. Элемент массива или одна дата, или период - две даты через дефис.
Пример:
["12.09.2020", "14.09.2020-02.10.2020"]
Вам надо выяснить можно ли добавить в массив новую введенную дату или период для нового бронирования. Например,
для указанного выше примера период '01.10.2020-05.10.2020' добавлять нельзя, так как первые два дня уже забронированы.
В первой строке входных данных содержится информация о дате-времени существующих бронирований, во второй - новая бронь.
Выведите True, если бронь можно добавить, False - если нельзя.*/

import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class HotelBooking {
    private static final int THREE = 3;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String massStr = in.nextLine();
        String bookingDate = in.nextLine();
        String[] busyDates = massStr.replaceAll("[\\[\\]\"]", "").split(", ");

        //с помощью indexNum(далее также используется checkNum) идет проверка
        // даты бронирования на то является ли она периодом дат или единичной датой
        int indexNum;
        indexNum = bookingDate.indexOf("-");

        for (int i = 0; i < busyDates.length; i++) {
            //если единичная дата бронирования
            if ((-1) == indexNum) {
                String[] bookingDates = bookingDate.split("\\.");
                int bookingDay = (Integer.parseInt(bookingDates[0]));
                int bookingMonth = (Integer.parseInt(bookingDates[1]));
                int bookingYear = Integer.parseInt(bookingDates[2]);
                checkDate(bookingYear, bookingMonth, bookingDay);
                Calendar bookingDayCalendar = new GregorianCalendar(bookingYear, bookingMonth, bookingDay);

                int checkNum = 0;
                checkNum = busyDates[i].indexOf("-");
                //если занятая дата период
                if (checkNum != -1) {
                    String[] busyMass = busyDates[i].split("\\-");
                    Calendar startDate = strToCalendar(busyMass, 0);
                    Calendar endDate = strToCalendar(busyMass, 1);

                    if (startDate.before(bookingDayCalendar) && bookingDayCalendar.before(endDate)) {
                        System.out.println("False");
                        System.exit(0);
                    }
                    // иначе занятая дата число
                } else {
                    Calendar busyCalendar = strToCalendar(busyDates, i);
                    if (busyCalendar.equals(bookingDayCalendar)) {
                        System.out.println("False");
                        System.exit(0);
                    }
                }
                //иначе дата бронирования период
            } else {
                String[] bookingBuff = bookingDate.split("-");
                Calendar startBooking = strToCalendar(bookingBuff, 0); // дата начала брони
                Calendar endBooking = strToCalendar(bookingBuff, 1); // дата окончания брони
                int checkNum;
                checkNum = busyDates[i].indexOf("-");
                // занятая дата период
                if (checkNum != -1) {
                    String[] busyMass = busyDates[i].split("\\-");
                    Calendar startBusy = strToCalendar(busyMass, 0);
                    Calendar endBusy = strToCalendar(busyMass, 1);
                    if ((startBusy.before(startBooking) && startBooking.before(endBusy))
                            || (startBusy.before(endBooking) && endBooking.before(endBusy))) {
                        System.out.println("False");
                        System.exit(0);
                    }
                    //занятая дата - число
                } else {
                    Calendar busyCalendar = strToCalendar(busyDates, i);
                    if (startBooking.before(busyCalendar) && busyCalendar.before(endBooking)) {
                        System.out.println("False");
                        System.exit(0);
                    }
                }
            }
        }
        System.out.println("True");
    }

    public static void checkDate(int year, int month, int day) {
        try {
            LocalDate localDate = LocalDate.of(year, month, day);
            String str = localDate.toString();
        } catch (Exception e) {
            System.out.println("Date is not correct");
            System.exit(0);
        }
    }

    public static Calendar strToCalendar(String[] buff, int index) {
        String[] sBuff = buff[index].split("\\.");
        int Day = (Integer.parseInt(sBuff[0]));
        int Month = (Integer.parseInt(sBuff[1]));
        int Year = Integer.parseInt(sBuff[2]);
        checkDate(Year, Month, Day);
        int[] intMass = new int[THREE];
        intMass[0] = Year;
        intMass[1] = Month;
        intMass[2] = Day;
        Calendar calendar = new GregorianCalendar(intMass[0], intMass[1], intMass[2]);
        return calendar;
    }
}
