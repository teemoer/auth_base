package com.easy.auth;

import com.easy.auth.utils.MD5Util;

public class Test {

  private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(Test.class);
  public static void main(String[] args) {

    String md5Encode = MD5Util.MD5Encode("123", "UTF-8");
    logger.debug(md5Encode);

    /*Map<String, String> keyMap = RSAUtil.createKeys(1024);
    //String  publicKey = keyMap.get("publicKey");
    //String  privateKey = keyMap.get("privateKey");

          String  publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCWtm3K8SvWr7TKHYuOpDyR6Hgy2PNz9hE8jV-Moiux4DLtK_N8-_om7Zw6Q1FPMmqgpZoTJFcQm29oEd1nXrSRsZ6Kz-M0z142CCFadXdUd1QI0Cr8taWe5ZEGyY5HK5lanN20BVnZhlXeRZS1uV_mVallCf0-fIWpZSopeJZBVQIDAQAB";
          String  privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJa2bcrxK9avtModi46kPJHoeDLY83P2ETyNX4yiK7HgMu0r83z7-ibtnDpDUU8yaqClmhMkVxCbb2gR3WdetJGxnorP4zTPXjYIIVp1d1R3VAjQKvy1pZ7lkQbJjkcrmVqc3bQFWdmGVd5FlLW5X-ZVqWUJ_T58hallKil4lkFVAgMBAAECgYBsvBO8joGVc9FY3TYZ5K56iqFl9ha3fDn1WP4t5bbmr52udb5HVzikq1vCwVzBadt6KeBenSJfrfUTvYfik3ZsZyslDxNtM-A0OnnF08Um-JaZWVD2ajb6xLQg902EmXMLrfdBiDHkIFb7X4Dg7nbJLCcUJusM0h-DCVhuUV-5YQJBANCUaG9wqjBYumAITAvhfXbIYbwAzqKOs-l7UGdAzFZKSrOB313fl2QQrSK4_NE74xoT2ScVucHnQy52yUABjLkCQQC4-hXY2fOMASgRgzNA0QH54glj8mS4HixMSpxtCrT4n0oIc-6aIRcKestNmMBotqGgMIrwWieoZew83WynMWN9AkEAy0FT5ATuU96jACIVuR4rnSz30LUYp-BYAhZg672BYjVTRkow2NGiPajijwulkl_Blw_wdN3-_q3WEdk_lFfuQQJAOzuJ52jTfk-YfxMeRHEpStVA4IpsCjFPShbSRHCbRsnrMQ2-qAhUknyDwZNaea2Rz_P7ab_U1y3cGhllg5xjGQJBAIA2lHqxdtOINB3rX6gyrcV09JDvBLmOtmkX9TCFmwpGVjFA7RKvviJuHHgSYNhvXsaZUEy1hMVUY-mwRH8LIG0";

          logger.debug("公钥: \n\r" + publicKey);
          logger.debug("私钥： \n\r" + privateKey);

          logger.debug("公钥加密——私钥解密");
          String str = "ljj5209";
          logger.debug("\r明文：\r\n" + str);
          logger.debug("\r明文大小：\r\n" + str.getBytes().length);

          String decodedData = "";
    try {
    	String encodedData = RSAUtil.publicEncrypt(str, RSAUtil.getPublicKey(publicKey));
           logger.debug("密文：\r\n" + encodedData);
           logger.debug("密文长度：\r\n" + encodedData.length());

    	decodedData = RSAUtil.privateDecrypt(encodedData, RSAUtil.getPrivateKey(privateKey));
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
    	e.printStackTrace();
    }
          logger.debug("解密后文字: \r\n" + decodedData);*/
  }
}
