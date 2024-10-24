
package Author;

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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.thuvienjack.DataClass;
import com.example.thuvienjack.DetailBookUserActivity;
import com.example.thuvienjack.ListBorrow;
import com.example.thuvienjack.ListBorrowActivity;
import com.example.thuvienjack.ProductBookUserActivity;
import com.example.thuvienjack.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DetailAuthorUser extends AppCompatActivity {

    TextView detailTitleAuthor, detailDateAuthor, detailDesAuthor;
    ImageView detailImageAuthor, backDetailAuthor;
    Button btnBorrowBook;

    private static final String TAG = "DetailAuthorUserActivity";
    DataAuthorClass authorClass;
    String key = "";
    String imageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_author_user);

        detailTitleAuthor = findViewById(R.id.detailTitleAuthor);
        detailDesAuthor = findViewById(R.id.detailDescAuthor);
        detailDateAuthor = findViewById(R.id.detailDateAuthor);
        detailImageAuthor = findViewById(R.id.detailImageAuthor);


//        backDetailUser.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(DetailBookUserActivity.this, ProductBookUserActivity.class);
//                startActivity(intent);
//
//            }
//        });
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            detailTitleAuthor.setText(bundle.getString("Title"));
            detailDesAuthor.setText(bundle.getString("Description"));
            detailDateAuthor.setText(bundle.getString("Date"));
            key = bundle.getString("Key");
            imageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(detailImageAuthor);

            if (imageUrl != null && !imageUrl.isEmpty()) {
                Glide.with(this).load(imageUrl).into(detailImageAuthor);
            } else {
                Toast.makeText(this, "Hình ảnh bị thiếu", Toast.LENGTH_SHORT).show();
            }
        }


    }
}