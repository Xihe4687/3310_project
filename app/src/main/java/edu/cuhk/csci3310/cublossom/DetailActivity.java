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

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";
    // TODO:
    // Define other attributes as needed
    private String imagePath;
    private String canteenName;
    private String openingTime;
    private String ratingContent;
    private int rating;
    private String oCanteenName;
    private String oRatingContent;
    private int oRating;
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
        canteenName = intent.getStringExtra("canteenName");
        EditText editName = findViewById(R.id.edit_name);
        editName.setText(canteenName.toCharArray(),0,canteenName.length());
        openingTime = intent.getStringExtra("openingTime");
        TextView textGenus = findViewById(R.id.text_time);
        textGenus.setText(openingTime);
        rating = intent.getIntExtra("rating", 3);
        TextView textRichness = findViewById(R.id.textRating);

        if (rating == 1) {
            ratingContent = "Not good";
            textRichness.setText(ratingContent);
        } else if (rating == 2) {
            ratingContent = "Fair";
            textRichness.setText(ratingContent);
        } else {
            ratingContent = "Delicious";
            textRichness.setText(ratingContent);
        }

        position = intent.getIntExtra("position", 0);
        ratingContent = mPreferences.getString("ratingContent",null);
        textRichness.setText(ratingContent);
        rating = mPreferences.getInt("rating", 0);
        canteenName = mPreferences.getString("canteenName", null);
        editName.setText(canteenName.toCharArray(), 0, canteenName.length());
        oRatingContent = ratingContent;
        oRating = rating;
        oCanteenName = canteenName;

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


    // TODO:
    // Add more utility methods as needed
    public void minusRichness(){
        if(rating >= 2)
            rating--;
        else
            return;
        TextView textRating = findViewById(R.id.textRating);
        if(rating == 1)
        {
            ratingContent = "Not good";
            textRating.setText(ratingContent);
        }else {
            ratingContent = "Fair";
            textRating.setText(ratingContent);
        }
    }

    public void plusRichness(){
        if(rating <= 2)
            rating++;
        else
            return;
        TextView textRating = findViewById(R.id.textRating);
        if(rating == 2)
        {
            ratingContent = "Fair";
            textRating.setText(ratingContent);
        }else {
            ratingContent = "Delicious";
            textRating.setText(ratingContent);
        }
    }

    public void saveEntry()
    {
        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("canteenName", canteenName);
        intent.putExtra("rating", rating);
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
        editName.setText(oCanteenName);
        TextView textRating = findViewById(R.id.textRating);
        textRating.setText(oRatingContent);
        rating = oRating;

    }

    @Override
    public void onPause()
    {
        super.onPause();
        if(backPress)
            return;
        System.out.println("-----------Pause--------");
        SharedPreferences.Editor preferenceEditor = mPreferences.edit();
        preferenceEditor.putInt("rating", rating);
        preferenceEditor.putString("ratingContent", ratingContent);
        EditText editName = findViewById(R.id.edit_name);
        canteenName = editName.getText().toString();
        preferenceEditor.putString("canteenName", canteenName);
        preferenceEditor.apply();
        System.out.println("--------Pause Over---------");
    }
}