package com.example.anujmore.bmi;

public class Result {
    private double bmi2;
    private String s1;
    private  String time1;

    Result(){}

   /* public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }*/



    public double getBmi2() {
        return bmi2;
    }

    public void setBmi2(double bmi2) {
        this.bmi2 = bmi2;
    }

    public String getS1() {
        return s1;
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

    @Override
    public String toString() {
        return "Your Bmi is :" + " " + bmi2 + " and " + s1 + " ";
    }

    /*public Result(double bmi2) {
        this.bmi2 = bmi2;
    }

    public Result(String s1) {
        this.s1 = s1;
    }
    */

    public Result(double bmi2, String s1) {
        this.time1=time1;
        this.bmi2 = bmi2;
        this.s1 = s1;
    }
}

//String time1,