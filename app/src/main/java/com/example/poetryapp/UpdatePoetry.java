package com.example.poetryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.poetryapp.API.ApiClient;
import com.example.poetryapp.API.ApiInterface;
import com.example.poetryapp.Response.DeleteResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpdatePoetry extends AppCompatActivity {

    Toolbar toolbar;
    EditText poetryData;
    AppCompatButton submitBtn;
    int poetryId;
    String poetryDataString;
    ApiInterface apiInterface;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_poetry);

        initialization();
        setuptoolbar();
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p_data = poetryData.getText().toString();
                if(p_data.equals(""))
                {
                    poetryData.setError("Field is empty");
                }
                else{
                    callApi(p_data,poetryId+"");
                }
            }
        });

    }

    private void initialization(){
        toolbar = findViewById(R.id.update_poetry_toolbar);
        poetryData = findViewById(R.id.update_poetry_data_et);
        submitBtn= findViewById(R.id.update_submit_data_btn);

        poetryId= getIntent().getIntExtra("p_id",0);
        poetryDataString= getIntent().getStringExtra("p_data");

        poetryData.setText(poetryDataString);

        Retrofit retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);


    }
    private void setuptoolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void callApi(String pData,String pId){
        apiInterface.updatePoetry(pData,pId).enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                try{
                    if(response.body().getStatus().equals("1")){
                        Toast.makeText(UpdatePoetry.this, "Updated!!!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(UpdatePoetry.this, "Not Updated!!!", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e)
                {
                    Log.e("Failure",e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable throwable) {
                Log.e("Failure",throwable.getLocalizedMessage());
            }
        });

    }
}