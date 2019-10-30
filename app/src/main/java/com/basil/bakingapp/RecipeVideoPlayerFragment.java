package com.basil.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class RecipeVideoPlayerFragment extends Fragment {

    @BindView(R.id.exoPlayerView)
    SimpleExoPlayerView exoPlayerView;
    @BindView(R.id.discriptionTextView)
    TextView discriptionTextView;

    private Unbinder unbinder;

    SimpleExoPlayer exoPlayer;
    String videoUrl="";


    public RecipeVideoPlayerFragment() {}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recipe_video_player, container, false);
        unbinder = ButterKnife.bind(this, v);

        Bundle fragmentBundle = this.getArguments();

        if (fragmentBundle != null) {

            String discriptionStep = fragmentBundle.getString(this.getString(R.string.discriptionStepKey));
            String videoUrlStep = fragmentBundle.getString(this.getString(R.string.videoStepKey));

            discriptionTextView.setText(discriptionStep);

            if(!videoUrlStep.equals("")) {
                exoPlayerView.setVisibility(View.VISIBLE);
                videoUrl = videoUrlStep;
                initializePlayer(Uri.parse(videoUrl));
            }else {
                exoPlayerView.setVisibility(View.GONE);
            }
        }else {
            Bundle extras = getActivity().getIntent().getExtras();

            String discriptionStep = extras.getString(this.getString(R.string.discriptionStepKey));
            String videoUrlStep = extras.getString(this.getString(R.string.videoStepKey));

            discriptionTextView.setText(discriptionStep);

            if (videoUrlStep != null) {
                if (!videoUrlStep.equals("")) {
                    exoPlayerView.setVisibility(View.VISIBLE);
                    videoUrl = videoUrlStep;
                    initializePlayer(Uri.parse(videoUrl));
                }else {
                    exoPlayerView.setVisibility(View.GONE);
                }
            }

        }











        return v;
    }



    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        releasePlayer();
    }



    private void initializePlayer(Uri mediaUri) {
        if (exoPlayer == null) {

            Context context = getActivity().getApplicationContext();

            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);
            exoPlayerView.setPlayer(exoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(context, "Baking App");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    context, userAgent), new DefaultExtractorsFactory(), null, null);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {

        if (exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }


}
