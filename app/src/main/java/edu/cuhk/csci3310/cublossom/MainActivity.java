package edu.cuhk.csci3310.cublossom;

// TODO:
// Include your personal particular here
//

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.FileUtils;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private FlowerListAdapter mAdapter;
    private int position;
    private String flowerName;
    private int richness;
    private int num;
    private ArrayList<Integer> rate = new ArrayList<Integer>();
    // Initially a list storing image path
    // TODO: replace with another data structure to store the JSON fields
    private ArrayList<Flower> mFlowerList = new ArrayList<>();
    private ArrayList<String> mImagePathList = new ArrayList<>();
    private final String mRawFilePath = "android.resource://edu.cuhk.csci3310.cublossom/raw/";
    private final String mAppFilePath = "/data/data/edu.cuhk.csci3310.cublossom/";
    private final String mDrawableFilePath = "android.resource://edu.cuhk.csci3310.cublossom/drawable/";
    private String sharedPrefFile = "edu.cuhk.csci3310.cublossom";
    private SharedPreferences mPreferences;

    // TODO:
    // You may define more data members as needed
    // ... Rest of MainActivity code ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPreferences = getSharedPreferences(sharedPrefFile,0);
        // Initially put random data into the image list, modify to pass correct info read from JSON

        //------------------------------------------------------------------------------------------
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //------------------------------------------------------------------------------------------

        try {
            this.readJson();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        num = mFlowerList.size();
        for(int i=0; i<num; i++) {
            String name = mFlowerList.get(i).getFilename();
            rate.add(mFlowerList.get(i).getRichness());
            mImagePathList.add(mDrawableFilePath + name.substring(0, name.length()-4));
        }

        Intent intent = getIntent();
        position = intent.getIntExtra("position",-1);

        if(position != -1)
        {
            mFlowerList.get(position).setFlowerName(intent.getStringExtra("flowerName"));
            rate.set(position,intent.getIntExtra("richness", 0));
            mFlowerList.get(position).setRichness(rate.get(position));
            savePreference();
        }

        for(int i = 0; i < num; i++)
        {
            rate.set(i, mPreferences.getInt("rate " + i, 0));
            mFlowerList.get(i).setRichness(rate.get(i));
        }

        // Get a handle to the RecyclerView.
        mRecyclerView = findViewById(R.id.recyclerview);
        // Create an adapter and supply the data to be displayed,
        // initially just a list of image path
        // TODO: Update and pass more information as needed
        mAdapter = new FlowerListAdapter(this, mImagePathList, mFlowerList);

        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        // TODO: Update the layout manager
        //  i.e. Set up Grid according to the orientation of phone
        int columns;
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            columns = 2;
        else
            columns = 3;
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, columns));
    }

    // TODO:
    // Overriding extra callbacks, e.g. onStop(), onActivityResult(), etc.
    // as well as other utility method for JSON file read here

    public void readJson() throws IOException, JSONException {
        InputStream stream = getResources().openRawResource(R.raw.cu_flowers);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder stringBuilder = new StringBuilder();
        String line = bufferedReader.readLine();
        while (line != null){
            stringBuilder.append(line);
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        // This responce will have Json Format String
        String responce = stringBuilder.toString();
        JSONObject jsonObject = new JSONObject(responce);
        JSONArray flowers = jsonObject.getJSONArray("flowers");
        JSONObject f;
        for(int i = 0; i < flowers.length(); i++)
        {
            f = flowers.getJSONObject(i);
            mFlowerList.add(new Flower(f.getString("filename"), f.getString("flower_name"),
                    f.getString("genus"), f.getInt("richness")));
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        savePreference();
    }

    public void savePreference()
    {
        SharedPreferences.Editor preferenceEditor = mPreferences.edit();
        for(int i = 0; i < num; i++)
        {
            preferenceEditor.putInt("rate " + i, rate.get(i));
        }
        preferenceEditor.apply();
    }


}
