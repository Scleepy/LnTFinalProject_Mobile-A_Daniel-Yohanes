package com.daniel.courseapplication;

public class Utility {

    public static boolean isNumeric(String number){
        try {
            Integer.parseInt(number);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validCourseID(String courseID){
        return courseID.startsWith("CR-") && courseID.length() == 9 && isNumeric(courseID.substring(3, courseID.length()));
    }

    public boolean validName(String name){
        return name.length() >= 5;
    }

    public boolean validEmail(String email){
        return email.endsWith(".com") && email.contains("@");
    }

    public boolean validPassword(String password){
        return password.length() >= 5;
    }

    public boolean validConfirm(String password, String confirmPassword){
        return password.equals(confirmPassword);
    }
}
