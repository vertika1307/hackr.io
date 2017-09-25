package com.production.hackr;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {

    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    private List<LangName> langNamesList = null;
    private ArrayList<LangName> arraylist;

    public ListViewAdapter(Context context, List<LangName> langNamesList) {
        mContext = context;
        this.langNamesList = langNamesList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<LangName>();
        this.arraylist.addAll(langNamesList);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return langNamesList.size();
    }

    @Override
    public LangName getItem(int position) {
        return langNamesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_item, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(langNamesList.get(position).getLangName());
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        langNamesList.clear();
        if (charText.length() == 0) {
            langNamesList.addAll(arraylist);
        } else {
            for (LangName wp : arraylist) {
                if (wp.getlangName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    langNamesList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
