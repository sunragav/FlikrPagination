package com.example.sundararaghavan.flickrpagingdemo.view.recview;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.data.model.FlikrModel;
import com.example.data.model.ImageSize;
import com.example.sundararaghavan.flickrpagingdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sundararaghavan on 7/24/2018.
 */

public class FlikrListAdapter extends RecyclerView.Adapter<FlikrListAdapter.FlikrViewHolder> {

    private final Application application;
    private List<FlikrModel> mItems = new ArrayList<>();
    private static final String IMAGE_URL = "https://farm%s.staticflickr.com/%s/%s_%s_%s.jpg";
    private Drawable drawable;
    private RequestOptions options;

    public FlikrListAdapter(Application application) {
        this.application = application;
        drawable = ContextCompat.getDrawable(application.getApplicationContext(), R.drawable.ic_image_24dp);
        options = new RequestOptions();
        options.centerCrop();
        options.placeholder(drawable);
    }

    @Override
    public void onBindViewHolder(@NonNull FlikrViewHolder holder, int position) {
        final FlikrModel model = mItems.get(position);


        Glide.with(application.getApplicationContext()).load(String.format(IMAGE_URL, model.farm,
                model.server, model.id, model.secret, ImageSize.MEDIUM.getValue())).
                apply(options).into(holder.ivIcon);
    }

    @Override
    @NonNull
    public FlikrViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new FlikrViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false));

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setItems(List<FlikrModel> items) {
       // mItems.clear();
        if (items != null) {
            mItems.addAll(items);
        }else{
            mItems.clear();
        }
        notifyDataSetChanged();
    }


    class FlikrViewHolder extends RecyclerView.ViewHolder {

        ImageView ivIcon;

         FlikrViewHolder(View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.image);
        }
    }
}
