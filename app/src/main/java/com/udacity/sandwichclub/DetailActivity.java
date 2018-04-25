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

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @BindView(R.id.also_known_tv) TextView alsoKnown;
    @BindView(R.id.origin_tv) TextView origin;
    @BindView(R.id.description_tv) TextView description;
    @BindView(R.id.ingredients_tv) TextView ingredients;
    @BindView(R.id.image_iv) ImageView ingredientsIv;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

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

        StringBuilder ak = new StringBuilder();
        List<String> akList = sw.getAlsoKnownAs();
        for(String names : akList){
            ak.append(names).append("\n");
        }
        alsoKnown.setText(ak.toString());

        StringBuilder ing = new StringBuilder();
        List<String> ingredientsList = sw.getIngredients();
        for(String name : ingredientsList){
            ing.append(name).append("\n");
        }
        ingredients.setText(ing.toString());

        origin.setText(sw.getPlaceOfOrigin());

        description.setText(sw.getDescription());
    }
}
