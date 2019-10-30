package com.basil.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewMainAdapter extends RecyclerView.Adapter<RecyclerViewMainAdapter.MainViewHolder> {

        private Context mContect;
        private List<BakingModel> mData;

        public RecyclerViewMainAdapter(Context mContect, List<BakingModel> mData) {
            this.mContect = mContect;
            this.mData = mData;
        }

        @Override
        public MainViewHolder onCreateViewHolder(ViewGroup viewGroup , int viewType) {

            LayoutInflater inflater = LayoutInflater.from(mContect);
            int layoutIdForListItem = R.layout.cardview_recipe_item;


            View view = inflater.inflate(layoutIdForListItem, viewGroup, false);

            return new MainViewHolder(view);
        }

        @Override
        public void onBindViewHolder( MainViewHolder holder, final int position) {

            String title = mData.get(position).getName();
            String servings = "" + mData.get(position).getServings();

            holder.tv_recipe_title.setText(title);
            holder.tv_serving_text.setText(servings);


            Picasso.get().load(getURLForResource(title)).into(holder.tv_recipe_poster);

            holder.posterCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ArrayList<Ingredient> ingredients = (ArrayList<Ingredient>) mData.get(position).getIngredients();
                    ArrayList<Step>  steps = (ArrayList<Step>) mData.get(position).getSteps();

                    String name = mData.get(position).getName();
                    long id = mData.get(position).getid();

                    Intent recipeDetailsIntent = new Intent(mContect,RecipeDetailsAcitivity.class);



                    String ingredientsIdKey = mContect.getString(R.string.ingredientsKey);
                    String stepsKey = mContect.getString(R.string.stepsKey);
                    String nameKey = mContect.getString(R.string.nameKey);
                    String idKey = mContect.getString(R.string.idKey);


                    recipeDetailsIntent.putExtra(ingredientsIdKey,ingredients);
                    recipeDetailsIntent.putExtra(stepsKey,steps);
                    recipeDetailsIntent.putExtra(nameKey,name);
                    recipeDetailsIntent.putExtra(idKey,id);

                    mContect.startActivity(recipeDetailsIntent);

                }
            });

        }

        static public String getURLForResource(String imageTitle){

            String imageUrl = "";

       if(imageTitle.equals("Nutella Pie")){
           imageUrl =  Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +R.drawable.nutella_pie).toString();

            }else if(imageTitle.equals("Brownies")){
            imageUrl = Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +R.drawable.brownies).toString();

           }else if(imageTitle.equals("Yellow Cake")) {
           imageUrl = Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.yellow_cake).toString();

       } else if(imageTitle.equals("Cheesecake")) {
           imageUrl = Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.cheesecake).toString();

       }

                return imageUrl;
            }





        @Override
        public int getItemCount() {
            return mData == null ? 0 : mData.size();

        }

        public static class MainViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.recipe_title_id) TextView tv_recipe_title;
            @BindView(R.id.recipe_serving_id) TextView tv_serving_text;
            @BindView(R.id.movie_recipe_id) ImageView tv_recipe_poster;
            @BindView(R.id.recipe_cardview_id) CardView posterCardView;

            public MainViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this,itemView);

            }
        }
    }


