package com.example.mandyamd.videoplayer.activity;


import android.os.Bundle;

import com.example.mandyamd.videoplayer.R;

public class Player extends android.support.v7.app.AppCompatActivity {

    //Player
    private com.google.android.exoplayer2.ui.SimpleExoPlayerView simpleExoPlayerView;
    private com.google.android.exoplayer2.SimpleExoPlayer player;

    //Logs
    final private String TAG = "PlayerActivity";

    //HLS
    private String VIDEO_URL = "https://video.media.yql.yahoo.com/v1/video/sapi/hlsstreams/91abef41-271c-3910-ba82-858c2021e1a5.m3u8?t=test-interview";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        //getting data from intent :
        String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                newString = null;
            } else {
                newString = extras.getString("url");
            }
        } else {
            newString = (String) savedInstanceState.getSerializable("url");
        }

        if (newString != null) {
            VIDEO_URL = newString;
        }


        //ExoPlayer implementation
        //Create a default TrackSelector
        com.google.android.exoplayer2.upstream.BandwidthMeter bandwidthMeter = new com.google.android.exoplayer2.upstream.DefaultBandwidthMeter();
        com.google.android.exoplayer2.trackselection.TrackSelection.Factory videoTrackSelectionFactory = new com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection.Factory(bandwidthMeter);
        com.google.android.exoplayer2.trackselection.TrackSelector trackSelector = new com.google.android.exoplayer2.trackselection.DefaultTrackSelector(videoTrackSelectionFactory);

        // Create a default LoadControl
        com.google.android.exoplayer2.LoadControl loadControl = new com.google.android.exoplayer2.DefaultLoadControl();
        //Bis. Create a RenderFactory
        com.google.android.exoplayer2.RenderersFactory renderersFactory = new com.google.android.exoplayer2.DefaultRenderersFactory(this);

        //Create the player
        player = com.google.android.exoplayer2.ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector, loadControl);
        simpleExoPlayerView = new com.google.android.exoplayer2.ui.SimpleExoPlayerView(this);
        simpleExoPlayerView = (com.google.android.exoplayer2.ui.SimpleExoPlayerView) findViewById(R.id.player_view);


        //Set media controller
        simpleExoPlayerView.setUseController(true);
        simpleExoPlayerView.requestFocus();

        // Bind the player to the view.
        simpleExoPlayerView.setPlayer(player);

        // Set the media source
        android.net.Uri mp4VideoUri = android.net.Uri.parse(VIDEO_URL);

        //Measures bandwidth during playback. Can be null if not required.
        com.google.android.exoplayer2.upstream.DefaultBandwidthMeter bandwidthMeterA = new com.google.android.exoplayer2.upstream.DefaultBandwidthMeter();

        //Produces DataSource instances through which media data is loaded.
        com.google.android.exoplayer2.upstream.DefaultDataSourceFactory dataSourceFactory = new com.google.android.exoplayer2.upstream.DefaultDataSourceFactory(this, com.google.android.exoplayer2.util.Util.getUserAgent(this, "PiwikVideoApp"), bandwidthMeterA);

        //Produces Extractor instances for parsing the media data.
        com.google.android.exoplayer2.extractor.ExtractorsFactory extractorsFactory = new com.google.android.exoplayer2.extractor.DefaultExtractorsFactory();

        //FOR LIVE STREAM LINK:
        com.google.android.exoplayer2.source.MediaSource videoSource = new com.google.android.exoplayer2.source.hls.HlsMediaSource(mp4VideoUri, dataSourceFactory, 1, null, null);
        final com.google.android.exoplayer2.source.MediaSource mediaSource = videoSource;

        player.prepare(videoSource);

        //ExoPLayer events listener
        player.addListener(new com.google.android.exoplayer2.Player.EventListener() {

            @Override
            public void onTimelineChanged(com.google.android.exoplayer2.Timeline timeline, Object manifest) {
   //             android.util.Log.v(TAG, "Listener-onTimelineChanged...");
            }

            @Override
            public void onTracksChanged(com.google.android.exoplayer2.source.TrackGroupArray trackGroups, com.google.android.exoplayer2.trackselection.TrackSelectionArray trackSelections) {
     //           android.util.Log.v(TAG, "Listener-onTracksChanged...");
            }

            @Override
            public void onLoadingChanged(boolean isLoading) {
          //      android.util.Log.v(TAG, "Listener-onLoadingChanged...isLoading:" + isLoading);
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
          //      android.util.Log.v(TAG, "Listener-onPlayerStateChanged..." + playbackState);

                switch (playbackState) {


                    case com.google.android.exoplayer2.Player.STATE_IDLE:
            //            android.util.Log.v(TAG, "STATE IDLE");
                        break;

                    case com.google.android.exoplayer2.Player.STATE_BUFFERING:
              //          android.util.Log.v(TAG, "STATE BUFFERING");
                        break;

                    case com.google.android.exoplayer2.Player.STATE_READY:
                //        android.util.Log.v(TAG, "STATE READY");
                        break;

                    case com.google.android.exoplayer2.Player.STATE_ENDED:
          //              android.util.Log.v(TAG, "STATE ENDED");
                        break;

                    default:
                        break;
                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {
    //            android.util.Log.v(TAG, "Listener-onRepeatModeChanged...");
            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(com.google.android.exoplayer2.ExoPlaybackException error) {
      //          android.util.Log.v(TAG, "Listener-onPlayerError...");
                player.stop();
                //  player.prepare(adsMediaSource);
                player.setPlayWhenReady(true);

            }

            @Override
            public void onPositionDiscontinuity(int reason) {
     //           android.util.Log.v(TAG, "Listener-onPositionDiscontinuity...");

            }

            @Override
            public void onPlaybackParametersChanged(com.google.android.exoplayer2.PlaybackParameters playbackParameters) {
        //        android.util.Log.v(TAG, "Listener-onPlaybackParametersChanged...");
            }

            @Override
            public void onSeekProcessed() {

            }
        });


    }

    //Android Life cycle
    @Override
    protected void onStop() {
        player.release();
        super.onStop();
   //     android.util.Log.v(TAG, "onStop()...");
    }

    @Override
    protected void onStart() {
        super.onStart();
    //    android.util.Log.v(TAG, "onStart()...");
    }

    @Override
    protected void onResume() {
        super.onResume();
    //    android.util.Log.v(TAG, "onResume()...");
    }

    @Override
    protected void onPause() {
        super.onPause();
     //   android.util.Log.v(TAG, "onPause()...");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
   //     android.util.Log.v(TAG, "onDestroy()...");
        player.release();
    }


}
