package com.uitm.myattend.utility;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

public class FieldUtility {

    public static String getCurrentDate() throws ParseException {
        Date curr = new Date();
        return format(curr, "yyyyMMdd");
    }

    public static String getCurrentTimestamp() throws ParseException {
        Date curr = new Date();
        return getFormatted(new Timestamp(curr.getTime()).toString(), "yyyy-MM-dd hh:mm:ss.SSS", "yyyyMMddHHmmssSSS");
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
        return getFormatted(timestamp, "yyyyMMddHHmmssSSS", "yyyy-MM-dd hh:mm:ss.SSS");
    }

    public static String date2Oracle(String date) throws ParseException {
        return getFormatted(date, "yyyyMMdd", "yyyy-MM-dd");
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

    public static String checkNull(String str) {
        return Objects.requireNonNullElse(str, "");
    }

    public static String checkNullDate(String str) {
        return Objects.requireNonNullElse(str, "0000-00-00 00:00:00");
    }

    public static String encodeBase64(String filepath) {
        try {
            Path path = Paths.get(filepath);
            byte[] bytes = Files.readAllBytes(path);

            return Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
