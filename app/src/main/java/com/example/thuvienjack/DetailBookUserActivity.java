package com.example.thuvienjack;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DetailBookUserActivity extends AppCompatActivity {
    TextView detailTitle, detailDesc, detailAuthor, detailCate;
    ImageView detailImage, backDetailUser;
    Button btnBorrowBook;
    private ArrayList<ListBorrow> arr_list;
    private static final String TAG = "DetailUserActivity";
    DataClass book;
    String key = "";
    String imageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_book_user);

        detailTitle = findViewById(R.id.detailTitle);
        detailDesc = findViewById(R.id.detailDesc);
        detailAuthor = findViewById(R.id.detailAuthor);
        detailCate = findViewById(R.id.detailCate);
        detailImage = findViewById(R.id.detailImage);
        backDetailUser = findViewById(R.id.backDetailUser);
        btnBorrowBook = findViewById(R.id.btnBorrowBook);

        backDetailUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailBookUserActivity.this, ProductBookUserActivity.class);
                startActivity(intent);

            }
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            detailTitle.setText(bundle.getString("Title"));
            detailDesc.setText(bundle.getString("Description"));
            detailAuthor.setText(bundle.getString("Author"));
            detailCate.setText(bundle.getString("Category"));
            key = bundle.getString("Key");
            imageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(detailImage);

            if (imageUrl != null && !imageUrl.isEmpty()) {
                Glide.with(this).load(imageUrl).into(detailImage);
            } else {
                Toast.makeText(this, "Hình ảnh bị thiếu", Toast.LENGTH_SHORT).show();
            }
        }

        btnBorrowBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddListBorrow();
            }
        });

    }
    private void AddListBorrow() {
        String name = detailTitle.getText().toString();
        String hinh = imageUrl;

        ListBorrow List = new ListBorrow(name, hinh, key);

        // Cập nhật Firebase
        DatabaseReference ListRef = FirebaseDatabase.getInstance().getReference("Danh sach muon");
        ListRef.push().setValue(List).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Hiển thị thông báo thành công
                Toast.makeText(DetailBookUserActivity.this, "Thêm vào danh sách thành công", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Hiển thị thông báo lỗi
                Toast.makeText(DetailBookUserActivity.this, "Lỗi khi thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
            }
        });

        // Nếu cần, có thể cập nhật danh sách giỏ hàng cục bộ
        ListBorrowActivity.arr_list.add(List);

    }

}