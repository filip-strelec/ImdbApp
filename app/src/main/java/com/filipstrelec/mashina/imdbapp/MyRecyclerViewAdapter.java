package com.filipstrelec.mashina.imdbapp;

import android.content.Context;
import android.graphics.Movie;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {


    private Context context;
    private List<String> titleArrayList;
    private List<String> imageArrayList;
    private LinearLayout descriptionLayout;
    private boolean[] booleans;
    private boolean boolean_test;




    public MyRecyclerViewAdapter(Context context, List<String> titleArrayList, List<String> imageArrayList) {
        this.titleArrayList = titleArrayList;
        this.imageArrayList = imageArrayList;
        this.context = context;



    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        boolean_test=true;
        booleans=new boolean[imageArrayList.size()];
        for ( int i=0;i<imageArrayList.size();i++){

            booleans[i]=true;


        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycleview_item, parent, false);


        return new ViewHolder(view);



    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


        Glide.with(context).asBitmap().load(imageArrayList.get(position)).into(holder.image);
        holder.textTitle.setText(titleArrayList.get(position));

//        descriptionLayout.setVisibility(booleans[position] ? View.VISIBLE : View.GONE);
        if (boolean_test){

            descriptionLayout.setVisibility(View.VISIBLE);

        }
        else{
            descriptionLayout.setVisibility(View.GONE);

        }


//        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //za onclick ako treba nest
//
//                if (booleans[position]==true){
//
//                    booleans[position]=false;
//
//                }
//                else{
//                    booleans[position]=true;
//
//                }
//
//
////                expand.setExpanded(!expanded);
//                Toast.makeText(context, "Kliknuo si "+(position+1)+". objekt u recyclerView-u", Toast.LENGTH_SHORT).show();
//                Log.i("fakingBulean"+position, String.valueOf(booleans[position]));
//                notifyItemChanged(position);
//
//            }
//        });



    }

    @Override
    public int getItemCount() {

        return (imageArrayList.size());
    }


    public class ViewHolder extends RecyclerView.ViewHolder  implements  View.OnClickListener{


        CircularImageView image;
        TextView textTitle;
        RelativeLayout relativeLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            image = itemView.findViewById(R.id.circleViewList);
            textTitle = itemView.findViewById(R.id.textList);
            relativeLayout = itemView.findViewById(R.id.relativeLayoutList);
            descriptionLayout=itemView.findViewById(R.id.description_layout);
//            if (isExpanded==true) {
//                descriptionLayout.setVisibility(View.GONE);
//            }

        }


        @Override
        public void onClick(View v) {
//            Toast.makeText(v.getContext(), "position = " + getLayoutPosition(), Toast.LENGTH_SHORT).show();

            Log.i("fakingBulecan"+getLayoutPosition(), String.valueOf(booleans[getLayoutPosition()]));

            if (boolean_test==true){

                  boolean_test=false;

                }
                else{
                    boolean_test=true;

                }

            notifyItemChanged(getLayoutPosition());

//            if(getLayoutPosition()==0){
//                Toast.makeText(v.getContext(), "positwfqwfqion = " + getLayoutPosition(), Toast.LENGTH_SHORT).show();
//
//            }

        }
    }





}
