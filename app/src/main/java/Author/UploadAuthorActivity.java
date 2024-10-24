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

import com.example.thuvienjack.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;

public class UploadAuthorActivity extends AppCompatActivity {

    ImageView uploadImageAuthor, backArrowUploadAuthor;
    Button saveButtonAuthor;
    EditText uploadTitleAuthor, uploadDescAuthor, uploadDateAuthor;
    String imageURL;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_upload_author);

        uploadImageAuthor = findViewById(R.id.uploadImageAuthor);
        uploadTitleAuthor = findViewById(R.id.uploadTitleAuthor);
        uploadDescAuthor = findViewById(R.id.uploadDescAuthor);
        uploadDateAuthor= findViewById(R.id.uploadDateAuthor);
        saveButtonAuthor = findViewById(R.id.btnSaveAuthor);
        backArrowUploadAuthor = findViewById(R.id.backArrowUploadAuthor);


        backArrowUploadAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UploadAuthorActivity.this, HomeAuthorActivity.class);
                startActivity(intent);
            }
        });

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        if(o.getResultCode() == Activity.RESULT_OK){
                            Intent data = o.getData();
                            uri = data.getData();
                            uploadImageAuthor.setImageURI(uri);
                        }else {
                            Toast.makeText(UploadAuthorActivity.this,"Chưa chọn được ảnh", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        uploadImageAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        saveButtonAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }
    public void saveData(){
        if (uri == null) {
            Toast.makeText(this, "Chưa chọn ảnh!", Toast.LENGTH_SHORT).show();
            return; // Dừng lại nếu uri là null
        }

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Android Images")
                .child(uri.getLastPathSegment());

        AlertDialog.Builder builder = new AlertDialog.Builder(UploadAuthorActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask  = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imageURL = urlImage.toString();
                uploadData();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }

    public  void uploadData(){
        String title = uploadTitleAuthor.getText().toString();
        String desc = uploadDescAuthor.getText().toString();
        String date = uploadDateAuthor.getText().toString();
        DataAuthorClass dataAuthorClass = new DataAuthorClass(title,desc,date, imageURL);

        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        FirebaseDatabase.getInstance().getReference("Quan ly tac gia").child(currentDate)
                .setValue(dataAuthorClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(UploadAuthorActivity.this,"saved",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UploadAuthorActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}