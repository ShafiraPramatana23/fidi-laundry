package com.fidilaundry.app.util.okhttphelper;


public interface CryptoStrategy {
    String encrypt(String body) throws Exception;

    String decrypt(String data) throws Exception;

}
