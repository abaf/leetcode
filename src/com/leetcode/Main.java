package com.leetcode;

import com.leetcode.crypt.DesSecurity;
import com.leetcode.crypt.RSAsecurity;
import org.apache.commons.codec.binary.Hex;

import java.util.Base64;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        try {
            String ccbKey= "2012PinganVitality075522628888ForShenZhenBelter075561869839";
            // write your code here
            Map<String, String> cfpamfKeyPairs = RSAsecurity.getKey(1024);
            byte[] ccbDesKeys = buildKey(ccbKey);

            String rawContent = "测试加密，帐号xxx密码xxx";
            Base64.Decoder decoder = Base64.getDecoder();
            Base64.Encoder encoder = Base64.getEncoder();
            //MD5WithRsa签名
            byte[] signData = RSAsecurity.MD5WithRsaSignPri(cfpamfKeyPairs.get("privateKey"), rawContent);
            String signDataString = encoder.encodeToString(signData);

            //拿到建行密钥加密
            byte[] desData = DesSecurity.encryptByDESede(ccbDesKeys, rawContent.getBytes("UTF-8"));
            //加密后再做base64编码
            String finalText = encoder.encodeToString(desData);

            //建行收到报文
            //1.建行解密报文,通过中和农信提供的R
            byte[] recvData = decoder.decode(finalText);
            byte[] result = DesSecurity.decryptByDESede(ccbDesKeys, recvData);
            String recvRawText = new String(result);
            byte[] recvSignData = decoder.decode(signDataString);
            //2 建行再验签，通过中和农信提供的公钥验签
            if(RSAsecurity.IsSignOk(cfpamfKeyPairs.get("publicKey"), recvSignData, recvRawText))
            {
                System.out.println("验签通过！");
                System.out.println(recvRawText);
            }else{
                System.out.println("验签失败！");
            }

            //System.out.println(enText);
        /*System.out.println("===================================");
        String deText = RSAsecurity.deByPubKey(enText, keyPairs.get("publicKey"));
        System.out.println(deText);*/
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private static byte[] buildKey(String key) {
        try {
            byte[] keybytes = new byte[24];
            byte[] rawKeyBytes = key.getBytes("UTF-8");
            if(rawKeyBytes.length<keybytes.length){
                System.arraycopy(rawKeyBytes,0,keybytes,0,rawKeyBytes.length);
            } else {
                System.arraycopy(rawKeyBytes,0,keybytes,0,keybytes.length);
            }
            return keybytes;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
