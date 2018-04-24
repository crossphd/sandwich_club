package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.CircleTransform;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;
import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = Objects.requireNonNull(intent).getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
//        Toast.makeText(this,json, Toast.LENGTH_LONG).show();
        Log.d("Json",json);
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .transform(new CircleTransform(50,0))
                .fit()
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sw) {

        TextView alsoKnown = findViewById(R.id.also_known_tv);
        StringBuilder ak = new StringBuilder();
        List<String> akList = sw.getAlsoKnownAs();
        for(String names : akList){
            ak.append(names).append("\n");
        }
        alsoKnown.setText(ak.toString());

        TextView origin = findViewById(R.id.origin_tv);
        origin.setText(sw.getPlaceOfOrigin());

        TextView description = findViewById(R.id.description_tv);
        description.setText(sw.getDescription());

        TextView ingredients = findViewById(R.id.ingredients_tv);
        StringBuilder ing = new StringBuilder();
        List<String> ingredientsList = sw.getIngredients();
        for(String name : ingredientsList){
            ing.append(name).append("\n");
        }
        ingredients.setText(ing.toString());


    }
}