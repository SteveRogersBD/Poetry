package com.example.poetryapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.poetryapp.API.ApiClient;
import com.example.poetryapp.API.ApiInterface;
import com.example.poetryapp.Models.PoetryModel;
import com.example.poetryapp.R;
import com.example.poetryapp.Response.DeleteResponse;
import com.example.poetryapp.UpdatePoetry;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PoetryAdapter extends RecyclerView.Adapter<PoetryAdapter.ViewHolder>{
    Context context;
    List<PoetryModel>list;
    ApiInterface apiInterface;
    public PoetryAdapter(Context context, List<PoetryModel> list) {
        this.context = context;
        this.list = list;
        Retrofit retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.poetry_list_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        PoetryModel model = list.get(position);
        holder.poetry.setText(model.getPoetry_data());
        holder.poetName.setText(model.getPoet_name());
        holder.dateTime.setText(model.getDate_time());

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePoetry(model.getId()+"",position);
            }
        });

        holder.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdatePoetry.class);
                intent.putExtra("p_id",model.getId());
                intent.putExtra("p_data",model.getPoetry_data());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView poetry,poetName,dateTime;
        AppCompatButton updateBtn, deleteBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            poetName = itemView.findViewById(R.id.tv_poet_name);
            poetry = itemView.findViewById(R.id.tv_poetry_data);
            dateTime = itemView.findViewById(R.id.tv_date_time);

            updateBtn = itemView.findViewById(R.id.update_btn);
            deleteBtn = itemView.findViewById(R.id.delete_btn);
        }
    }
    private void deletePoetry(String id,int position)
    {
        apiInterface.deletePoetry(id).enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                try{
                    if(response!=null)
                    {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        if(response.body().getStatus().equals("1"))
                        {
                            list.remove(position);
                            notifyDataSetChanged();
                        }
                    }
                    else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }


                }catch(Exception e)
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
