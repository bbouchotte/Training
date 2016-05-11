package com.example.benj.training;

/**
 * Created by Benj on 23/04/2016.
 */
public class Auditor {
    private long id;
    private String name;
    private String firstname;
    private double note1;
    private double note2;
    private double note3;

    public Auditor(long id, String name, String firstname, double note1, double note2, double note3) {
        this.id = id;
        this.name = name;
        this.firstname = firstname;
        this.note1 = note1;
        this.note2 = note2;
        this.note3 = note3;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public double getNote1() {
        return note1;
    }

    public void setNote1(double note1) {
        this.note1 = note1;
    }

    public double getNote2() {
        return note2;
    }

    public void setNote2(double note2) {
        this.note2 = note2;
    }

    public double getNote3() {
        return note3;
    }

    public void setNote3(double note3) {
        this.note3 = note3;
    }

}
