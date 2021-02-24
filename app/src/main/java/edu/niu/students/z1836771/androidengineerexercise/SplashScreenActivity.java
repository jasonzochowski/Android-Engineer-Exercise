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

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

//SplashScreenActivity used to download JSON data into an object arraylist before displaying it in MainActivity.java
public class SplashScreenActivity extends AppCompatActivity {

    ArrayList<ListData> listDataArrayList = new ArrayList<ListData>();
    int asyncTaskCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //download and parse through JSON data
        loadJSON();
    }

    //download JSON data
    public void loadJSON() {
        String stringURL = "https://fetch-hiring.s3.amazonaws.com/hiring.json"; //API
        URL url = createURL(stringURL); //convert to url
        if (url != null)
        {
            SplashScreenActivity.getListData getListData = new SplashScreenActivity.getListData();
            getListData.execute(url);
        }
        else //url is null
        {
            Toast.makeText(SplashScreenActivity.this, "invalid url", Toast.LENGTH_LONG).show();
        }
    }

    //create url
    private URL createURL(String stringURL)
    {
        try
        {
            return new URL(stringURL); //return url
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    //download and parse JSON data into an ArrayList of object type ListData
    public class getListData extends AsyncTask<URL, Void, JSONArray>
    {
        @Override //build JSONArray
        protected JSONArray doInBackground(URL...params)
        {
            HttpURLConnection connection = null;
            try //open connection
            {
                connection = (HttpURLConnection) params[0].openConnection();
                int response = connection.getResponseCode();
                if (response == HttpURLConnection.HTTP_OK) //connection was successful
                {
                    StringBuilder builder = new StringBuilder();
                    try (BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream())
                    ))
                    {
                        String line;
                        while ((line = reader.readLine()) != null)
                        {
                            builder.append(line); //build line used in jsonarray
                        }
                    }
                    catch (IOException e) //failed to connect
                    {
                        Toast.makeText(SplashScreenActivity.this, "Unable to connect to api", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                    return new JSONArray(builder.toString()); //return JSONArray
                }
                else //failed to connect
                {
                    Toast.makeText(SplashScreenActivity.this, "Unable to connect to api", Toast.LENGTH_LONG).show();
                }
            }
            catch (Exception e) //failed to connect
            {
                Toast.makeText(SplashScreenActivity.this, "Unable to connect to api", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            finally {
                connection.disconnect(); //disconnect from api
            }
            return null;
        }
        @Override
        protected void onPostExecute(JSONArray listArray) //once JSON data is downloaded, call convertJSONtoArrayList
        {
            convertJSONtoArrayList(listArray); //convert to JSONArray and add each element to ArrayLists
        }
    }

    //convert JSON to an ArrayList
    private void convertJSONtoArrayList(JSONArray listArray)
    {
        try
        {
            for (int i = 0; i < listArray.length(); i++) { //loop through JSONArray and parse through JSON data
                JSONObject listObject = listArray.getJSONObject(i); //JSON object
                int id = listObject.getInt("id");
                int listid = listObject.getInt("listId");
                String name = listObject.getString("name");

                if (!(name.equals("") || name.equals("null"))) //don't add elements to listDataArrayList that do not have a name
                {
                    listDataArrayList.add(new ListData(id, listid, name));
                }
                asyncTaskCounter++; //completed task
            }

            if (asyncTaskCounter == listArray.length()) { //start activity (all async tasks completed)
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class); //start MainActivity
                Bundle extras = new Bundle();
                extras.putSerializable("list", listDataArrayList); //arraylist to pass to MainActivity
                intent.putExtras(extras);
                startActivity(intent); //start activity
            }
        }
        catch (Exception e) //failed
        {
            e.printStackTrace();
        }
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