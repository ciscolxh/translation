package com.luokse;

import com.luokes.google.Google;
import org.junit.Test;

public class ExampleUnitTest {
    @Test
    public void GoogleTest(){
        String tk = "273850.173049";
        System.out.println(Google.token("价格(%1$s)"));
    }
}
