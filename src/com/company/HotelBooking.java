/*
В одной системе бронирования отелей вам надо написать один небольшой модуль.
Вам дан массив дат бронирования номера в отеле. Элемент массива или одна дата, или период - две даты через дефис.
Пример:
['12.09.2020', '14.09.2020-02.10.2020']
Вам надо выяснить можно ли добавить в массив новую введенную дату или период для нового бронирования. Например,
для указанного выше примера период '01.10.2020-05.10.2020' добавлять нельзя, так как первые два дня уже забронированы.
В первой строке входных данных содержится информация о дате-времени существующих бронирований, во второй - новая бронь.
Выведите True, если бронь можно добавить, False - если нельзя.
 */
package com.company;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class HotelBooking {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String massStr = in.nextLine();
        String bookingDate = in.nextLine();
        String[] busyDates = massStr.replaceAll("\\[?'*]?( )*", "").split(",");

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
                    String[] BusyMass = busyDates[i].split("-");
                    String[] sBuff = BusyMass[0].split("\\.");
                    int sDay = (Integer.parseInt(sBuff[0]));
                    int sMonth = (Integer.parseInt(sBuff[1]));
                    int sYear = Integer.parseInt(sBuff[2]);
                    Calendar startDate = new GregorianCalendar(sYear, sMonth, sDay);
                    String[] eBuff = BusyMass[1].split("\\.");
                    int eDay = (Integer.parseInt(eBuff[0]));
                    int eMonth = (Integer.parseInt(eBuff[1]));
                    int eYear = Integer.parseInt(eBuff[2]);
                    Calendar endDate = new GregorianCalendar(eYear, eMonth, eDay);

                    if (startDate.before(bookingDayCalendar) && bookingDayCalendar.before(startDate)) {
                        System.out.println("False");
                        System.exit(0);
                    }
                    // иначе занятая дата число
                } else {
                    String[] busyBuff = busyDates[i].split("\\.");
                    int busyDay = (Integer.parseInt(busyBuff[0]));
                    int busyMonth = (Integer.parseInt(busyBuff[1]));
                    int busyYear = Integer.parseInt(busyBuff[2]);
                    Calendar busyCalendar = new GregorianCalendar(busyYear, busyMonth, busyDay);
                    if (busyCalendar.equals(bookingDayCalendar)) {
                        System.out.println("False");
                        System.exit(0);
                    }
                }
                //иначе дата бронирования период
            } else {
                String[] bookingBuff = bookingDate.split("-");
                String[] startBookingMass = bookingBuff[0].split("\\.");
                int stBookDay = (Integer.parseInt(startBookingMass[0]));
                int stBookMonth = (Integer.parseInt(startBookingMass[1]));
                int stBookYear = Integer.parseInt(startBookingMass[2]);
                Calendar startBooking = new GregorianCalendar(stBookYear, stBookMonth, stBookDay); // дата начала брони

                String[] endBookingMass = bookingBuff[1].split("\\.");
                int endBookDay = (Integer.parseInt(endBookingMass[0]));
                int endBookMonth = (Integer.parseInt(endBookingMass[1]));
                int endBookYear = Integer.parseInt(endBookingMass[2]);
                Calendar endBooking = new GregorianCalendar(endBookYear, endBookMonth, endBookDay); // дата окончания брони

                int checkNum = 0;
                checkNum = busyDates[i].indexOf("-");
                // занятая дата период
                if (checkNum != -1) {
                    String[] busyMass = busyDates[i].split("\\-");
                    String[] sBuffBusy = busyMass[0].split("\\.");
                    int sDayBusy = (Integer.parseInt(sBuffBusy[0]));
                    int sMonthBusy = Integer.parseInt(sBuffBusy[1]); //возможно -1 не нужна
                    int sYearBusy = Integer.parseInt(sBuffBusy[2]);
                    Calendar startBusy = new GregorianCalendar(sYearBusy, sMonthBusy, sDayBusy);

                    String[] eBuffBusy = busyMass[1].split("\\.");
                    int eDayBusy = (Integer.parseInt(eBuffBusy[0]));
                    int eMonthBusy = Integer.parseInt(eBuffBusy[1]);
                    int eYearBusy = Integer.parseInt(eBuffBusy[2]);
                    Calendar endBusy = new GregorianCalendar(eYearBusy, eMonthBusy, eDayBusy);

                    if ((startBusy.before(startBooking) && startBooking.before(endBusy)) ||
                            (startBusy.before(endBooking) && endBooking.before(endBusy))) {
                        System.out.println("False");
                        System.exit(0);
                    }
                    //занятая дата - число
                } else {
                    String[] bookingBuffer = bookingDate.split("-");
                    String[] sBookingDateMass = bookingBuffer[0].split("\\.");
                    int sBookingDay = (Integer.parseInt(sBookingDateMass[0]));
                    int sBookingMonth = (Integer.parseInt(sBookingDateMass[1]));
                    int sBookingYear = Integer.parseInt(sBookingDateMass[2]);
                    checkDate(sBookingYear, sBookingMonth, sBookingDay);
                    Calendar sBookingCalendar = new GregorianCalendar(sBookingYear, sBookingMonth, sBookingDay);

                    String[] eBookingDateMass = bookingBuffer[0].split("\\.");
                    int eBookingDay = (Integer.parseInt(eBookingDateMass[0]));
                    int eBookingMonth = (Integer.parseInt(eBookingDateMass[1]));
                    int eBookingYear = Integer.parseInt(eBookingDateMass[2]);
                    checkDate(eBookingYear, eBookingMonth, eBookingDay);
                    Calendar eBookingCalendar = new GregorianCalendar(eBookingYear, eBookingMonth, sBookingDay);

                    String[] busyBuff = busyDates[i].split("\\.");
                    int busyDay = (Integer.parseInt(busyBuff[0]));
                    int busyMonth = (Integer.parseInt(busyBuff[1]));
                    int busyYear = Integer.parseInt(busyBuff[2]);
                    Calendar busyCalendar = new GregorianCalendar(busyYear, busyMonth, busyDay);

                    if (sBookingCalendar.before(busyCalendar) && busyCalendar.before(eBookingCalendar)) {
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
}
