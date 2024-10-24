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
import com.example.thuvienjack.DataClass;
import com.example.thuvienjack.DetailBookActivity;
import com.example.thuvienjack.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapterAuthor extends RecyclerView.Adapter<MyViewAuthorHolder> {
    private Context context;
    private List<DataAuthorClass> dataList;

    public MyAdapterAuthor(Context context, List<DataAuthorClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewAuthorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyler_author, parent,false);
        return new MyViewAuthorHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewAuthorHolder holder, int position) {
        Glide.with(context).load(dataList.get(position).getDataImageAuthor()).into(holder.recImageAuthor);
        holder.recTitleAuthor.setText(dataList.get(position).getDataTitleAuthor());
        holder.recDateAuthor.setText(dataList.get(position).getDataDesAuthor());
        holder.recDateAuthor.setText(dataList.get(position).getDataDateAuthor());


        holder.recCardAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailAuthorActivity.class);
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
class MyViewAuthorHolder extends RecyclerView.ViewHolder{

    ImageView recImageAuthor;
    TextView recTitleAuthor, recDescAuthor, recDateAuthor;
    CardView recCardAuthor;
    public MyViewAuthorHolder(@NonNull View itemView) {
        super(itemView);


        recImageAuthor = itemView.findViewById(R.id.recImageAuthor);
        recTitleAuthor = itemView.findViewById(R.id.recTitleAuthor);
        recDescAuthor = itemView.findViewById(R.id.recDescAuthor);
        recDateAuthor = itemView.findViewById(R.id.recDateAuthor);
        recCardAuthor = itemView.findViewById(R.id.recCardAuthor);
    }
}