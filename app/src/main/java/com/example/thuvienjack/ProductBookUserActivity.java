package com.example.thuvienjack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductBookUserActivity extends AppCompatActivity {

    SearchView searchView;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    RecyclerView RycProduct;
    List<DataClass> dataList;
    MyAdapterUser adapterUser;
    ImageView btnBackHome, IVList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_book_user);
        IVList = findViewById(R.id.IVList);
        searchView = findViewById(R.id.searchProduct);
        searchView.clearFocus();


        IVList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductBookUserActivity.this, ListBorrowActivity.class);
                startActivity(intent);
            }
        });

        btnBackHome = findViewById(R.id.btnBackHome);
        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductBookUserActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });

        RycProduct = findViewById(R.id.rycProduct);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ProductBookUserActivity.this, 2);
        RycProduct.setLayoutManager(gridLayoutManager);
        dataList = new ArrayList<>();
        adapterUser = new MyAdapterUser(ProductBookUserActivity.this, dataList);
        RycProduct.setAdapter(adapterUser);
        databaseReference = FirebaseDatabase.getInstance().getReference("Quan ly sach");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot i : snapshot.getChildren()) {
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
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });
    }
    public void searchList(String text) {
        ArrayList<DataClass> searchList = new ArrayList<>();
        for (DataClass dataClass : dataList) {
            if (dataClass.getDataTitle().toLowerCase().contains(text.toLowerCase()) ||
                    dataClass.getDataAuthor().toLowerCase().contains(text.toLowerCase()) ||
                    dataClass.getDataCate().toLowerCase().contains(text.toLowerCase())) {

                searchList.add(dataClass);
            }
        }
        adapterUser.searchDataList(searchList);
    }

}