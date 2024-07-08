package com.jla388.sfu.greenfoodchallenge;

import org.junit.Test;
import static org.junit.Assert.*;
public class PledgeTest {

    @Test
    public void test(){
        Pledge p = new Pledge();
        assertEquals(p.getKey(),"none");
        assertEquals(p.getName(),"none");
        assertEquals(p.getUserLogoLocation(),"none");
        assertEquals(p.getMunicipality(),"none");
        assertEquals(p.getPledgedAmount(),"none");
        assertEquals(p.getAvgSavings(),"none");

        Pledge p1 = new Pledge("a","b","c","d","e","f");
        assertEquals(p1.getKey(),"a");
        assertEquals(p1.getName(),"b");
        assertEquals(p1.getMunicipality(),"c");
        assertEquals(p1.getAvgSavings(),"d");
        assertEquals(p1.getPledgedAmount(),"e");
        assertEquals(p1.getUserLogoLocation(),"f");

        p1.setAvgSavings("1");
        p1.setKey("2");
        p1.setMunicipality("3");
        p1.setName("4");
        p1.setPledgedAmount("5");
        p1.setUserLogoLocation("6");

        assertEquals(p1.getUserLogoLocation(),"6");
        assertEquals(p1.getPledgedAmount(),"5");
        assertEquals(p1.getAvgSavings(),"1");
        assertEquals(p1.getMunicipality(),"3");
        assertEquals(p1.getName(),"4");
        assertEquals(p1.getKey(),"2");

    }
}

