package com.example.mandyamd.videoplayer.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.mandyamd.videoplayer.R;
import com.example.mandyamd.videoplayer.adapter.VideoAdapter;
import com.example.mandyamd.videoplayer.model.VideoData;
import com.example.mandyamd.videoplayer.rest.VideoApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.mandyamd.videoplayer.util.util.BASE_URL;

public class MainActivity extends AppCompatActivity {


    public interface RecyclerViewClickListener {

        void onClick(View view, int position);
    }

    private static Retrofit retrofit = null;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        connectAndGetApiData();
    }

    public void connectAndGetApiData() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }


        VideoApiInterface videoApiInterface = retrofit.create(VideoApiInterface.class);

        Call<List<VideoData>> call = videoApiInterface.getVideoData();
        call.enqueue(new Callback<List<VideoData>>() {
            @Override
            public void onResponse(Call<List<VideoData>> call, Response<List<VideoData>> response) {
                List<VideoData> videoData = response.body();
                RecyclerViewClickListener listener = (view, position) -> {
                    Intent myIntent = new Intent(getApplicationContext(), Player.class);
                    Bundle bundle = new Bundle();
                    String value = videoData.get(position).getUrl();
                    bundle.putString("url", value);
                    myIntent.putExtras(bundle);
                    view.getContext().startActivity(myIntent);
                };
                VideoAdapter myAdapter = new VideoAdapter(videoData, getApplicationContext(), listener);

                mRecyclerView.setAdapter(myAdapter);
            }

            @Override
            public void onFailure(Call<List<VideoData>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Call failed", Toast.LENGTH_LONG).show();
            }
        });
    }


}