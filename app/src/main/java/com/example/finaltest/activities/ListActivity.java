package com.example.finaltest.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.finaltest.R;
import com.example.finaltest.adapters.GenericAdapter;
import com.example.finaltest.adapters.ItemsListView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    ListView listView;
    List<ItemsListView> list = new ArrayList<ItemsListView>();
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listView = (ListView) findViewById(R.id.listView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        showProgress();
        FirebaseFirestore.getInstance().collection("contacts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        hideProgress();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ItemsListView itemsListView = new ItemsListView();
                                itemsListView.setTitulo1(document.getId());
                                itemsListView.setTitulo2(document.getData().get("first_name").toString());
                                itemsListView.setTitulo3(document.getData().get("last_name").toString());
                                itemsListView.setTitulo4(document.get("number").toString());
                                list.add(itemsListView);
                            }
                            setAdapter();
                        }
                    }
                });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                
            }
        });
    }
    private void setAdapter(){
        GenericAdapter adapter = new GenericAdapter(this, R.id.listView, list);
        listView.setAdapter(adapter);
    }
    private void showProgress(){
        progressBar.setVisibility(View.VISIBLE);

    }
    private void hideProgress(){
        progressBar.setVisibility(View.INVISIBLE);
    }
}
