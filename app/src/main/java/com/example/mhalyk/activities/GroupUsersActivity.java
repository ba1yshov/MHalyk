package com.example.mhalyk.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mhalyk.adapters.UsersAdapter;
import com.example.mhalyk.databinding.ActivityGroupUsersBinding;
import com.example.mhalyk.listeners.UserListener;
import com.example.mhalyk.models.User;
import com.example.mhalyk.utilities.Constants;
import com.example.mhalyk.utilities.PreferenceManager;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class GroupUsersActivity extends BaseActivity implements UserListener {

    private ActivityGroupUsersBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGroupUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
        setListeners();
        getGroupUsers();
    }

    private void setListeners() {
        binding.imageBack.setOnClickListener(v -> onBackPressed());
    }

    private void getGroupUsers() {
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .get()
                .addOnCompleteListener(task -> {
                    loading(false);
                    String currentUserId = preferenceManager.getString(Constants.KEY_USER_ID);
                    if (task.isSuccessful() && task.getResult() != null) {
                        List<User> users = new ArrayList<>();
                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                            if (currentUserId.equals(queryDocumentSnapshot.getId())) {
                                continue;
                            }
                            //***********************************************************************
                            User user = new User();
                            user.image = queryDocumentSnapshot.getString(Constants.KEY_IMAGE);
                            user.fio = queryDocumentSnapshot.getString(Constants.KEY_FIO);
                            user.position = queryDocumentSnapshot.getString(Constants.KEY_POSITION);
                            user.department = queryDocumentSnapshot.getString(Constants.KEY_DEPARTMENT);
                            user.email = queryDocumentSnapshot.getString(Constants.KEY_EMAIL);
                            user.password_comp = queryDocumentSnapshot.getString(Constants.KEY_PASSWORD);
                            user.phone = queryDocumentSnapshot.getString(Constants.KEY_PHONE);
                            user.branch = queryDocumentSnapshot.getString(Constants.KEY_BRANCH);
                            //***********************************************************************
                            user.token = queryDocumentSnapshot.getString(Constants.KEY_FCM_TOKEN);
                            user.id = queryDocumentSnapshot.getId();
                            users.add(user);
                        }
                        if (users.size() > 0) {
                            UsersAdapter usersAdapter = new UsersAdapter(users, this);
                            binding.usersRecyclerView.setAdapter(usersAdapter);
                            binding.usersRecyclerView.setVisibility(View.VISIBLE);
                        } else {
                            showErrorMessage();
                        }
                    } else {
                        showErrorMessage();
                    }
                });
    }

    private void showErrorMessage() {
        binding.textErrorMessage.setText(String.format("%s", "Пользователи недоступны"));
        binding.textErrorMessage.setVisibility(View.VISIBLE);
    }

    private void loading(Boolean isLoading) {
        if (isLoading) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onUserClicked(User user) {
        Intent intent = new Intent(getApplicationContext(), GroupChatActivity.class);
        intent.putExtra(Constants.KEY_USER, user);
        startActivity(intent);
        finish();
    }
}
















