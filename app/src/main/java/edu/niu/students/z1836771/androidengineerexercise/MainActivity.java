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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

//MainActivity is used for displaying the arraylist on a listview
public class MainActivity extends AppCompatActivity {

    ArrayList<ListData> listDataArrayList = new ArrayList<ListData>(); //object arraylist
    ArrayList<String> listViewValues = new ArrayList<String>(); //string arraylist for arrayadapter
    ListView listView; //listview

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent(); //get arraylist from SplashScreenActivity
        listDataArrayList = (ArrayList<ListData>)intent.getSerializableExtra("list");
        listViewValues = buildListViewValues(); //populate string arraylist with data from listDataArrayList

        listView = (ListView)findViewById(R.id.listview_id);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listViewValues); //default adapter for 1 string value
        listView.setAdapter(arrayAdapter); //set adapter
    }

    //first calls sortListViewDataArrayList to sort listDataArrayList by listId and then by name.
    //then adds listDataArrayList's listIds and names into a temporary arraylist with a space inbetween
    public ArrayList<String> buildListViewValues() {
        sortListViewDataArrayList(); //sort by listid and then name
        ArrayList<String> tempArrayList = new ArrayList<String>();
        for (int i = 0; i < listDataArrayList.size(); i++) //add listDataArrayList values into new string arraylist
        {
            tempArrayList.add("listId: " + listDataArrayList.get(i).getListid() + "         " + listDataArrayList.get(i).getName());
        }

        return tempArrayList; //return arraylist
    }

    //sort listDataArrayList first by listId and then name using Collections and it's compare methods
    public void sortListViewDataArrayList() {
        Collections.sort(listDataArrayList, new Comparator<ListData>() {
            @Override
            public int compare(ListData o1, ListData o2) {
                Integer listid1 = o1.getListid(); //listid
                Integer listid2 = o2.getListid();
                int compareListId = listid1.compareTo(listid2); //comparing listids
                if (compareListId != 0)
                    return compareListId; //return result if not 0

                Integer name1 = Integer.parseInt(o1.getName().substring(5)); //parse through string to only get the number after "Item"
                Integer name2 = Integer.parseInt(o2.getName().substring(5));
                int compareName = name1.compareTo(name2); //comparing names
                return compareName; //return result
            }
        });
    }

    //if back key was pressed, close app
    @Override
    public boolean onKeyDown(int i, KeyEvent event)
    {
        if (i == KeyEvent.KEYCODE_BACK)
        {
            finishAffinity(); //close app
        }
        return super.onKeyDown(i, event);
    }
}