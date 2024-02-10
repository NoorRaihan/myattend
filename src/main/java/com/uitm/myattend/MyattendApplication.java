package com.uitm.myattend;

import com.uitm.myattend.utility.FieldUtility;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigInteger;
import java.text.ParseException;
@SpringBootApplication
public class MyattendApplication {

	public static void main(String[] args) throws ParseException {
		SpringApplication.run(MyattendApplication.class, args);
		System.out.println(FieldUtility.timestamp2Oracle(FieldUtility.getCurrentTimestamp()));
	}

}
