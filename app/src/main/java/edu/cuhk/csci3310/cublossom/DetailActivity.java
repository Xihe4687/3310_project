package edu.cuhk.csci3310.cublossom;

// TODO:
// Include your personal particular here
//

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";
    // TODO:
    // Define other attributes as needed
    private String imagePath;
    private String flowerName;
    private String genus;
    private String florence;
    private int richness;
    private String oFlowerName;
    private String oFlorence;
    private int oRichness;
    private int position;
    private SharedPreferences mPreferences;
    private boolean backPress;
    private String sharedPrefFile = "edu.cuhk.csci3310.cublossom";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mPreferences = getSharedPreferences(sharedPrefFile, 0);
        // TODO:
        // 1. Obtain data (e.g. JSON) via Intent
        // 2. Setup Views based on the data received
        // 3. Perform necessary data-persistence steps
        Intent intent = getIntent();
        imagePath = intent.getStringExtra("imagePath");
        Uri uri = Uri.parse(imagePath);
        ImageView imageLarge = findViewById(R.id.image_large);
        imageLarge.setImageURI(uri);
        flowerName = intent.getStringExtra("flowerName");
        EditText editName = findViewById(R.id.edit_name);
        editName.setText(flowerName.toCharArray(),0,flowerName.length());
        genus = intent.getStringExtra("genus");
        TextView textGenus = findViewById(R.id.text_genus);
        textGenus.setText(genus);
        richness = intent.getIntExtra("richness", 3);
        TextView textRichness = findViewById(R.id.textRichness);

        if (richness == 1) {
            florence = "Scattered";
            textRichness.setText(florence);
        } else if (richness == 2) {
            florence = "Clustered";
            textRichness.setText(florence);
        } else {
            florence = "High-Full";
            textRichness.setText(florence);
        }

        position = intent.getIntExtra("position", 0);
        florence = mPreferences.getString("florence",null);
        textRichness.setText(florence);
        richness = mPreferences.getInt("richness", 0);
        flowerName = mPreferences.getString("flowerName", null);
        editName.setText(flowerName.toCharArray(), 0, flowerName.length());
        oFlorence = florence;
        oRichness = richness;
        oFlowerName = flowerName;

        Button buttonFewer = (Button)findViewById(R.id.buttonFewer);
        buttonFewer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minusRichness();
            }
        });

        Button buttonMore = (Button)findViewById(R.id.buttonMore);
        buttonMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plusRichness();
            }
        });

        Button buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveEntry();
            }
        });

        Button buttonCancel = (Button) findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelEntry();
            }
        });
    }

/*
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        System.out.println("----------Save-------");
        savedInstanceState.putString("flowerName", flowerName);
        System.out.println(flowerName);
        savedInstanceState.putInt("richness", richness);
        System.out.println(richness);
        savedInstanceState.putString("florence", florence);
        System.out.println(florence);
        // TODO:
        // Perform necessary data-persistence steps




    }*/

 /*   @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // TODO:
        // Perform necessary data-persistence steps
        System.out.println("--------------Restore-----------");
        EditText editName = findViewById(R.id.edit_name);
        editName.setText(mPreferences.getString("flowerName", null));
        TextView textRichness = findViewById(R.id.textRichness);
        textRichness.setText(mPreferences.getString("florence", null));
        richness = mPreferences.getInt("richness", 0);





    }*/

    // TODO:
    // Add more utility methods as needed
    public void minusRichness(){
        if(richness >= 2)
            richness--;
        else
            return;
        TextView textFlorence = findViewById(R.id.textRichness);
        if(richness == 1)
        {
            florence = "Scattered";
            textFlorence.setText(florence);
        }else {
            florence = "Clustered";
            textFlorence.setText(florence);
        }
    }

    public void plusRichness(){
        if(richness <= 2)
            richness++;
        else
            return;
        TextView textFlorence = findViewById(R.id.textRichness);
        if(richness == 2)
        {
            florence = "Clustered";
            textFlorence.setText(florence);
        }else {
            florence = "High-Full";
            textFlorence.setText(florence);
        }
    }

    public void saveEntry()
    {
        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("flowerName", flowerName);
        intent.putExtra("richness", richness);
        startActivity(intent);

    }

    @Override
    public void onBackPressed(){
        backPress = true;
        super.onBackPressed();

    }
    public void cancelEntry()
    {
        EditText editName = findViewById(R.id.edit_name);
        editName.setText(oFlowerName);
        TextView textRichness = findViewById(R.id.textRichness);
        textRichness.setText(oFlorence);
        richness = oRichness;

    }

    @Override
    public void onPause()
    {
        super.onPause();
        if(backPress)
            return;
        System.out.println("-----------Pause--------");
        SharedPreferences.Editor preferenceEditor = mPreferences.edit();
        preferenceEditor.putInt("richness", richness);
        preferenceEditor.putString("florence", florence);
        EditText editName = findViewById(R.id.edit_name);
        flowerName = editName.getText().toString();
        preferenceEditor.putString("flowerName", flowerName);
        preferenceEditor.apply();
        System.out.println("--------Pause Over---------");
    }
}