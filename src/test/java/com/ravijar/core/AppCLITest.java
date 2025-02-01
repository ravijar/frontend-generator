package com.ravijar.core;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppCLITest {
    @Test
    @Disabled
    void testAppCLI(){
        String[] args={"--help"};
        AppCLI.main(args);
    }
}