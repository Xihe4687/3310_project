package edu.cuhk.csci3310.cublossom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private FlowerListAdapter mAdapter;
    private int position;
    private int num;
    private ArrayList<Integer> rating = new ArrayList<Integer>();
    private ArrayList<Canteen> mCanteenList = new ArrayList<>();
    private ArrayList<String> mImagePathList = new ArrayList<>();
    private final String mDrawableFilePath = "android.resource://edu.cuhk.csci3310.cublossom/drawable/";
    private String sharedPrefFile = "edu.cuhk.csci3310.cublossom";
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPreferences = getSharedPreferences(sharedPrefFile,0);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);

            }
        });
        //------------------------------------------------------------------------------------------

        try {
            this.readJson();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        num = mCanteenList.size();
        for(int i=0; i<num; i++) {
            String name = mCanteenList.get(i).getFilename();
            rating.add(mCanteenList.get(i).getRating());
            mImagePathList.add(mDrawableFilePath + name.substring(0, name.length()-4));
        }

        Intent intent = getIntent();
        position = intent.getIntExtra("position",-1);

        if(position != -1)
        {
            mCanteenList.get(position).setCanteenName(intent.getStringExtra("canteenName"));
            rating.set(position,intent.getIntExtra("rating", 0));
            mCanteenList.get(position).setRating(rating.get(position));
            savePreference();
        }

        for(int i = 0; i < num; i++)
        {
            rating.set(i, mPreferences.getInt("rating " + i, 0));
            mCanteenList.get(i).setRating(rating.get(i));
        }

        mRecyclerView = findViewById(R.id.recyclerview);

        mAdapter = new FlowerListAdapter(this, mImagePathList, mCanteenList);

        mRecyclerView.setAdapter(mAdapter);
        int columns;
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            columns = 2;
        else
            columns = 3;
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, columns));
    }

    public void readJson() throws IOException, JSONException {
        InputStream stream = getResources().openRawResource(R.raw.cu_canteens);
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
        JSONArray flowers = jsonObject.getJSONArray("canteens");
        JSONObject f;
        for(int i = 0; i < flowers.length(); i++)
        {
            f = flowers.getJSONObject(i);
            mCanteenList.add(new Canteen(f.getString("filename"), f.getString("canteen_name"),
                    f.getString("opening_time"), f.getInt("rating")));
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
            preferenceEditor.putInt("rating " + i, rating.get(i));
        }
        preferenceEditor.apply();
    }


}
