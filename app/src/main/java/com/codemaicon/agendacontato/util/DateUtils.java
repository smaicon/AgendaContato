package com.codemaicon.agendacontato.util;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by smaicon on 07/11/2017.
 */

public class DateUtils {

    public static Date getDate(int year, int month, int day) {
        //retorna pega data
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Date data = calendar.getTime();
        //sem conversao
        return data;

    }

    public static String dateToString(int year, int month, int day) {
        //retorna pega data
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Date date = calendar.getTime();

        //deixando no formato visivel para usuario
        //medium exibe mes por short somente numeros
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        //converte string para date
        String dt = dateFormat.format(date);
        return dt;
    }


    public static String DayToString1(Date date) {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        String dt = dateFormat.format(date);
        return dt;
    }

}
