package com.example.poetryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.poetryapp.API.ApiClient;
import com.example.poetryapp.API.ApiInterface;
import com.example.poetryapp.Adapters.PoetryAdapter;
import com.example.poetryapp.Models.PoetryModel;
import com.example.poetryapp.Response.GetPoetryResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import com.example.poetryapp.R;
public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PoetryAdapter poetryAdapter;
    List<PoetryModel> poetryModels = new ArrayList<>();
    ApiInterface apiInterface;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialization();
        setSupportActionBar(toolbar);
        getData();
    }

    private void initialization() {
        recyclerView = findViewById(R.id.recyclerView);
        Retrofit retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        toolbar = findViewById(R.id.main_toolbar);

    }

    private void setAdapter(List<PoetryModel> poetryModels) {
        poetryAdapter = new PoetryAdapter(MainActivity.this, poetryModels);
        recyclerView.setAdapter(poetryAdapter);
    }

    private void getData() {
        apiInterface.getPoetry().enqueue(new Callback<GetPoetryResponse>() {
            @Override
            public void onResponse(Call<GetPoetryResponse> call, Response<GetPoetryResponse> response) {
                try {
                    if (response != null && response.body() != null) {
                        if (response.body().getStatus().equals("1")) {
                            setAdapter(response.body().getData());
                        } else {
                            Toast.makeText(MainActivity.this, response.body().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "No response from server", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Log.e("exp", e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<GetPoetryResponse> call, Throwable throwable) {
                Log.e("failure", throwable.getLocalizedMessage());
                Toast.makeText(MainActivity.this, "Failed to connect to server", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.addPoetry)
        {
            Intent a = new Intent(MainActivity.this,AddPoetry.class);
            startActivity(a);
            return true;
        }
        else{
            return super.onOptionsItemSelected(item);
        }

    }
}
