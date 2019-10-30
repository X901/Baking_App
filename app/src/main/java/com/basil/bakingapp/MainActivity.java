package com.basil.bakingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    List<BakingModel> listBaking;
    @BindView(R.id.recipe_recyclerviet_id) RecyclerView recipeRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();


        BakingModel[] bakingArray;

        bakingArray = gson.fromJson(loadJSONFromAsset(this),  BakingModel[].class);

        List<BakingModel> bakingList = Arrays.asList(bakingArray);

        listBaking = bakingList;

        Log.v("Main Activity","BakingModel: "+ bakingArray[0].getName() );

        updateUI();

    }

    private void updateUI() {

        RecyclerViewMainAdapter recyclerViewMainAdapter = new RecyclerViewMainAdapter(this, listBaking);

        recipeRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));


        recipeRecyclerView.setAdapter(recyclerViewMainAdapter);
    }

    public String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("baking.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

}
