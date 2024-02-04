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
import java.util.Map;
import java.util.Objects;

public class FieldUtility {

    public final static int ADMIN_ROLE = 1;
    public final static int LECTURER_ROLE = 2;
    public final static int STUDENT_ROLE = 3;
    public final static int REGISTANT_ROLE = 4;

    public static String getCurrentDate() throws ParseException {
        Date curr = new Date();
        return format(curr, "yyyyMMdd");
    }

    public static String getCurrentTimestamp() throws ParseException {
        Date curr = new Date();
        return getFormatted(new Timestamp(curr.getTime()).toString(), "yyyy-MM-dd HH:mm:ss.SSS", "yyyyMMddHHmmssSSS");
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
        return getFormatted(timestamp, "yyyyMMddHHmmssSSS", "yyyy-MM-dd HH:mm:ss.SSS");
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

    public static String encodeFileBase64(String filepath) {
        try {
            Path path = Paths.get(filepath);
            byte[] bytes = Files.readAllBytes(path);

            return Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String encodeByteBase64(byte[] bytes) throws Exception {
        return encodeBase64(null, bytes);
    }

    public static String encodeStringBase64(String str) throws Exception {
        return encodeBase64(str, null);
    }

    public static String encodeBase64(String str, byte[] bytes) throws Exception {
        if(bytes != null) {
            return Base64.getEncoder().encodeToString(bytes);
        }else if(str != null){
            return Base64.getEncoder().encodeToString(str.getBytes());
        }else {
            throw new Exception("null value to be encode");
        }
    }

    public static String decodeStringBase64(String str) throws Exception {
        return decodeBase64(str, null);
    }

    public static String decodeBytesBase64(byte[] bytes) throws Exception {
        return decodeBase64(null, bytes);
    }

    public static String decodeBase64(String str, byte[] bytes) throws Exception {
        if(str != null) {
            return new String (Base64.getDecoder().decode(str.getBytes()));
        }else if(bytes != null){
            return new String (Base64.getDecoder().decode(bytes));
        }else {
            throw new Exception("null value to be encode");
        }
    }

    public static void requiredValidator(Map<String, Object> body, String [][] validate) throws Exception{
        for(String [] data : validate) {
            String key = data[0];
            String errMsg = data[1];
            if(body.get(key) == null || body.get(key).toString().isEmpty()) {
                throw new Exception(errMsg);
            }
        }
    }
}
