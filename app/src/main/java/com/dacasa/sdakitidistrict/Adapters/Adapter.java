package com.dacasa.sdakitidistrict.Adapters;

import android.content.Context;
import android.content.Intent;
import android.telecom.Call;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dacasa.sdakitidistrict.Nyimbo_Details;
import com.dacasa.sdakitidistrict.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>implements Filterable {

    private LayoutInflater inflater;
    private String[] sTitles;
    private String[] sContent;
    private List<Nyimbo_Details> mData;
    List<Nyimbo_Details> mDataFiltered;

    Context mcontext;
    boolean isDark=false;


    public Adapter(LayoutInflater inflater, String[] sTitles, String[] sContent, List<Nyimbo_Details> mData, Context mcontext, boolean isDark) {
        this.inflater = inflater;
        this.sTitles = sTitles;
        this.sContent = sContent;
        this.mcontext = mcontext;
        this.isDark = isDark;
        this.mData = mData;
        this.mDataFiltered = mData;

    }

    public Adapter(Context context, String[] titles, String[] contents){
        this.inflater=LayoutInflater.from(context);
        this.sTitles=titles;
        this.sContent=contents;
        this.mcontext=context;
        this.mData = mData;
        this.mDataFiltered = mData;




    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_layout_forrecyclerview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String title = sTitles[position];
        String content = sContent[position];
        holder.songTitle.setText(title);
        holder.songContent.setText(content);

        //animation for song title
        holder.songTitle.setAnimation(AnimationUtils.loadAnimation(mcontext,R.anim.fade_transition_animation));
        //animation for linear layout
        holder.LLcontainer.setAnimation(AnimationUtils.loadAnimation(mcontext,R.anim.fade_transition_animation));
        //anim for cardview
        holder.CVcontainer.setAnimation(AnimationUtils.loadAnimation(mcontext,R.anim.fade_scale_animation));



    }

    @Override
    public int getItemCount() {
        return sTitles.length;
    }

    @Override
    public Filter getFilter() {


        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String Key = charSequence.toString();
                if (Key.isEmpty()) {

                    mDataFiltered = mData;


                }
                else {
                    List<Nyimbo_Details> IstFiltered = new ArrayList<>();
                    for (Nyimbo_Details row : mData) {

                       if (row.getTitle().toString().toLowerCase().contains(Key.toLowerCase())){
                           IstFiltered.add(row);
                       }
                    }
                    mDataFiltered = IstFiltered;

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mDataFiltered;
                return filterResults;



            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                mDataFiltered =( List<Nyimbo_Details>) filterResults.values;
                notifyDataSetChanged();
            }
        };


    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView songTitle,songContent;
        LinearLayout LLcontainer;
        CardView CVcontainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //implement on click
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), Nyimbo_Details.class);
                    //send song title and contents through recyclerview to detail activity
                    i.putExtra("title of song",sTitles[getAdapterPosition()]);
                    i.putExtra("content of song",sContent[getAdapterPosition()]);
                    v.getContext().startActivity(i);
                }
            });


            songTitle=itemView.findViewById(R.id.tvsongTitle);
            songContent=itemView.findViewById(R.id.tvSongContent);
            LLcontainer=itemView.findViewById(R.id.LLcontainer);
            CVcontainer=itemView.findViewById(R.id.CVcontainer);

            if (isDark){
                setDarkTheme();
            }
        }
        private void setDarkTheme(){

            CVcontainer.setBackgroundResource(R.drawable.circle_bg_dark);

        }


    }

}

