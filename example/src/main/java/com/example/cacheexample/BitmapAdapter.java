package com.example.cacheexample;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidyuan.libcache.FastHugeStorage;
import com.androidyuan.libcache.core.ITicket;
import com.androidyuan.libcache.data.BitmapTicket;

import java.util.ArrayList;
import java.util.List;

public class BitmapAdapter extends RecyclerView.Adapter<BitmapAdapter.BTViewHolder> {

    private final List<String> list = new ArrayList<>();

    public BitmapAdapter(List<String> list) {
        this.list.addAll(list);
    }

    @NonNull
    @Override
    public BTViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BTViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bitmap_test, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BTViewHolder holder, int position) {
        position = holder.getAdapterPosition();
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void replace(String old, String newId) {
        if (old == null || newId == null)
            throw new NullPointerException("woooo.i dont no wt happens.");

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(old)) {
                list.set(i, newId);
                return;
            }
        }
    }

    class BTViewHolder extends RecyclerView.ViewHolder {

        public String uuid;
        public Bitmap bitmap;

        private final ImageView imgView;

        public BTViewHolder(@NonNull View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.img);
        }

        void bind(final String id) {
            ITicket it = FastHugeStorage.getInstance().popTicket(id);
            if (it == null) {
                return;
            }

            if (bitmap != null) {
                imgView.setImageBitmap(null);
                replace(uuid, FastHugeStorage.getInstance().put(new BitmapTicket(bitmap)));
                bitmap = null;
            }
            uuid = id;

            bitmap = (Bitmap) it.getBean();
            if (bitmap != null) {
                if (imgView.getHeight() != bitmap.getHeight()) {
                    ViewGroup.LayoutParams params = imgView.getLayoutParams();
                    params.height = bitmap.getHeight();
                    params.width = bitmap.getWidth();
                    imgView.setLayoutParams(params);
                }
                imgView.setImageBitmap(bitmap);
            }


        }

    }

}
