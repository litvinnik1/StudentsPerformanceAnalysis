package com.example.kursova_oop;

public class Student {
    private int id;
    private String group;
    private String firstName;
    private String lastName;
    private int compmath;
    private int english;
    private int cyberwars;
    private int oop;

    private int mean;


    public Student(int id, String group, String firstName, String lastName, int compmath, int oop, int english, int cyberwars,int mean) {
        this.id = id;
        this.group = group;
        this.firstName = firstName;
        this.lastName = lastName;
        this.compmath = compmath;
        this.english = english;
        this.cyberwars = cyberwars;
        this.oop = oop;
        this.mean = mean;
    }

    public Student() {
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getGroup(){
        return group;
    }
    public void setGroup(){
        this.group = group;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getCompmath() {
        return compmath;
    }

    public void setCompmath(int compmath) {
        this.compmath = compmath;
    }

    public int getOop() {
        return oop;
    }

    public void setOop(int oop) {
        this.oop = oop;
    }

    public int getEnglish() {
        return english;
    }

    public void setEnglish(int english) {
        this.english = english;
    }

    public int getCyberwars() {
        return cyberwars;
    }

    public void setCyberwars(int cyberwars) {
        this.cyberwars = cyberwars;
    }
    public int getMean() {
        return mean;
    }

    public void setMean(int mean) {
        this.mean = mean;
    }

}
