    package com.example.mhalyk.adapters;

    import android.graphics.Bitmap;
    import android.graphics.BitmapFactory;
    import android.util.Base64;
    import android.view.LayoutInflater;
    import android.view.ViewGroup;

    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;

    import com.example.mhalyk.databinding.ActivityUsersProfileBinding;
    import com.example.mhalyk.models.User;

    import java.util.List;

    public class UserProfileAdapter extends RecyclerView.Adapter<UserProfileAdapter.UserViewHolder> {

        private final List<User> users;


        public UserProfileAdapter(List<User> user) {
            this.users = user;

        }

        @NonNull
        @Override
        public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ActivityUsersProfileBinding activityUsersProfileBinding = ActivityUsersProfileBinding.inflate(
                    LayoutInflater.from(parent.getContext()),
                    parent,
                    false
            );
            return new UserViewHolder(activityUsersProfileBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
            holder.setUserData(users.get(position));
        }

        @Override
        public int getItemCount() {
            return users.size();
        }

        class UserViewHolder extends RecyclerView.ViewHolder {

            ActivityUsersProfileBinding binding;

            UserViewHolder(ActivityUsersProfileBinding activityUsersProfileBinding) {
                super(activityUsersProfileBinding.getRoot());
                binding = activityUsersProfileBinding;
            }

            void setUserData(User user) {
                binding.textUserFio.setText(user.fio);
                binding.textUserPosition.setText(user.position);
                binding.textUserDepartment.setText(user.department);
                binding.textUserEmail.setText(user.email);
                binding.textUserPhone.setText(user.phone);
                binding.textUserBranch.setText(user.branch);

                binding.imageUserProfile.setImageBitmap(getUserImage(user.image));
            }

        }
        private Bitmap getUserImage(String encodedImage) {
            byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }

    }
