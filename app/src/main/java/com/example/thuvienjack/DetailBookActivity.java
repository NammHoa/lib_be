package com.example.thuvienjack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.github.clans.fab.FloatingActionButton;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DetailBookActivity extends AppCompatActivity {

    TextView detailTitle, detailDesc, detailAuthor, detailCate;
    ImageView detailImage;
    FloatingActionButton deleteButton, editButton;
    String key = "";
    String imageUrl = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_book);

        detailTitle = findViewById(R.id.detailTitle);
        detailDesc = findViewById(R.id.detailDesc);
        detailAuthor = findViewById(R.id.detailAuthor);
        detailCate = findViewById(R.id.detailCate);
        detailImage =findViewById(R.id.detailImage);
        deleteButton = findViewById(R.id.btnDelete);
        editButton = findViewById(R.id.btnEdit);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            detailTitle.setText(bundle.getString("Title"));
            detailDesc.setText(bundle.getString("Description"));
            detailAuthor.setText(bundle.getString("Author"));
            detailCate.setText(bundle.getString("Category"));
            key = bundle.getString("Key");
            imageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(detailImage);
        }



        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Quan ly sach");
                FirebaseStorage storage = FirebaseStorage.getInstance();

                StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);
                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        reference.child(key).removeValue();
                        Toast.makeText(DetailBookActivity.this,"Đã Xóa", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    }
                });
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailBookActivity.this,UpdateBookActivity.class)
                        .putExtra("Title", detailTitle.getText().toString())
                        .putExtra("Description", detailDesc.getText().toString())
                        .putExtra("Author", detailAuthor.getText().toString())
                        .putExtra("Category", detailCate.getText().toString())
                        .putExtra("Image",imageUrl)
                        .putExtra("Key",key);

                startActivity(intent);
            }
        });


    }
}