package Author;

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

import com.example.thuvienjack.DataClass;
import com.example.thuvienjack.HomeActivity;
import com.example.thuvienjack.MyAdapterUser;
import com.example.thuvienjack.ProductBookUserActivity;
import com.example.thuvienjack.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeAuthorUserActivity extends AppCompatActivity {

    SearchView searchAuthorUser;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    RecyclerView RycAuthorUser;
    List<DataAuthorClass> dataList;
    MyAdapterAuthorUser adapterAuthorUser;
    ImageView btnBackHomeAuthorUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_author_user);

        searchAuthorUser = findViewById(R.id.searchAuthorUser);
        searchAuthorUser.clearFocus();


        btnBackHomeAuthorUser = findViewById(R.id.btnBackHomeUserAuthor);
        btnBackHomeAuthorUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeAuthorUserActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        RycAuthorUser = findViewById(R.id.recyclerViewAuthorUser);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(HomeAuthorUserActivity.this, 2);
        RycAuthorUser.setLayoutManager(gridLayoutManager);
        dataList = new ArrayList<>();
        adapterAuthorUser = new MyAdapterAuthorUser(HomeAuthorUserActivity.this, dataList);
        RycAuthorUser.setAdapter(adapterAuthorUser);
        databaseReference = FirebaseDatabase.getInstance().getReference("Quan ly tac gia");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot i : snapshot.getChildren()) {
                    DataAuthorClass dataClass = i.getValue(DataAuthorClass.class);
                    dataClass.setKey(i.getKey());
                    dataList.add(dataClass);
                }
                adapterAuthorUser.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                finish();
            }
        });
        searchAuthorUser.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        ArrayList<DataAuthorClass> searchList = new ArrayList<>();
        for (DataAuthorClass dataClass : dataList) {
            if (dataClass.getDataTitleAuthor().toLowerCase().contains(text.toLowerCase()) ||
                    dataClass.getDataDateAuthor().toLowerCase().contains(text.toLowerCase()))
            {

                searchList.add(dataClass);
            }
        }
        adapterAuthorUser.searchDataList(searchList);
    }
}
