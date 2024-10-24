package com.example.thuvienjack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

public class ListBorrowAdapter extends BaseAdapter {
    private final Context context;
    private ArrayList<ListBorrow> arr_list;

    public ListBorrowAdapter(Context context, ArrayList<ListBorrow> arr_list) {
        this.context = context;
        this.arr_list = arr_list != null ? arr_list : new ArrayList<>();
    }

    public ListBorrowAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return arr_list.size();
    }

    @Override
    public Object getItem(int position) {
        return arr_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;



        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listborrow, parent, false);
            holder = new ViewHolder();
            holder.ivhinhGH = convertView.findViewById(R.id.ivHinhListBorrow);
            holder.txttenGH = convertView.findViewById(R.id.txttenSPDS);
            holder.btnDeleteListBorrow = convertView.findViewById(R.id.btnDeleteListBorrow);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ListBorrow Ds = arr_list.get(position);
        holder.txttenGH.setText(Ds.getTenDS());

        if (Ds.getHinhDS() != null && !Ds.getHinhDS().isEmpty()) {
            Glide.with(context)
                    .load(Ds.getHinhDS())
                    .into(holder.ivhinhGH);
        } else {
            holder.ivhinhGH.setImageResource(R.drawable.img_1);
        }


        holder.btnDeleteListBorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = Ds.getKey(); // Lấy key của mục cần xóa
                if (key != null) {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Danh sach muon").child(key);
                    databaseReference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(context, "Xóa sách thành công", Toast.LENGTH_SHORT).show();

                            // Kiểm tra xem danh sách có rỗng trước khi xóa
                            if (!arr_list.isEmpty()) {
                                arr_list.remove(position);
                                notifyDataSetChanged(); // Cập nhật lại ListView
                            } else {
                                // Có thể hiển thị một thông báo rằng danh sách đã trống
                                Toast.makeText(context, "Danh sách đã trống", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        return convertView;
    }

    static class ViewHolder {
        ImageView ivhinhGH;
        TextView txttenGH;
        ImageView btnDeleteListBorrow;

    }


}