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

    public String getName(String name){

        int count = 0;

        for(int i = 0; i < name.length(); i++){
            if (name.charAt(i) == ' '){
                count++;
            }
        }

        int length = name.length() - 1;

        while(name.charAt(length) == ' '){
            count--;
            length--;
        }

        if(count == 0){
            return getFirst(name);
        } else {

            return getInitials(name, count);
        }

    }

    public String getFirst(String name){

        return name.substring(0, name.length());
    }

    public String getInitials(String name, Integer count){

        StringBuilder currentName = new StringBuilder(name);

        int index = count;

        String firstName = name.substring(0, name.indexOf(' '));

        StringBuilder newName = new StringBuilder(firstName + " ");

        while(index != 0){

            int spaceIndex = currentName.indexOf(" ") + 1;

            currentName.setCharAt(spaceIndex - 1, 'a');

            newName.append(currentName.charAt(spaceIndex) + ".");

            index--;
        }

        return newName.toString();

    }
}
