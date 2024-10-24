package com.example.thuvienjack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;


public class HomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private PhotoAdapter photoAdapter;
    TextView btnAllBook;

    DatabaseReference databaseReference;

    RecyclerView ryc_category;
    List<DataClass> dataList;
    MyAdapterUser adapterUser;


    ImageView homeBottom, authorBottom, bookBottom, profileBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnAllBook = findViewById(R.id.btnAllBook);
        homeBottom = findViewById(R.id.homeBottom);
        bookBottom = findViewById(R.id.bookBottom);
        profileBottom = findViewById(R.id.profileBottom);


        bookBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,ProductBookUserActivity.class);
                startActivity(intent);
            }
        });
        profileBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,ProfileUserActivity.class);
                startActivity(intent);
            }
        });
        homeBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setCancelable(false);
                builder.setView(R.layout.progress_layout);
                AlertDialog dialog = builder.create();
                dialog.show();

                databaseReference = FirebaseDatabase.getInstance().getReference("Quan ly sach");

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        dialog.dismiss();

                        Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        dialog.dismiss();
                    }
                });
            }
        });

        btnAllBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProductBookUserActivity.class);
                startActivity(intent);
            }
        });

        viewPager = findViewById(R.id.viewpager);
        circleIndicator = findViewById(R.id.circle_indicator);



        photoAdapter = new PhotoAdapter(this, getListPhoto());
        viewPager.setAdapter(photoAdapter);

        circleIndicator.setViewPager(viewPager);
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());


        ryc_category = findViewById(R.id.rcv_category);
        ryc_category.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        dataList = new ArrayList<>();
        adapterUser = new MyAdapterUser(HomeActivity.this, dataList);
        ryc_category.setAdapter(adapterUser);
        databaseReference = FirebaseDatabase.getInstance().getReference("Quan ly sach");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot i: snapshot.getChildren()){
                    DataClass dataClass = i.getValue(DataClass.class);
                    dataClass.setKey(i.getKey());
                    dataList.add(dataClass);
                }
                adapterUser.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                finish();
            }
        });
    }

    private List<Photo> getListPhoto() {
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.img));
        list.add(new Photo(R.drawable.img_1));
        list.add(new Photo(R.drawable.img_2));
        list.add(new Photo(R.drawable.img_3));
        return list;
    }
}