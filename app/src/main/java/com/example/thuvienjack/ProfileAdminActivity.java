package com.example.thuvienjack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import Author.HomeAuthorActivity;

public class ProfileAdminActivity extends AppCompatActivity {

    ImageView IVBookAdmin, IVAuthor;
    Button btnLogoutAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_admin);

        IVBookAdmin = findViewById(R.id.IVBookAdmin);
        IVAuthor = findViewById(R.id.IVAuthor);
        btnLogoutAdmin = findViewById(R.id.btnLogoutAdmin);
        IVBookAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileAdminActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        IVAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileAdminActivity.this, HomeAuthorActivity.class);
                startActivity(intent);
            }
        });


        btnLogoutAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileAdminActivity.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

    }
}