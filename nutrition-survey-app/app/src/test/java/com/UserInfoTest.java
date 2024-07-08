package com.jla388.sfu.greenfoodchallenge;
import org.junit.Test;
import static  org.junit.Assert.*;

/**
 * Tests UserInfo class
 */
public class UserInfoTest {
    @Test
    public void TestGetName(){
        UserInfo U=new UserInfo("User",60,20,"male");
        assertEquals("User",U.getName());
        U.setName("Abe");
        assertEquals("Abe",U.getName());
    }

    @Test
    public void TestSetName(){
        UserInfo U=new UserInfo("Wendy",45,21,"female");
        U.setName("Null");
        assertEquals("Null",U.getName());
        U.setName("T");
        assertEquals("T",U.getName());
    }

    @Test
    public void TestSetAge(){
        UserInfo U=new UserInfo("Wendy",45,21,"female");
        U.setAge(40);
        assertEquals(40,U.getAge());
        U.setAge(30);
        assertEquals(30,U.getAge());
    }

    @Test
    public void TestGetAge(){
        UserInfo U=new UserInfo("Wendy",45,21,"female");
        assertEquals(21,U.getAge());
        U.setAge(34);
        assertEquals(34,U.getAge());
        U.setAge(66);
        assertEquals(66,U.getAge());
    }

    @Test
    public void TestSetWeight(){
        UserInfo U=new UserInfo("Wendy",45,21,"female");
        assertEquals(45,U.getWeight());
        U.setWeight(70);
        assertEquals(70,U.getWeight());
        U.setWeight(66);
        assertEquals(66,U.getWeight());
    }

    @Test
    public void TestGetWeight(){
        UserInfo U=new UserInfo("Wendy",45,21,"female");
        assertEquals(45,U.getWeight());
        U.setWeight(100);
        assertEquals(100,U.getWeight());
        U.setWeight(66);
        assertEquals(66,U.getWeight());
    }
    @Test
    public void TestSetGender(){
        UserInfo U=new UserInfo("Wendy",45,21,"female");
        U.setGender("male");
        assertEquals("male",U.getGender());
        U.setGender("female");
        assertEquals("female",U.getGender());
    }

    @Test
    public void TestGetGender(){
        UserInfo U=new UserInfo("Wendy",45,21,"female");
        assertEquals("female",U.getGender());
        U.setGender("male");
        assertEquals("male",U.getGender());
        U.setGender("female");
        assertEquals("female",U.getGender());
    }
}
