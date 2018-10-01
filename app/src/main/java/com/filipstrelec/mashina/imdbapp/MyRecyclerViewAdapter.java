package com.filipstrelec.mashina.imdbapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Movie;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.silencedut.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {


    private MainActivity context;
    private List<String> titleArrayList;
    private List<String> imageArrayList;
    private List<String> simplePlotArrayList;
    private int positionList;



//    private int colorSwitcher;


    public MyRecyclerViewAdapter(MainActivity context, List<String> titleArrayList, List<String> imageArrayList, List<String> simplePlotArrayList) {
        this.titleArrayList = titleArrayList;
        this.imageArrayList = imageArrayList;
        this.context = context;
        this.simplePlotArrayList = simplePlotArrayList;


    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycleview_item, parent, false);


        return new ViewHolder(view);


    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        Glide.with(context).asBitmap().load(imageArrayList.get(position)).into(holder.image);
        holder.textTitle.setText(titleArrayList.get(position));

        holder.description.setText(simplePlotArrayList.get(position));


        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
//
//


            }
        });

        holder.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                positionList = position;

                setThings(titleArrayList.get(position), imageArrayList.get(position), simplePlotArrayList.get(position));
//
//                holder.button2.setAlpha( 1f);   UGAŠENO ZBOG BUGA!



            }
        });

    }

    private void setThings(String movieName, String imageUrl, String desc) {
        TinyDB tinydb = new TinyDB(context);

        if (MainActivity.switcher == 3) {

            List<String> listTitle = tinydb.getListString("title");


            listTitle.remove(positionList);
            tinydb.putListString("title", (ArrayList<String>) listTitle);




            List<String> listImage = tinydb.getListString("image");
            listImage.remove(positionList);
            tinydb.putListString("image", (ArrayList<String>) listImage);


            List<String> listDesc = tinydb.getListString("desc");
            listDesc.remove(positionList);
            tinydb.putListString("desc", (ArrayList<String>) listDesc);

            titleArrayList.remove(positionList);
            imageArrayList.remove(positionList);
            simplePlotArrayList.remove(positionList);

            notifyItemRemoved(positionList);
            notifyDataSetChanged();

        }

        else{

        List<String> listTitle = tinydb.getListString("title");

        if (listTitle.contains(movieName)) {
            Log.i("duplo", "duplic");

            Toast.makeText(context, "Wish list već sadrži taj film!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Uspješno dodano u wish list!", Toast.LENGTH_SHORT).show();
            listTitle.add(movieName);
            tinydb.putListString("title", (ArrayList<String>) listTitle);


            List<String> listImage = tinydb.getListString("image");
            listImage.add(imageUrl);
            tinydb.putListString("image", (ArrayList<String>) listImage);


            List<String> listDesc = tinydb.getListString("desc");
            listDesc.add(desc);
            tinydb.putListString("desc", (ArrayList<String>) listDesc);


        }


    }}

    @Override
    public int getItemCount() {

        return (imageArrayList.size());
    }


    public class ViewHolder extends RecyclerView.ViewHolder

    {


        CircularImageView image;
        TextView textTitle;
        ExpandableLayout relativeLayout;
        TextView description;
        Button button2;
        LinearLayout descriptionLayout;


        private ViewHolder(View itemView) {
            super(itemView);


            image = itemView.findViewById(R.id.circleViewList);
            textTitle = itemView.findViewById(R.id.textList);
            relativeLayout = itemView.findViewById(R.id.relativeLayoutList);
            descriptionLayout = itemView.findViewById(R.id.description_layout);
            description = itemView.findViewById(R.id.textDescription);
            button2 = itemView.findViewById(R.id.gumb2);

            if(MainActivity.switcher==3){

                button2.setBackgroundResource(R.drawable.ic_delete_forever_black_24dp);


            }


//            descriptionLayout.setMinimumHeight(descriptionLayout.getHeight()+260);

//            if(colorSwitcher==0){
//                relativeLayout.setBackgroundColor(Color.parseColor("#519def"));
//
//                colorSwitcher=1;
//            }
//
//            else if (colorSwitcher==1) {
//
//
//                relativeLayout.setBackgroundColor(Color.parseColor("#3072ba"));
//
//                colorSwitcher=0;
//
//            }


        }


    }


}
