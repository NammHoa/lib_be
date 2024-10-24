package com.example.thuvienjack;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.util.ArrayList;

public class ListBorrowActivity extends AppCompatActivity {

    private Toolbar toolbarListBorrow;
    ListView lvListBorrow;
    static TextView txtthongbao;

    public static ArrayList<ListBorrow> arr_list = new ArrayList<>(); // Khởi tạo danh sách giỏ hàng

    DatabaseReference ListRef;
    String key = "";
    String imageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_borrow);
        addControl();
        ActionToolBar();



        getDanhsachFromFirebase();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getDanhsachFromFirebase();
        listView();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        arr_list.clear();

    }



    private void listView() {
        if (!arr_list.isEmpty()) {
            txtthongbao.setText("");
        } else {
            txtthongbao.setText("Danh sách trống");
        }

        ListBorrowAdapter ListAdapter = new ListBorrowAdapter(ListBorrowActivity.this, arr_list);
        lvListBorrow.setAdapter(ListAdapter);
        ListAdapter.notifyDataSetChanged();
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarListBorrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Drawable drawable = toolbarListBorrow.getNavigationIcon();
        if (drawable != null) {
            drawable.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(drawable);
        }
        toolbarListBorrow.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    private void addControl() {
        toolbarListBorrow = findViewById(R.id.toolbarListBorrow);
        lvListBorrow = findViewById(R.id.lvListBorrow);
        txtthongbao = findViewById(R.id.txtthongbao);
        ListRef = FirebaseDatabase.getInstance().getReference("Danh sach muon");
    }


    private void getDanhsachFromFirebase() {
        if (ListRef == null) {
            ListRef = FirebaseDatabase.getInstance().getReference("Danh sach muon");
        }

        if (ListRef != null) {
            ListRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    arr_list.clear();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ListBorrow Ds = snapshot.getValue(ListBorrow.class);
                        if (Ds != null) {
                            Ds.setKey(snapshot.getKey());
                            arr_list.add(Ds);
                        }
                    }

                    listView();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("ListBorrowActivity", "Failed to read value.", databaseError.toException());
                }
            });
        } else {
            Log.e("ListBorrowActivity", "DatabaseReference ListRef is null.");
        }
    }

}
