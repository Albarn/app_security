package com.company.tests;

import com.company.BIConverter;
import org.junit.Assert;
import org.junit.Test;

public class BIConverterTests {

    @Test
    public void conversionShouldBeReversible(){
        for(int i=1;i<(1<<30);i++){
            Assert.assertEquals(i, BIConverter.toInt(BIConverter.toBytes(i)));
        }
    }
}
