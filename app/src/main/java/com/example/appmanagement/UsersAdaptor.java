package com.example.appmanagement;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UsersAdaptor extends RecyclerView.Adapter<UsersAdaptor.AppHolder> {
    Dbhelper db;
    private SharedPreferences sharedPreferences;




    public UsersAdaptor(List<UserItems> userItems, Context context) {
        this.userItems = userItems;
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);


    }

    private List<UserItems> userItems;
    private Context context;
    AppListManage methods;

    public UsersAdaptor(Context context) {

    }


    @NonNull
    @Override
    public AppHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_users_list_design,parent,false);

        return new AppHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AppHolder holder, @SuppressLint("RecyclerView") int position) {

        UserItems userItems1 = userItems.get(position);
        holder.username.setText(userItems1.getUsername());
        holder.email.setText(userItems1.getEmail());
        holder.listholder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId= userItems1.getUserId();
                Toast.makeText(context, userId, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,PermisionOverView.class);
                intent.putExtra("username",userItems1.getUsername());
                intent.putExtra("userId",userId);
                context.startActivity(intent);


            }
        });



    }


    @Override
    public int getItemCount() {
        return userItems.size();
    }

    public class AppHolder extends RecyclerView.ViewHolder
    {
        public TextView email;
        public TextView username;
        public LinearLayout listholder;
        public String  UserId;

        public AppHolder(@NonNull View itemView) {
            super(itemView);
            email = (TextView) itemView.findViewById(R.id.dbemail);
            username =(TextView) itemView.findViewById(R.id.dbusername);
            listholder = (LinearLayout) itemView.findViewById(R.id.userListLayout);
        }
    }


}
