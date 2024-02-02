package com.uitm.myattend.service;

import com.uitm.myattend.model.AttendanceModel;
import com.uitm.myattend.repository.AttendanceRepository;
import com.uitm.myattend.utility.FieldUtility;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final Environment env;

    public AttendanceService(AttendanceRepository attendanceRepository, Environment env) {
        this.attendanceRepository = attendanceRepository;
        this.env = env;
    }

    public boolean insert(AttendanceModel attendance) {
        try {

            if(!attendanceRepository.insert(attendance)) {
                throw new Exception("Failed to process an attendance");
            }

            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String encryptStringBase64(String str) throws Exception{
        byte[] key = env.getProperty("app.key").getBytes();
        byte[] data = str.getBytes();

        byte[] xored = xorWithKey(data, key);
        return FieldUtility.encodeByteBase64(xored);
    }

    public String decryptStringBase64(String str) throws Exception{
        byte[] key = env.getProperty("app.key").getBytes();
        byte[] data = str.getBytes();

        byte[] xored = xorWithKey(data, key);
        return FieldUtility.decodeBytesBase64(xored);
    }

    public byte[] xorWithKey(byte[] data, byte[] key) {
        byte[] output = new byte[data.length];

        for(int i=0; i<data.length; i++) {
            output[i] = (byte) (data[i] ^ key[i % key.length]);
        }
        return output;
    }
}
