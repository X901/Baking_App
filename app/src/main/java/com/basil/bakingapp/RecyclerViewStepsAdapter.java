package com.basil.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



public class RecyclerViewStepsAdapter extends RecyclerView.Adapter<RecyclerViewStepsAdapter.StepsViewHolder> {

    private Context mContext;
    private List<Step> mData;
    private OnStepClickListener mOnStepClickListener;

    private boolean mTwoPane;

    public RecyclerViewStepsAdapter(Context mContext, List<Step> mData, OnStepClickListener onStepClickListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.mOnStepClickListener = onStepClickListener;
    }

    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup viewGroup , int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        int layoutIdForListItem = R.layout.steps_recyclerview_row;


        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);

        return new StepsViewHolder(view,mOnStepClickListener);
    }

    @Override
    public void onBindViewHolder( StepsViewHolder holder, final int position) {

        String id = "" + mData.get(position).getid();
        String title =  mData.get(position).getShortDescription();

        holder.tv_step_id.setText(id);
        holder.tv_step_title.setText(title);




    }






    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();

    }

    public static class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.idTextview) TextView tv_step_id;
        @BindView(R.id.stepTextView) TextView tv_step_title;
OnStepClickListener onStepClickListener;


        public StepsViewHolder(View itemView, OnStepClickListener onStepClickListener) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            this.onStepClickListener = onStepClickListener;

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
onStepClickListener.onStepClick(getAdapterPosition());
        }
    }

    public interface OnStepClickListener{
        void onStepClick(int position);
    }
}


