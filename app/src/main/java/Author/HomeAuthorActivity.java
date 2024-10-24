package Author;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thuvienjack.ProfileAdminActivity;
import com.example.thuvienjack.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeAuthorActivity extends AppCompatActivity {

    ImageView btnBackHomeAuthor;
    FloatingActionButton fabAuthor;
    RecyclerView recyclerViewAuthor;
    List<DataAuthorClass> dataListAuthor;
    DatabaseReference databaseReferenceAuthor;
    ValueEventListener eventListenerAuthor;
    SearchView searchViewAuthor;

    MyAdapterAuthor myAdapterAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_author);

        btnBackHomeAuthor = findViewById(R.id.btnBackHomeAdminAuthor);
        btnBackHomeAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeAuthorActivity.this, ProfileAdminActivity.class);
                startActivity(intent);
            }
        });
        fabAuthor = findViewById(R.id.fabAuthor);
        recyclerViewAuthor = findViewById(R.id.recyclerViewAuthor);
        searchViewAuthor = findViewById(R.id.searchAuthor);
        searchViewAuthor.clearFocus();


        GridLayoutManager gridLayoutManager = new GridLayoutManager(HomeAuthorActivity.this, 2);
        recyclerViewAuthor.setLayoutManager(gridLayoutManager);

        AlertDialog.Builder builder = new AlertDialog.Builder(HomeAuthorActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        dataListAuthor = new ArrayList<>();

        myAdapterAuthor = new MyAdapterAuthor(HomeAuthorActivity.this, dataListAuthor);
        recyclerViewAuthor.setAdapter(myAdapterAuthor);

        databaseReferenceAuthor = FirebaseDatabase.getInstance().getReference("Quan ly tac gia");
        dialog.show();

        eventListenerAuthor = databaseReferenceAuthor.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataListAuthor.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    DataAuthorClass dataAuthorClass = itemSnapshot.getValue(DataAuthorClass.class);
                    dataAuthorClass.setKey(itemSnapshot.getKey());
                    dataListAuthor.add(dataAuthorClass);
                }
                myAdapterAuthor.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                dialog.dismiss();
            }
        });

        searchViewAuthor.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchListFood(newText);
                return true;
            }
        });
        fabAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeAuthorActivity.this, UploadAuthorActivity.class);
                startActivity(intent);
            }
        });
    }
    public void searchListFood(String text){
        ArrayList<DataAuthorClass> searchAuthor = new ArrayList<>();
        for (DataAuthorClass dataAuthorClass:dataListAuthor){
            if(dataAuthorClass.getDataTitleAuthor().toLowerCase().contains(text.toLowerCase())){
                searchAuthor.add(dataAuthorClass);
            }
        }
        myAdapterAuthor.searchDataList(searchAuthor);

    }
}