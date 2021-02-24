/********************************************************************
 Android Engineer Exercise
 Programmer(s): Jason Zochowski
 Date: 2/23/2021
 Purpose: This app is for downloading JSON data from an online API into a listview.
 Before displaying the data in the listview, I sorted the arraylist first by listid,
 and then by name (variables from API). I first downloaded the JSON data on a splashscreen
 before displaying it in MainActivity.
 *********************************************************************/

package edu.niu.students.z1836771.androidengineerexercise;

import java.io.Serializable;

//class used to initialize data downloaded from API
public class ListData implements Serializable {

    //private variables that correspond to JSON data variables
    private int id;
    private int listid;
    private String name;

    //default constructor
    ListData() {

    }

    //initialize private variables in default constructor
    ListData(int id, int listid, String name) {
        this.id = id;
        this.listid = listid;
        this.name = name;
    }

    //set private varables
    public void setId(int id) {
        this.id = id;
    }
    public void setListid(int listid) {
        this.listid = listid;
    }
    public void setName(String name) {
        this.name = name;
    }
    //get private variables
    public int getId() {
        return id;
    }
    public int getListid() {
        return listid;
    }
    public String getName() {
        return name;
    }
}
