package com.example.project.coursepicker;

/**
 * Created by Kenneth on 2/23/2018.
 */

public class loginchecker {

	String passWord;

public void setPassWord(String x){
        passWord = x;
        }

//-----------------------------PASSWORD------------------------------------------------

public boolean match(String x){			//check if password is direct match
    return x.equals(passWord);
}


public boolean okLength(String x) {		//check length of password
    return x.length() >= 8;
}

public boolean specChar(String x){		//check for at least 1 special character
        for (int i=0; i < x.length(); i++){
        int ascii = (int) x.charAt(i);
        if (!(ascii >= 65 && ascii <= 90) && !(ascii >= 97 && ascii <= 122)){
        return true;
        }
        }
        return false;
        }

public boolean pwNumCheck(String x){		//check for at least 1 number character
        for (int i=0; i < x.length(); i++){
        int ascii = (int) x.charAt(i);
        if (ascii >= 48 && ascii <= 57){
        return true;
        }
        }
        return false;
        }

public boolean caseCheck(String x){		//check for at least 1 upper case and 1 lower case character
        boolean upper = false;
        boolean lower = false;
        for (int i=0; i < x.length(); i++){
        int ascii = (int) x.charAt(i);
        if (ascii >= 65 && ascii <= 90){
        upper = true;
        }
        if (ascii >= 97 && ascii <= 122){
        lower = true;
        }
        if (upper == true && lower == true){
        return true;
        }
        }
        return false;
        }

//-----------------------------USER NAME CASES------------------------

public boolean userLength(String x){		//user name length must be 8 characters
    return x.length() == 8;

}

public boolean letterCheck(String x){		//user names first two characters must be letters
        Boolean condition1 = false;
        Boolean condition2 = false;

        int ascii = (int) x.charAt(0);
        if ((ascii >= 65 && ascii <= 90) || ascii >= 97 && ascii <= 122){
        condition1 = true;
        }
        ascii = (int) x.charAt(1);
        if ((ascii >= 65 && ascii <= 90) || ascii >= 97 && ascii <= 122){
        condition2 = true;
        }
    return condition1 == true && condition2 == true;
}

public boolean unNumCheck(String x){		//user names last six characters must be numbers
        for (int i = x.length()-6; i < x.length(); i++){
        int ascii = (int) x.charAt(i);
        if (!(ascii >= 48 && ascii <= 57)){
        return false;
        }
        }
        return true;
        }
}
