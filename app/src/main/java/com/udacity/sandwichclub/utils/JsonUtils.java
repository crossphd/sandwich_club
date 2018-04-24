package com.udacity.sandwichclub.utils;

import android.text.TextUtils;
import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sw = new Sandwich();

        if(TextUtils.isEmpty(json)){
            return null;
        }

//        try {
//            JSONObject data = new JSONObject(json);
//            JSONArray articleList = data.getJSONArray("articles");
//
//            for(int i = 0; i < articleList.length(); i++){
//                JSONObject story = articleList.getJSONObject(i);
//                JSONObject sourceJSON = story.getJSONObject("source");
//                String source = sourceJSON.getString("name");
//                String title = story.getString("title");
//                String author = story.getString("author");
//                String url = story.getString("url");
//                String image = story.getString("urlToImage");
//                String date = story.getString("publishedAt");
//
//
//                Article article = new Article(source, author,title,url,date, image);
//                articles.add(article);
//            }

        try {
            JSONObject data = new JSONObject(json);
            JSONObject name = data.getJSONObject("name");

            String mainName = name.getString("mainName");
            sw.setMainName(mainName);

            JSONArray alsoKnown = name.getJSONArray("alsoKnownAs");
            List<String> alsoKnownList = new ArrayList<>();
            for(int i = 0; i < alsoKnown.length(); i++){
                String akString = alsoKnown.getString(i);
                alsoKnownList.add(akString);
            }
            sw.setAlsoKnownAs(alsoKnownList);

            String origin = data.getString("placeOfOrigin");
            sw.setPlaceOfOrigin(origin);

            String description = data.getString("description");
            sw.setDescription(description);

            String image = data.getString("image");
            sw.setImage(image);

            JSONArray ingredients = data.getJSONArray("ingredients");
            List<String> ingredientsList = new ArrayList<>();
            for(int i = 0; i < ingredients.length(); i++){
                String ingredient = ingredients.getString(i);
                ingredientsList.add(ingredient);
            }
            sw.setIngredients(ingredientsList);

        } catch (JSONException e) {
            Log.e("JsonUtils", "Problem parsing the JSON results", e);
        }


        return sw;
    }
}
