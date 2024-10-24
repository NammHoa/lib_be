package Author;

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
import com.example.thuvienjack.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapterAuthorUser extends RecyclerView.Adapter<MyViewAuthorUserHolder> {
    private Context context;
    private List<DataAuthorClass> dataList;

    public MyAdapterAuthorUser(Context context, List<DataAuthorClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewAuthorUserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_author_user, parent,false);
        return new MyViewAuthorUserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewAuthorUserHolder holder, int position) {
        Glide.with(context).load(dataList.get(position).getDataImageAuthor()).into(holder.recImageAuthor);
        holder.recTitleAuthor.setText(dataList.get(position).getDataTitleAuthor());
        holder.recDateAuthor.setText(dataList.get(position).getDataDesAuthor());
        holder.recDateAuthor.setText(dataList.get(position).getDataDateAuthor());


        holder.recCardAuthorUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailAuthorUser.class);
                intent.putExtra("Image", dataList.get(holder.getAdapterPosition()).getDataImageAuthor());
                intent.putExtra("Description", dataList.get(holder.getAdapterPosition()).getDataDesAuthor());
                intent.putExtra("Date", dataList.get(holder.getAdapterPosition()).getDataDateAuthor());
                intent.putExtra("Title", dataList.get(holder.getAdapterPosition()).getDataTitleAuthor());
                intent.putExtra("Key",dataList.get(holder.getAdapterPosition()).getKey());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public void searchDataList(ArrayList<DataAuthorClass> searchList){
        dataList = searchList;
        notifyDataSetChanged();
    }
}
class MyViewAuthorUserHolder extends RecyclerView.ViewHolder{

    ImageView recImageAuthor;
    TextView recTitleAuthor, recDescAuthor, recDateAuthor;
    CardView recCardAuthorUser;
    public MyViewAuthorUserHolder(@NonNull View itemView) {
        super(itemView);


        recImageAuthor = itemView.findViewById(R.id.recImageAuthor);
        recTitleAuthor = itemView.findViewById(R.id.recTitleAuthor);
        recDescAuthor= itemView.findViewById(R.id.recDescAuthor);
        recDateAuthor = itemView.findViewById(R.id.recDateAuthor);
        recCardAuthorUser = itemView.findViewById(R.id.recCardAuthorỦe);
    }
}