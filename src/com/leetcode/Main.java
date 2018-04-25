package com.leetcode;

import com.leetcode.crypt.RSAsecurity;

import java.util.Map;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Map<String, String> keyPairs = RSAsecurity.getKey(1024);

        String rawContent = "测试加密，帐号xxx密码xxx";
        String  enText = RSAsecurity.enByPriKey(rawContent, keyPairs.get("privateKey"));
        System.out.println(enText);
        System.out.println("===================================");
        String deText = RSAsecurity.deByPubKey(enText, keyPairs.get("publicKey"));
        System.out.println(deText);
    }
}
