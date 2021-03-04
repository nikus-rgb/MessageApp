package com.example.qrcodebysensors.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qrcodebysensors.Activities.Messages.MessageToUser_Activity;
import com.example.qrcodebysensors.Models.UserModel;
import com.example.qrcodebysensors.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<UserModel> mUsersList;
    private Context mContext;
    private LayoutInflater inflater;

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUName, tvUNumber;
        private LinearLayout item_user;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUName = itemView.findViewById(R.id.name_user);
            tvUNumber = itemView.findViewById(R.id.phone_user);
            item_user = itemView.findViewById(R.id.user_item_id);
        }
    }

    public UserAdapter(List<UserModel> usersList, Context context) {
        this.mUsersList = usersList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_user, parent, false);
        final UserViewHolder viewHolder = new UserViewHolder(view);

        viewHolder.item_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mUsersList.get(viewHolder.getAdapterPosition()).getuName();
                String number = mUsersList.get(viewHolder.getAdapterPosition()).getuNumber();

                /** Good approach*/
                Intent myIntent = new Intent(view.getContext(), MessageToUser_Activity.class);
                myIntent.putExtra("name", name);
                myIntent.putExtra("number", number);
                mContext.startActivities(new Intent[]{myIntent});
                /****************/
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserModel currentItem = mUsersList.get(position);
        holder.tvUName.setText(currentItem.getuName());
        holder.tvUNumber.setText(currentItem.getuNumber());
    }

    @Override
    public int getItemCount() {
        return mUsersList.size();
    }
}