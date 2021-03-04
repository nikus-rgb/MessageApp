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

import com.example.qrcodebysensors.Activities.Messages.MessageToContact_Activity;
import com.example.qrcodebysensors.Models.ContactModel;
import com.example.qrcodebysensors.R;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> /*implements Filterable*/ {

    private Context mContext;
    private LayoutInflater inflater;
    private List<ContactModel> mListContacts;

    public ContactsAdapter(Context context, List<ContactModel> listContacts) {
        this.mContext = context;
        this.mListContacts = listContacts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_contact, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.item_contact.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String name = mListContacts.get(viewHolder.getAdapterPosition()).getName();
                String number = mListContacts.get(viewHolder.getAdapterPosition()).getNumber();

                /** Good approach*/
                Intent myIntent = new Intent(view.getContext(), MessageToContact_Activity.class);
                myIntent.putExtra("name", name);
                myIntent.putExtra("number", number);
                mContext.startActivities(new Intent[]{myIntent});
                /****************/
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextView c_name, c_number;

        c_name = holder.cV_name;
        c_number = holder.cV_number;

        c_name.setText(mListContacts.get(position).getName());
        c_number.setText(mListContacts.get(position).getNumber());
    }

    @Override
    public int getItemCount() {
        return mListContacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cV_name, cV_number;
        private LinearLayout item_contact;

        public ViewHolder(View itemView) {
            super(itemView);

            cV_name = itemView.findViewById(R.id.name_contact);
            cV_number = itemView.findViewById(R.id.phone_contact);
            item_contact = (LinearLayout) itemView.findViewById(R.id.contact_item_id);
        }
    }
}
