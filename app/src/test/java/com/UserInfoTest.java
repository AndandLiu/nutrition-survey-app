package com.jla388.sfu.greenfoodchallenge;
import org.junit.Test;
import static  org.junit.Assert.*;

/**
 * Tests UserInfo class
 */
public class UserInfoTest {
    @Test
    public void test(){
        UserInfo u = new UserInfo("May",12.5,"Vancouver",5,45,12.4,"male");

        assertEquals(u.getUserName(),"May");
        assertEquals(String.valueOf(u.getPreviousDayFoodData()),String.valueOf(12.5));
        assertEquals(u.getMunicipalityOfUser(),"Vancouver");
        assertEquals(u.getPledgedAmountOfSavings(),5);
        assertEquals(u.getTotalDaysRecorded(),45);
        assertEquals(String.valueOf(u.getTotalSavings()),String.valueOf(12.4));
        assertEquals(u.getUserGender(),"male");

        u.setUserName("J");
        u.setPreviousDayFoodData(30);
        u.setMunicipalityOfUser("Burnaby");
        u.setPledgedAmountOfSavings(4);
        u.setTotalDaysRecorded(56);
        u.setTotalSavings(323);
        u.setUserGender("female");

        assertEquals(u.getUserName(),"J");
        assertEquals(String.valueOf(u.getPreviousDayFoodData()),String.valueOf(30.0));
        assertEquals(u.getMunicipalityOfUser(),"Burnaby");
        assertEquals(u.getPledgedAmountOfSavings(),4);
        assertEquals(u.getTotalDaysRecorded(),56);
        assertEquals(String.valueOf(u.getTotalSavings()),String.valueOf(323.0));
        assertEquals(u.getUserGender(),"female");

        UserInfo u2 = new UserInfo();
        u2.setUserKey("000");
        assertEquals(u2.getUserKey(),"000");



    }
}