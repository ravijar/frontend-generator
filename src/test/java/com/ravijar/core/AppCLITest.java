package com.ravijar.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppCLITest {
    @Test
    void testAppCLI(){
        String[] args={"--help"};
        AppCLI.main(args);
    }
}