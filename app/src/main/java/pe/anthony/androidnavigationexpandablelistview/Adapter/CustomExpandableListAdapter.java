package pe.anthony.androidnavigationexpandablelistview.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import pe.anthony.androidnavigationexpandablelistview.R;

/**
 * Created by ANTHONY on 18/02/2018.
 */

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    // Como estamos mostrando un expandible un lista, esto practicante es un listView y como tal ponemos personalizar

    private Context context;
    private List<String> listTitle;
    private Map<String,List<String>> listItem;

    public CustomExpandableListAdapter(Context context, List<String> listTitle, Map<String, List<String>> listItem) {
        this.context = context;
        this.listTitle = listTitle;
        this.listItem = listItem;
    }

    @Override
    public int getGroupCount() {
        return listTitle.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listItem.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listTitle.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listItem.get(listTitle.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    //Aqui es donde se personaliza para el padre listView -> Android Programing, Xamarin Programming
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
       String title = (String)getGroup(groupPosition);
       if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_group,null);
       }
        TextView txtTitle = convertView.findViewById(R.id.listTitle);
       txtTitle.setTypeface(null, Typeface.BOLD);
       txtTitle.setText(title);
       return convertView;
    }

    //Aqui es donde se personaliza para el hijo listView -> Beginner, Intermediate and Advanced
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String title = (String)getChild(groupPosition,childPosition);
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item,null);
        }
        TextView txtChild = convertView.findViewById(R.id.expandedListItem);
        txtChild.setText(title);
        return convertView;
    }

    //Solo es para que se vea que el hijo esta siendo seleccionado y debe estar en true para esto
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
