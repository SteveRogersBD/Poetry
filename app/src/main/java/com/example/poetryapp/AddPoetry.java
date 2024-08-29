package com.example.poetryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

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

public class AddPoetry extends AppCompatActivity {

    Toolbar toolbar;
    EditText poetName, poetryData;
    AppCompatButton submitButton;
    ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_poetry);
        initialization();
        setUpToolBar();



        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String poetryDataString = poetryData.getText().toString();
                String poetNameString = poetName.getText().toString();
                if(poetryDataString.equals("")){
                    poetryData.setError("Field is empty");
                }
                else{
                    if(poetNameString.equals(""))
                    {
                        poetName.setError("Field is empty");
                    }
                    else{
                        callApi(poetryDataString,poetNameString);
                    }
                }
            }
        });
    }

    private void initialization(){

        toolbar = findViewById(R.id.add_poet_name_toolbar);
        poetName = findViewById(R.id.add_poet_name_et);
        poetryData = findViewById(R.id.add_poetry_data_et);
        submitButton = findViewById(R.id.submit_data_btn);

        Retrofit retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    private void setUpToolBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //unables the back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void callApi(String poetryData,String poetName){
        apiInterface.addPoetry(poetryData,poetName).enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                try{
                    if(response.body().getStatus().equals("1")){
                        Toast.makeText(AddPoetry.this, "Added successfully!!!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(AddPoetry.this, "Couldn't add!!!", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
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