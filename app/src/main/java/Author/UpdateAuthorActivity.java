package Author;

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
import com.example.thuvienjack.DataClass;
import com.example.thuvienjack.MainActivity;
import com.example.thuvienjack.R;
import com.example.thuvienjack.UpdateBookActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UpdateAuthorActivity extends AppCompatActivity {
    ImageView updateImageAuthor, backArrowUpdateAuthor;
    Button updateButtonAuthor;
    EditText updateDescAuthor, updateTitleAuthor, updateDateAuthor;
    String titleAuthor, descAuthor, dateAuthor;
    String imageUrl;
    String key, oldImageURL;
    Uri uri;
    DatabaseReference databaseReferenceAuthor;
    StorageReference storageReferenceAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_author);

        updateImageAuthor = findViewById(R.id.updateImageAuthor);
        updateTitleAuthor = findViewById(R.id.updateTitleAuthor);
        updateDescAuthor = findViewById(R.id.updateDescAuthor);
        updateDateAuthor = findViewById(R.id.updateDateAuthor);
        updateButtonAuthor = findViewById(R.id.btnSaveUpdateAuthor);
        backArrowUpdateAuthor = findViewById(R.id.backArrowUpdateAuthor);

        backArrowUpdateAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateAuthorActivity.this, HomeAuthorActivity.class);
                startActivity(intent);
            }
        });

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            uri = data.getData();
                            updateImageAuthor.setImageURI(uri);
                        } else {
                            Toast.makeText(UpdateAuthorActivity.this, "Không chọn được ảnh", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Glide.with(UpdateAuthorActivity.this).load(bundle.getString("Image")).into(updateImageAuthor);
            updateTitleAuthor.setText(bundle.getString("Title"));
            updateDescAuthor.setText(bundle.getString("Description"));
            updateDateAuthor.setText(bundle.getString("Date"));
            key = bundle.getString("Key");
            oldImageURL = bundle.getString("Image");
        }

        databaseReferenceAuthor = FirebaseDatabase.getInstance().getReference("Quan ly tac gia").child(key);
        updateImageAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        updateButtonAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uri != null) {
                    saveData();
                } else {
                    updateData(oldImageURL);
                    Intent intent = new Intent(UpdateAuthorActivity.this, HomeAuthorActivity.class);
                    startActivity(intent);
                }
            }
        });
    }



    public void saveData() {
        storageReferenceAuthor = FirebaseStorage.getInstance().getReference().child("Android Images").child(uri.getLastPathSegment());
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateAuthorActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReferenceAuthor.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
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
        titleAuthor = updateTitleAuthor.getText().toString().trim();
        descAuthor = updateDescAuthor.getText().toString().trim();
        dateAuthor = updateDateAuthor.getText().toString();
        DataAuthorClass dataClass = new DataAuthorClass(titleAuthor, descAuthor, dateAuthor, imageUrl);

        databaseReferenceAuthor.setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (uri != null) {
                        StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(oldImageURL);
                        reference.delete();
                    }
                    Toast.makeText(UpdateAuthorActivity.this, "Đã cập nhật", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateAuthorActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
