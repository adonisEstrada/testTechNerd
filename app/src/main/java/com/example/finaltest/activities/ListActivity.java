package com.example.finaltest.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.finaltest.R;
import com.example.finaltest.adapters.GenericAdapter;
import com.example.finaltest.adapters.ItemsListView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
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

        loadList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                initChat(list.get(i).getTitle1());
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                delete(i);
                return true;
            }
        });
    }

    private void loadList(){
        showProgress();
        list.clear();
        FirebaseFirestore.getInstance().collection("contacts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        hideProgress();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ItemsListView itemsListView = new ItemsListView();
                                itemsListView.setTitle1(document.getId());
                                itemsListView.setTitle2(document.getData().get("first_name").toString());
                                itemsListView.setTitle3(document.getData().get("last_name").toString());
                                itemsListView.setTitle4(document.get("number").toString());
                                list.add(itemsListView);
                            }
                            setAdapter();
                        }
                    }
                });
    }

    public void delete(final int index){
        new AlertDialog.Builder(this)
                    .setMessage("This contact will be delete, continue?")
                    .setTitle("Alert!")
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            FirebaseFirestore.getInstance().collection("contacts").document(list.get(index).getTitle1()).delete();
                            dialogInterface.dismiss();
                            AlertDialog alert = showAlert("Your contact has been delete successfulle.", "Contact Delete!.");
                            alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialogInterface) {
                                    loadList();
                                }
                            });
                            alert.show();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            showConfirmEditContact(index);
                            dialogInterface.dismiss();
                        }
                    }).show();
    }

    private void showConfirmEditContact(final int index){
        new AlertDialog.Builder(this).setTitle("Edit contact!").setMessage("Do you want to edit this contact?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        editContact(list.get(index).getTitle1());
                    }
                }).setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).show();
    }

    private void editContact(String email){
        Intent intent = new Intent(this, AddActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);
    }



    private AlertDialog showAlert(String message, String title){
        if(title!=null && message!=null){
            return new AlertDialog.Builder(this).setTitle(title.isEmpty()?"Error!.":title)
                    .setPositiveButton("Accept", null)
                    .setMessage(message.isEmpty()?"An authentication error has occurred.":message).create();
        }else{
            return new AlertDialog.Builder(this).setTitle("Error!.")
                    .setPositiveButton("Accept", null)
                    .setMessage("An authentication error has occurred.").create();
        }

    }

    private void initChat(String email){
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);
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
