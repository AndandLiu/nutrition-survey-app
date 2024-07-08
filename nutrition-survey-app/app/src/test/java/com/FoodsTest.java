package com.jla388.sfu.greenfoodchallenge;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests Foods class
 */
public class FoodsTest {

    @Test
    public void test(){

        Foods testfood = new Foods("banana",10,10);
        double expected = testfood.getAmount_suggest();
        double actual = 10;

        assertEquals(actual,expected,0.5);

        assertEquals(testfood.getName(),"banana");


        testfood.setIs_selected(true);
        assertEquals(testfood.getIs_selected(),true);

        testfood.setName("hi");
        assertEquals(testfood.getName(),"hi");

        testfood.setAmount_input(10);

        assertEquals(testfood.getAmount_input(),10,0.1);

    }

    @Test
    public void test70(){
        //setAmountSuggest   //getAmountSuggest
        //getC02eActual,    //setC02eActual
        //getC02e/Kilo,     //setC02e/Kilo

        Foods aFood = new Foods("banana",10,10);
        aFood.setAmount_suggest(3);

        assertEquals(aFood.getAmount_suggest(),3,1);

        aFood.setCO2e_actual(10);
        assertEquals(aFood.getCO2e_actual(),10,1);

        aFood.setCO2ePerKilo(3);

        assertEquals(aFood.getCO2ePerKilo(),3,0.1);



    }



}
