package com.example.appmanagement;

import android.annotation.SuppressLint;
import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AppAdaptor extends RecyclerView.Adapter<AppAdaptor.AppHolder> {
    public AppAdaptor(List<AppItems> appItems, Context context) {
        this.appItems = appItems;
        this.context = context;
    }

    private List<AppItems> appItems;
private Context context;

    @NonNull
    @Override
    public AppHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.design_apps,parent,false);

        return new AppHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AppHolder holder, @SuppressLint("RecyclerView") int position) {

    AppItems AppItems = appItems.get(position);
    if(AppItems.getAppName().length()>5)
    {
        holder.AppName.setText(AppItems.getAppName().substring(0,5));
    }else
    {
        holder.AppName.setText(AppItems.getAppName());
    }
    holder.AppIcon.setImageDrawable(AppItems.getAppIcon());
    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String packageName = appItems.get(position).getPackageName();
            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            if (launchIntent != null) {
                context.startActivity(launchIntent);
            }


        }
    });


    }

    @Override
    public int getItemCount() {
        return appItems.size();
    }

    public class AppHolder extends RecyclerView.ViewHolder
    {
        public TextView AppName;
        public ImageView AppIcon;


        public AppHolder(@NonNull View itemView) {
            super(itemView);
            AppName=(TextView) itemView.findViewById(R.id.appName);
            AppIcon= (ImageView) itemView.findViewById(R.id.appIcon);

        }
    }


}
