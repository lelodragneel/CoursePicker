package com.example.project.coursepicker;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kenneth on 2/23/2018.
 */
public class logincheckerTest {

    @Test    //direct password match
    public void match() {
        loginchecker tester = new loginchecker();
        tester.setPassWord("Pass_word8");
        assertEquals(true, tester.match("Pass_word8"));
    }

    @Test	//password length
    public void okLength(){
        loginchecker tester = new loginchecker();
        assertEquals(true, tester.okLength("Not_ok20"));
    }

    @Test	//special character
    public void specChar(){
        loginchecker tester = new loginchecker();
        assertEquals(true, tester.specChar("PassWord_@"));
    }

    @Test	//number character
    public void pwNumCheck(){
        loginchecker tester = new loginchecker();
        assertEquals(true, tester.pwNumCheck("Pass@Word20"));
    }

    @Test	//upper & lower case letters
    public void caseCheck(){
        loginchecker tester = new loginchecker();
        assertEquals(true, tester.caseCheck("UPdown123$"));
    }

    //--------------------------------USERNAME------------------------------------

    @Test	//length at least 8 characters
    public void userLength(){
        loginchecker tester = new loginchecker();
        assertEquals(true, tester.userLength("pass_wd8"));
    }

    @Test	//first two characters are letters
    public void letterCheck(){
        loginchecker tester = new loginchecker();
        assertEquals(true, tester.letterCheck("user_wd"));
    }

    @Test	//last 6 characters are numbers
    public void unNumCheck(){
        loginchecker tester = new loginchecker();
        assertEquals(true, tester.unNumCheck("user202020"));
    }

}