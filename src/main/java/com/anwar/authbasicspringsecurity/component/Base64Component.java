package com.anwar.authbasicspringsecurity.component;


import java.util.Base64;


import org.springframework.stereotype.Component;

@Component
public class Base64Component {

	public static String encode(String str) {
		
        // Encode into Base64 format
        String BasicBase64format = Base64.getEncoder()
                  .encodeToString(str.getBytes());
 
        // print encoded String
//        System.out.println("Encoded String: " + BasicBase64format);
        return BasicBase64format;
	}
	
	public static String decode(String str) {

	     // decode into String from encoded format
	        byte[] actualByte = Base64.getDecoder()
	                                .decode(str);
	 
	        String actualString = new String(actualByte);
	 
	        // print actual String
//	        System.out.println("Decoded String: " + actualString);
	        return actualString;
	}
}
