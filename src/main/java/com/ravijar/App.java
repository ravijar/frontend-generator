package com.ravijar;

import com.ravijar.handler.OpenapiFileHandler;

public class App {

    public static void main(String[] args) {
        OpenapiFileHandler openapiFileHandler = new OpenapiFileHandler();
        System.out.println(openapiFileHandler.getSpecData().getInfo());
    }
}
