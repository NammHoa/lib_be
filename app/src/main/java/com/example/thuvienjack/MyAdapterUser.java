package com.example.thuvienjack;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MyAdapterUser extends RecyclerView.Adapter<MyViewHolderUser>{
    private Context context;
    private List<DataClass> dataList;

    public MyAdapterUser(Context context, List<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolderUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyle_item_user, parent, false);
        return new MyViewHolderUser(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderUser holder, int position) {
        Glide.with(context).load(dataList.get(position).getDataImage()).into(holder.recImage);
        holder.recTitle.setText(dataList.get(position).getDataTitle());
        holder.recDesc.setText(dataList.get(position).getDataDesc());
        holder.recAuthor.setText(dataList.get(position).getDataAuthor());
        holder.recCate.setText(dataList.get(position).getDataCate());

        holder.recCardUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,DetailBookUserActivity.class);
                intent.putExtra("Image", dataList.get(holder.getAdapterPosition()).getDataImage());
                intent.putExtra("Description", dataList.get(holder.getAdapterPosition()).getDataDesc());
                intent.putExtra("Author", dataList.get(holder.getAdapterPosition()).getDataAuthor());
                intent.putExtra("Category", dataList.get(holder.getAdapterPosition()).getDataCate());
                intent.putExtra("Title", dataList.get(holder.getAdapterPosition()).getDataTitle());
                intent.putExtra("Key",dataList.get(holder.getAdapterPosition()).getKey());
                context.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void searchDataList(ArrayList<DataClass> searchList) {
        dataList = searchList;
        notifyDataSetChanged();
    }
}
class MyViewHolderUser extends RecyclerView.ViewHolder{
    ImageView recImage;
    TextView recTitle, recDesc, recAuthor, recCate;
    CardView recCardUser;
    public MyViewHolderUser(@NonNull View itemView) {
        super(itemView);
        recImage = itemView.findViewById(R.id.recImage);
        recTitle = itemView.findViewById(R.id.recTitle);
        recDesc = itemView.findViewById(R.id.recDesc);
        recAuthor = itemView.findViewById(R.id.recAuthor);
        recCate = itemView.findViewById(R.id.recCate);
        recCardUser = itemView.findViewById(R.id.recCardUser);
    }
}