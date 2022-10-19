package com.example.mhalyk.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.mhalyk.R;
import com.example.mhalyk.databinding.ActivityUsersProfileBinding;
import com.example.mhalyk.utilities.Constants;
import com.example.mhalyk.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UsersProfileActivity extends BaseActivity {

    private ActivityUsersProfileBinding binding;
    private PreferenceManager preferenceManager;
    //**********
    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    TextView tv5;
    TextView tv6;
    //**********
    //++++++++++++++++

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsersProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
        //*****************************************
        tv1 = (TextView) findViewById(R.id.textUserFio);
        tv2 = (TextView) findViewById(R.id.textUserPosition);
        tv3 = (TextView) findViewById(R.id.textUserDepartment);
        tv4 = (TextView) findViewById(R.id.textUserEmail);
        tv5 = (TextView) findViewById(R.id.textUserPhone);
        tv6 = (TextView) findViewById(R.id.textUserBranch);
        //*****************************************
        FetchUserFromFirestore();
        setListeners();
    }

    private void setListeners() {
        binding.imageBack.setOnClickListener(v -> onBackPressed());
    }


    private void FetchUserFromFirestore() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference = database.collection("users").document("aBiowN68z27qhqYMawPh");//мастеринер озгортту
        documentReference.get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        tv1.setText(documentSnapshot.getString("fio"));
                        tv2.setText(documentSnapshot.getString("position"));
                        tv3.setText(documentSnapshot.getString("department"));
                        tv4.setText(documentSnapshot.getString("email"));
                        tv5.setText(documentSnapshot.getString("phone"));
                        tv6.setText(documentSnapshot.getString("branch"));
                        byte[] bytes = Base64.decode(preferenceManager.getString(Constants.KEY_IMAGE), Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        binding.imageUserProfile.setImageBitmap(bitmap);
                    } else

                        Toast.makeText(getApplicationContext(), "Row not found", Toast.LENGTH_LONG).show();
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Failed to fetch data", Toast.LENGTH_LONG).show();
                    }
                });
    }

}

//    private void LoadUserDetailsProfile() {
//        byte[] bytes = Base64.decode(preferenceManager.getString(Constants.KEY_IMAGE), Base64.DEFAULT);
//        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//        binding.imageUserProfile.setImageBitmap(bitmap);
//    }
//    private void getUserById() {
//        database = FirebaseFirestore.getInstance();
//        DocumentReference docRef = database.collection("users").document("DElUeOARNhb94n48Nk3f");
//        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                UserListener userListener = documentSnapshot.toObject(UserListener.class);
//            }
//        });
//    }

//    private void getUserById() {
//        DocumentReference docRef = FirebaseFirestore.getInstance().collection("users").document("0ovQaH0BaEv53SmhwTin");
//        docRef.get().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                DocumentSnapshot doc = task.getResult();
//                if (doc.exists()) {
//                    Log.d("Document", doc.getData().toString());
//                } else {
//                    Log.d("Document", "No data in Firebase");
//                }
//            }
//        });
//    }

//    private void getUserById() {
//        FirebaseFirestore.getInstance().collection("users").whereEqualTo("Bella", true)
//                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot doc : task.getResult()) {
//                                Log.d("Document", doc.getId() + "=>" + doc.getData());
//                            }
//                        }
//                    }
//                });
//    }


//    private void getUsersById() {
//        FirebaseFirestore database = FirebaseFirestore.getInstance();
//        database.collection(Constants.KEY_COLLECTION_USERS).document("0ovQaH0BaEv53SmhwTin")
//                .get().addOnCompleteListener(task -> {
//                    String currentUserId = preferenceManager.getString(Constants.KEY_USER_ID);
//                    if (task.isSuccessful() && task.getResult() != null) {
//                        List<User> users = new ArrayList<>();
//                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
//                            if (currentUserId.equals(queryDocumentSnapshot.getId())) {
//                                continue;
//                            }
//                            //***********************************************************************
//                            User user = new User();
//                            //user.image = queryDocumentSnapshot.getString(Constants.KEY_IMAGE);
//                            user.fio = queryDocumentSnapshot.getString(Constants.KEY_FIO);
//                            user.position = queryDocumentSnapshot.getString(Constants.KEY_POSITION);
//                            user.department = queryDocumentSnapshot.getString(Constants.KEY_DEPARTMENT);
//                            user.email = queryDocumentSnapshot.getString(Constants.KEY_EMAIL);
//                            //user.password_comp = queryDocumentSnapshot.getString(Constants.KEY_PASSWORD);
//                            user.phone = queryDocumentSnapshot.getString(Constants.KEY_PHONE);
//                            user.branch = queryDocumentSnapshot.getString(Constants.KEY_BRANCH);
//                            //***********************************************************************
//                            user.token = queryDocumentSnapshot.getString(Constants.KEY_FCM_TOKEN);
//                            user.id = queryDocumentSnapshot.getId();
//                            users.add(user);
//                        }
//                        if (users.size() > 0) {
//                            UserProfileAdapter usersProfileAdapter = new UserProfileAdapter(users);
//                            binding.profileRecyclerView.setAdapter(usersProfileAdapter);
//                            binding.profileRecyclerView.setVisibility(View.VISIBLE);
//                        }
//                    }
//                });
//    }


//    private void getUsers() {
//        FirebaseFirestore database = FirebaseFirestore.getInstance();
//        database.collection(Constants.KEY_COLLECTION_USERS).whereEqualTo("email", "baiyshov.17@gmail.com")
//                .get()
//                .addOnCompleteListener(task -> {
//                    String currentUserId = preferenceManager.getString(Constants.KEY_USER_ID);
//                    if (task.isSuccessful() && task.getResult() != null) {
//                        List<User> users = new ArrayList<>();
//                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
//                            if (currentUserId.equals(queryDocumentSnapshot.getId())) {
//                                continue;
//                            }
//                            //***********************************************************************
//                            User user = new User();
//                            user.image = queryDocumentSnapshot.getString(Constants.KEY_IMAGE);
//                            user.fio = queryDocumentSnapshot.getString(Constants.KEY_FIO);
//                            user.position = queryDocumentSnapshot.getString(Constants.KEY_POSITION);
//                            user.department = queryDocumentSnapshot.getString(Constants.KEY_DEPARTMENT);
//                            user.email = queryDocumentSnapshot.getString(Constants.KEY_EMAIL);
//                            user.password_comp = queryDocumentSnapshot.getString(Constants.KEY_PASSWORD);
//                            user.phone = queryDocumentSnapshot.getString(Constants.KEY_PHONE);
//                            user.branch = queryDocumentSnapshot.getString(Constants.KEY_BRANCH);
//                            //***********************************************************************
//                            user.token = queryDocumentSnapshot.getString(Constants.KEY_FCM_TOKEN);
//                            user.id = queryDocumentSnapshot.getId();
//                            users.add(user);
//                        }
//                        if (users.size() > 0) {
//                            UserProfileAdapter usersProfileAdapter = new UserProfileAdapter(users);
//                            binding.profileRecyclerView.setAdapter(usersProfileAdapter);
//                            binding.profileRecyclerView.setVisibility(View.VISIBLE);
//                        }
//                    }
//                });
//    }


















