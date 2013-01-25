package net.yuanmomo.test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Test {
	public static void main(String[] args) {
		try {
			System.out.println(URLEncoder.encode("342398690@qq.com", "utf-8"));
			String temp="/month?slow";//"adfafdaf/12345";
			System.out.println(temp.replace("/?","?"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
