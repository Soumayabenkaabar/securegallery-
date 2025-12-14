package com.example.securegallery;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.Holder> {

    private final Context context;
    private final ArrayList<File> files;
    private final LayoutInflater inflater;

    public GalleryAdapter(Context context, ArrayList<File> files) {
        this.context = context;
        this.files = files;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_image, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        File file = files.get(position);
        String name = file.getName().toLowerCase();

        if (name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png")) {

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4; // miniatures (RAM safe)
            Bitmap bmp = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
            holder.iv.setImageBitmap(bmp);

        } else if (name.endsWith(".mp3")) {

            holder.iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            holder.iv.setImageResource(R.drawable.ic_audio);

        } else if (name.endsWith(".mp4")) {

            holder.iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            holder.iv.setImageResource(R.drawable.ic_video);

        } else if (name.endsWith(".pdf")) {

            holder.iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            holder.iv.setImageResource(R.drawable.ic_pdf);

        } else {

            holder.iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            holder.iv.setImageResource(R.drawable.ic_file);
        }

        holder.itemView.setOnClickListener(v -> openFile(file));
    }

    private void openFile(File file) {

        String name = file.getName().toLowerCase();
        Intent i;

        if (name.endsWith(".jpg") || name.endsWith(".png")) {
            i = new Intent(context, ImageViewerActivity.class);

        } else if (name.endsWith(".mp3")) {
            i = new Intent(context, AudioPlayerActivity.class);

        } else if (name.endsWith(".mp4")) {
            i = new Intent(context, VideoPlayerActivity.class);

        } else if (name.endsWith(".pdf")) {
            i = new Intent(context, PdfViewerActivity.class);

        } else return;

        i.putExtra("path", file.getAbsolutePath());
        context.startActivity(i);
    }

    @Override
    public int getItemCount() {
        return files.size();
    }


    static class Holder extends RecyclerView.ViewHolder {
        ImageView iv;

        Holder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.ivItem);
        }
    }
}

