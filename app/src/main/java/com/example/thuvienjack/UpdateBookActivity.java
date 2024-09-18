package com.example.thuvienjack;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UpdateBookActivity extends AppCompatActivity {
    ImageView updateImage;
    Button updateButton;
    EditText updateDesc, updateTitle, updateAuthor, updateCate;
    String title, desc, author, cate;
    String imageUrl;
    String key, oldImageURL;
    Uri uri;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_book);

        updateImage = findViewById(R.id.updateImage);
        updateTitle = findViewById(R.id.updateTopic);
        updateDesc = findViewById(R.id.updateDesc);
        updateAuthor = findViewById(R.id.updateAuthor);
        updateCate = findViewById(R.id.updateCate);
        updateButton = findViewById(R.id.btnUpdate);
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            uri = data.getData();
                            updateImage.setImageURI(uri);
                        } else {
                            Toast.makeText(UpdateBookActivity.this, "Không chọn được ảnh", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Glide.with(UpdateBookActivity.this).load(bundle.getString("Image")).into(updateImage);
            updateTitle.setText(bundle.getString("Title"));
            updateDesc.setText(bundle.getString("Description"));
            updateAuthor.setText(bundle.getString("Author"));
            updateCate.setText(bundle.getString("Category"));
            key = bundle.getString("Key");
            oldImageURL = bundle.getString("Image");
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Quan ly sach").child(key);
        updateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uri != null) {
                    saveData();
                } else {
                    updateData(oldImageURL);
                    Intent intent = new Intent(UpdateBookActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void saveData() {
        storageReference = FirebaseStorage.getInstance().getReference().child("Android Images").child(uri.getLastPathSegment());
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateBookActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete()) ;
                Uri urlImage = uriTask.getResult();
                imageUrl = urlImage.toString();
                updateData(imageUrl);
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }

    public void updateData(String imageUrl) {
        title = updateTitle.getText().toString().trim();
        desc = updateDesc.getText().toString().trim();
        author = updateAuthor.getText().toString();
        cate = updateCate.getText().toString();
        DataClass dataClass = new DataClass(title, desc, author, cate, imageUrl);

        databaseReference.setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (uri != null) {
                        StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(oldImageURL);
                        reference.delete();
                    }
                    Toast.makeText(UpdateBookActivity.this, "Đã cập nhật", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateBookActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    }

