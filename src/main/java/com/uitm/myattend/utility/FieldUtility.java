package com.uitm.myattend.utility;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FieldUtility {

    public static String getCurrentDate() throws ParseException {
        Date curr = new Date();
        return format(curr, "yyyyMMdd");
    }

    public static String getCurrentTimestamp() throws ParseException {
        Date curr = new Date();
        return format(curr, "yyyyMMddHHmmssSSS");
    }

    public static String getFormatted(String date, String inFormat, String outFormat) throws ParseException {
        Date newDate = new SimpleDateFormat(inFormat).parse(date);
        return format(newDate, outFormat);
    }

    public static String format(Date date, String format) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static String timestamp2Oracle(String timestamp) throws ParseException {
        return getFormatted(timestamp, "yyyyMMddHHmmssSSS", "dd-MMM-yy hh.mm.ss.SSS a");
    }

    public static String date2Oracle(String date) throws ParseException {
        return getFormatted(date, "yyyyMMdd", "dd-MMM-yyyy");
    }

    public static String generateUUID() {
        try {
            BigInteger num = new BigInteger(FieldUtility.getCurrentTimestamp(), 10);
            BigInteger div = num.divide(new BigInteger(String.valueOf((int)(Math.random() * (99999-10000)))));
            return div.toString(10);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
