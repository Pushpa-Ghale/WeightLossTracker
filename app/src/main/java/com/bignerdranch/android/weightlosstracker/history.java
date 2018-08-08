package com.bignerdranch.android.weightlosstracker;

import java.util.Date;
import java.util.List;
import java.util.UUID;



public class history {
    private Integer id;
    private String weight;
    private String date;




    public void setDate (String  inputDate){date = inputDate;}
    public String getDate(){return date;}

    public void setWeight (String inputWeight) {weight = inputWeight;}
    public String getWeight() {return weight;}

    public void setId (Integer inputId) {id = inputId;}
    public Integer getid() {return id;}
}



