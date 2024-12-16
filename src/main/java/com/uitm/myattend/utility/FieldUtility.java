package com.uitm.myattend.utility;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
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

    //static attributes for general use
    public final static int ADMIN_ROLE = 1;
    public final static int LECTURER_ROLE = 2;
    public final static int STUDENT_ROLE = 3;


    //get the current date with custom format
    public static String getCurrentDate() throws ParseException {
        Date curr = new Date();
        return format(curr, "yyyyMMdd");
    }

    //get current timestamp
    public static String getCurrentTimestamp() throws ParseException {
        Date curr = new Date();
        return getFormatted(new Timestamp(curr.getTime()).toString(), "yyyy-MM-dd HH:mm:ss.SSS", "yyyyMMddHHmmssSSS");
    }

    //receive and return any date to custom format
    public static String getFormatted(String date, String inFormat, String outFormat) throws ParseException {
        Date newDate = new SimpleDateFormat(inFormat).parse(date);
        return format(newDate, outFormat);
    }

    //formation process
    public static String format(Date date, String format) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    //convert java date to oracle timestamp format
    public static String timestamp2Oracle(String timestamp) throws ParseException {
        return getFormatted(timestamp, "yyyyMMddHHmmssSSS", "yyyy-MM-dd HH:mm:ss.SSS");
    }

    //convert date java to oracle date format
    public static String date2Oracle(String date) throws ParseException {
        return getFormatted(date, "yyyyMMdd", "yyyy-MM-dd");
    }

    //generate custom UUID
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

    //check null field to return empty string
    public static String checkNull(String str) {
        return Objects.requireNonNullElse(str, "");
    }

    //check null field to return empty string
    public static String checkNull(Object str) {
        return Objects.requireNonNullElse((String) str, "");
    }

    //check null date to return empty date
    public static String checkNullDate(String str) {
        return Objects.requireNonNullElse(str, "0000-00-00 00:00:00");
    }

    //encode/convert file to base64 format
    public static String encodeFileBase64(String filepath) {
        try {
            Path path = Paths.get(filepath);
            byte[] bytes = Files.readAllBytes(path);

            return Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //encode from byte to base64
    public static String encodeByteBase64(byte[] bytes) throws Exception {
        return encodeBase64(null, bytes);
    }

    //encode from string to base64
    public static String encodeStringBase64(String str) throws Exception {
        return encodeBase64(str, null);
    }

    //main process for base64 encoding
    public static String encodeBase64(String str, byte[] bytes) throws Exception {
        if(bytes != null) {
            return Base64.getEncoder().encodeToString(bytes);
        }else if(str != null){
            return Base64.getEncoder().encodeToString(str.getBytes());
        }else {
            throw new Exception("null value to be encode");
        }
    }

    //base64 string decoding
    public static String decodeStringBase64(String str) throws Exception {
        return decodeBase64(str, null);
    }

    //base64 byte decoding
    public static String decodeBytesBase64(byte[] bytes) throws Exception {
        return decodeBase64(null, bytes);
    }

    //main process for base64 decoding
    public static String decodeBase64(String str, byte[] bytes) throws Exception {
        if(str != null) {
            return new String (Base64.getDecoder().decode(str.getBytes()));
        }else if(bytes != null){
            return new String (Base64.getDecoder().decode(bytes));
        }else {
            throw new Exception("null value to be encode");
        }
    }

    //encrypt base64 with the private key
    public static String encryptStringBase64(String keys, String str) throws Exception{
        byte[] key = keys.getBytes();
        byte[] data = str.getBytes();

        byte[] xored = xorWithKey(data, key);
        return FieldUtility.encodeByteBase64(xored);
    }

    //decrypt base64 string with key
    public static String decryptStringBase64(String keys, String str) throws Exception{
        byte[] key = keys.getBytes();
        byte[] data = str.getBytes();

        byte[] decoded = FieldUtility.decodeBytesBase64(data).getBytes();
        byte[] xored = xorWithKey(decoded, key);
        return new String(xored, StandardCharsets.UTF_8);
    }

    //xor the base64 byte with the key byte since java base64 not support base64 encoding with key encryption
    public static byte[] xorWithKey(byte[] data, byte[] key) {
        byte[] output = new byte[data.length];

        for(int i=0; i<data.length; i++) {
            output[i] = (byte) (data[i] ^ key[i % key.length]);
        }
        return output;
    }

    //generic function for frontend "require" validation to return error if the field is empty
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
