package io.github.timeime.time;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapterDataName extends BaseAdapter {

    private Context context;
    private static LayoutInflater inflater = null;
    private ArrayList<DB> mDBs;

    public ListAdapterDataName(Context c, ArrayList<DB> arrayProducts) {
        context = c;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mDBs = arrayProducts;
    }

    public int getCount() {
        return mDBs.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        vi = inflater.inflate(R.layout.item_list2, null);
        TextView text = (TextView) vi.findViewById(R.id.text_list111);
        text.setText(mDBs.get(position).getName());
        return vi;
    }
}