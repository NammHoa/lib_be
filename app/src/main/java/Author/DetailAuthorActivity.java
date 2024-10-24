package Author;

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

import com.bumptech.glide.Glide;
import com.example.thuvienjack.R;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DetailAuthorActivity extends AppCompatActivity {

    TextView detailTitleAuthor, detailDateAuthor, detailDesAuthor;
    ImageView detailImageAuthor, backDetailAuthor;
    FloatingActionButton deleteButtonAuthor, editButtonAuthor; // Sửa lại nếu là FloatingActionButton
    String key = "";
    String imageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_author);


        detailImageAuthor = findViewById(R.id.detailImageAuthor);
        detailTitleAuthor = findViewById(R.id.detailTitleAuthor);
        detailDateAuthor = findViewById(R.id.detailDateAuthor);
        detailDesAuthor = findViewById(R.id.detailDescAuthor);
        deleteButtonAuthor = findViewById(R.id.btnDeleteAuthor);
        editButtonAuthor = findViewById(R.id.btnEditAuthor);
        backDetailAuthor = findViewById(R.id.backDetailAuthor);
        backDetailAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailAuthorActivity.this, HomeAuthorActivity.class);
                startActivity(intent);
            }
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            detailTitleAuthor.setText(bundle.getString("Title"));
            detailDesAuthor.setText(bundle.getString("Description"));
            detailDateAuthor.setText(bundle.getString("Date"));
            key = bundle.getString("Key");
            imageUrl = bundle.getString("Image");

            if (imageUrl != null && !imageUrl.isEmpty()) {
                Glide.with(this).load(imageUrl).into(detailImageAuthor);
            } else {
                Toast.makeText(this, "Hình ảnh bị thiếu", Toast.LENGTH_SHORT).show();
            }
        }



        deleteButtonAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!key.isEmpty() && !imageUrl.isEmpty()) {
                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Quan ly tac gia");

                    try {
                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);

                        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                reference.child(key).removeValue();
                                Toast.makeText(DetailAuthorActivity.this, "Đã xóa", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), HomeAuthorActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(e -> {
                            Toast.makeText(DetailAuthorActivity.this, "Không thể xóa: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                    } catch (IllegalArgumentException e) {
                        Toast.makeText(DetailAuthorActivity.this, "Hình ảnh không hợp lệ: " + imageUrl, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DetailAuthorActivity.this, "Hình ảnh hoặc khóa không hợp lệ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        editButtonAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailAuthorActivity.this, UpdateAuthorActivity.class)
                        .putExtra("Title", detailTitleAuthor.getText().toString())
                        .putExtra("Description", detailDesAuthor.getText().toString())
                        .putExtra("Date", detailDateAuthor.getText().toString())
                        .putExtra("Image", imageUrl)
                        .putExtra("Key", key);
                startActivity(intent);
            }
        });
    }
}