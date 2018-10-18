package com.example.user.bitm_project.Moment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.bitm_project.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GalleryAdepter extends ArrayAdapter<MomentGallery> {

    ArrayList<MomentGallery>momentGalleryList=new ArrayList<>();
    Context mContext;


    public GalleryAdepter(@NonNull Context context, @NonNull ArrayList<MomentGallery>momentGalleryList) {
        super(context, R.layout.moment_gallery_show_custom_layout , momentGalleryList);
        this.momentGalleryList = momentGalleryList;
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater= LayoutInflater.from(mContext);
        convertView = inflater.inflate(R.layout.moment_gallery_show_custom_layout,parent,false);


        TextView dateTextView = convertView.findViewById(R.id.momentShowDate_id);
        TextView momentDetailsTextView = convertView.findViewById(R.id.momentDetailsShow_id);
        ImageView momentImageView = convertView.findViewById(R.id.momentImageViewShow_id);

        MomentGallery gallery = momentGalleryList.get(position);


       dateTextView.setText(gallery.getDateTime());
       momentDetailsTextView.setText(gallery.momentDetails);

       Picasso.get().load(gallery.getImageView()).placeholder(R.drawable.ic_launcher_background).into(momentImageView);


        return convertView;
    }



    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}
