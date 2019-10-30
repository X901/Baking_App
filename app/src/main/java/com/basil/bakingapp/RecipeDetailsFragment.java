package com.basil.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class RecipeDetailsFragment extends Fragment  {

    @BindView(R.id.ingredients_textView_id)
    TextView ingredientsText;

    List<Step> listSteps;
    @BindView(R.id.steps_recycleView_id)
    RecyclerView stepsRecyclerView;

    private Unbinder unbinder;

    public RecipeDetailsFragment(){}
    private Context mContext;

    OnStepClickListener mCallback;

    public interface OnStepClickListener{
        void onStepSelected(int position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        unbinder = ButterKnife.bind(this, v);
        mContext=v.getContext();

        Intent intent = getActivity().getIntent();
        ArrayList<Ingredient> ingredientList = (ArrayList<Ingredient>) intent.getSerializableExtra(this.getString(R.string.ingredientsKey));

        displayIngredient(ingredientList);

        ArrayList<Step> stepsList = (ArrayList<Step>) intent.getSerializableExtra(this.getString(R.string.stepsKey));
        listSteps = stepsList;

        updateUI();


        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mCallback = (OnStepClickListener) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString() +
                    " must implement OnStepClickListener");
        }
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void displayIngredient(ArrayList<Ingredient> ingredientList){

        String ingredient = "";

        for(int i=0 ; i<ingredientList.size() ;i++){
            ingredient = ingredient + "• " + ingredientList.get(i).getIngredient() + " (" + ingredientList.get(i).getQuantity() + " " + ingredientList.get(i).getMeasure() + ")" + "\n";
        }

        ingredientsText.setText(ingredient);

    }

    private void updateUI() {

        Context context = getActivity().getApplicationContext();
        RecyclerViewStepsAdapter recyclerViewStepsAdapter = new RecyclerViewStepsAdapter(context,listSteps,(int clickedItemIndex) -> {
            mCallback.onStepSelected(clickedItemIndex);
        });

        stepsRecyclerView.setLayoutManager(new GridLayoutManager(context, 1));

        stepsRecyclerView.setAdapter(recyclerViewStepsAdapter);
    }



}
