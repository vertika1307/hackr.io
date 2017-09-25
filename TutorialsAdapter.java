package com.production.hackr;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Vidushi Sharma on 7/21/2017.
 */

public class TutorialsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    List<TutorialContent> tutsList= Collections.emptyList();
    int currentPos=0;


    public TutorialsAdapter(Context context, List<TutorialContent> tutsList) {

        this.context=context;
        inflater= LayoutInflater.from(context);
        this.tutsList=tutsList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.tutorial_list, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        TutorialContent current=tutsList.get(position);

        myHolder.tut_name.setText(current.tut_name);
        myHolder.submitted_by.setText(current.submitted_by);
        myHolder.upvote.setText(current.upvotes);
        myHolder.source.setText(current.source);
        myHolder.status.setText(current.status);
    }

    @Override
    public int getItemCount() {
        return tutsList.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{

        TextView tut_name;
        TextView submitted_by;
        TextView upvote;
        TextView source;
        TextView status;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            tut_name= (TextView) itemView.findViewById(R.id.tv);
            submitted_by = (TextView) itemView.findViewById(R.id.tv2);
            upvote = (TextView) itemView.findViewById(R.id.tv3);
            source = (TextView) itemView.findViewById(R.id.tv4);
            status = (TextView) itemView.findViewById(R.id.tv5);
        }

    }
}
