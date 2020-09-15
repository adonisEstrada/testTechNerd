package com.example.finaltest.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.finaltest.R;
import com.example.finaltest.adapters.GenericAdapter;
import com.example.finaltest.adapters.ItemsListView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ChatActivity extends AppCompatActivity {

    EditText chatText;
    Button send;
    ListView chatList;
    ProgressBar progressBar;
    List<ItemsListView> list = new ArrayList<ItemsListView>();
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chatText = (EditText) findViewById(R.id.chatText);
        send = (Button) findViewById(R.id.buttonSend);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        chatList = (ListView) findViewById(R.id.chatList);

        bundle = getIntent().getExtras();

        loadChatList();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!chatText.getText().toString().isEmpty()){
                    sendMessage();
                }
            }
        });
    }

    private void sendMessage(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("date", new Date());
        map.put("receiver_email", bundle.getString("email"));
        map.put("sender_email", FirebaseAuth.getInstance().getCurrentUser().getEmail());
        map.put("message", chatText.getText().toString());
        showProgress();
        FirebaseFirestore.getInstance().collection("chats")
                .document(UUID.randomUUID().toString()).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                hideProgress();
                loadChatList();
            }
        });
    }

    private void loadChatList(){
        showProgress();
        list.clear();
        FirebaseFirestore.getInstance().collection("chats")
                .orderBy("date").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        hideProgress();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if((document.getData().get("receiver_email").toString().equals(bundle.getString("email"))
                                        && document.getData().get("sender_email").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail()))
                                || (document.getData().get("sender_email").toString().equals(bundle.getString("email"))
                                        && document.getData().get("receiver_email").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail()))){
                                    ItemsListView itemsListView = new ItemsListView();
                                    itemsListView.setTitulo1(document.getData().get("sender_email").toString());
                                    itemsListView.setTitulo2(document.getData().get("message").toString());
                                    list.add(itemsListView);
                                }
                            }
                            setAdapter();
                        }
                    }
                });
    }

    private void setAdapter(){
            GenericAdapter adapter = new GenericAdapter(this, R.id.listView, list);
            chatList.setAdapter(adapter);
    }
    private void showProgress(){
        progressBar.setVisibility(View.VISIBLE);
        send.setEnabled(false);
        chatText.setEnabled(false);
    }
    private void hideProgress(){
        progressBar.setVisibility(View.INVISIBLE);
        send.setEnabled(true);
        chatText.setEnabled(true);
    }
}
