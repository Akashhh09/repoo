package com.dev.akash.DataJpa.mail;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Component;

@Component
public class SmsConfig {
	
	
	public String sendSms(String number,String messages) {
		try {
			// Construct data
			System.out.println(number+" "+messages);
			String apiKey = "apikey=" + "Dvu8MeyJ0yQ-w4DavYjmAT2iUwiuXVNyPrq6ox9z37";
			String message = "&message=" + "Hello";
			String sender = "&sender=" + "TXTLCL";
			String numbers = "&numbers=" + "918850769702";
			
			// Send data
			HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
			String data = apiKey + numbers + message + sender;
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
			conn.getOutputStream().write(data.getBytes("UTF-8"));
			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			final StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) {
				stringBuffer.append(line);
			}
			rd.close();
			
			return stringBuffer.toString();
		} catch (Exception e) {
			System.out.println("Error SMS "+e);
			return "Error "+e;
		}
		}
		

}
