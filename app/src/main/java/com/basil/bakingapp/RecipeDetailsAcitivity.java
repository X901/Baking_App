package com.basil.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailsAcitivity extends AppCompatActivity implements RecipeDetailsFragment.OnStepClickListener {

    List<Step> listSteps;
    List<Ingredient> listIngredient;
    long recipeId;
    String recipeName;

    Boolean mTwoPane = false;
    private LinearLayout layoutRecipe;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details_acitivity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        layoutRecipe = findViewById(R.id.layout_recipe);


        Intent intent = getIntent();

        ArrayList<Step> stepsList = (ArrayList<Step>) intent.getSerializableExtra(this.getString(R.string.stepsKey));
        listSteps = stepsList;

        ArrayList<Ingredient> ingredientList = (ArrayList<Ingredient>) intent.getSerializableExtra(this.getString(R.string.ingredientsKey));
        listIngredient = ingredientList;

        recipeName = intent.getStringExtra(this.getString(R.string.nameKey));
        recipeId = intent.getIntExtra(this.getString(R.string.idKey),0);

        setTitle(recipeName);

        Log.w("name:",recipeName);
        Log.w("id:","" + recipeId);




    }

    @Override
    public void onStepSelected(int position) {
        String stepVideoUrl = listSteps.get(position).getVideourl();
        String setpDiscription = listSteps.get(position).getDescription();


        String discriptionStepKey = this.getString(R.string.discriptionStepKey);
        String videoStepKey = this.getString(R.string.videoStepKey);


        if (findViewById(R.id.fragment_container_detail) != null) {
            mTwoPane = true;

            //fragmentJump(stepVideoUrl,setpDiscription);

            RecipeVideoPlayerFragment recipeVideoPlayerFragment = new RecipeVideoPlayerFragment();
            Bundle bundle = new Bundle();
            bundle.putString(discriptionStepKey, setpDiscription);
            bundle.putString(videoStepKey, stepVideoUrl);

            recipeVideoPlayerFragment.setArguments(bundle);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_detail, recipeVideoPlayerFragment)
                    .commit();
        } else {
            mTwoPane = false;

            Intent recipeVideoPlayerDetailsIntent = new Intent(this, RecipeVideoPlayerActivity.class);
            Bundle bundle = new Bundle();

            bundle.putString(discriptionStepKey, setpDiscription);
            bundle.putString(videoStepKey, stepVideoUrl);

            recipeVideoPlayerDetailsIntent.putExtras(bundle);

            this.startActivity(recipeVideoPlayerDetailsIntent);


        }

    }


    private String ingredientsToString(){
        StringBuilder result = new StringBuilder();
        for (Ingredient ingredient :  listIngredient){
            result.append( "• " + ingredient.getIngredient() + " (" + ingredient.getQuantity() + " " + ingredient.getMeasure() + ")" + "\n");



        }
        return result.toString();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_to_widget_menu, menu);

        // persistence.  Set checked state based on the fetchPopular boolean
        sharedPreferences = getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE);
        if ((sharedPreferences.getInt("ID", -1) == recipeId)){
            menu.findItem(R.id.mi_add_widget).setIcon(R.drawable.ic_star_white_48dp);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();



        if (id == R.id.mi_add_widget){
            boolean isRecipeInWidget = (sharedPreferences.getInt("ID", -1) == recipeId);

            // If recipe already in widget, remove it
            if (isRecipeInWidget){
                sharedPreferences.edit()
                        .remove("ID")
                        .remove("WIDGET_TITLE")
                        .remove("WIDGET_CONTENT")
                        .apply();

                item.setIcon(R.drawable.ic_star_border_white_48dp);
                Snackbar.make(layoutRecipe, "Widget Recipe Removed", Snackbar.LENGTH_SHORT).show();
            }
            // if recipe not in widget, then add it
            else{
                sharedPreferences
                        .edit()
                        .putInt("ID", Math.toIntExact(recipeId))
                        .putString("WIDGET_TITLE", recipeName)
                        .putString("WIDGET_CONTENT",ingredientsToString())
                        .apply();

                item.setIcon(R.drawable.ic_star_white_48dp);
                Snackbar.make(layoutRecipe, "Widget Recipe Added", Snackbar.LENGTH_SHORT).show();
            }

            // Put changes on the Widget
            ComponentName provider = new ComponentName(this, BakingWidget.class);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] ids = appWidgetManager.getAppWidgetIds(provider);
            BakingWidget bakingWidget = new BakingWidget();
            bakingWidget.onUpdate(this, appWidgetManager, ids);
        }

        return super.onOptionsItemSelected(item);


    }

}