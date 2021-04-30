package com.example.wiscpets;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private final List<String> expandableListTitle_org;
    private final HashMap<String, List<String>> original_expandableListDetail;
    private HashMap<String, List<String>> expandableListDetail;
    private DatabaseManager db = new DatabaseManager();

    public CustomExpandableListAdapter(Context context, List<String> expandableListTitle,
                                       HashMap<String, List<String>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
        this.original_expandableListDetail = expandableListDetail;
        this.expandableListTitle_org = expandableListTitle;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.expandedListItem);
        expandedListTextView.setText(expandedListText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition)).size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }

    public void restoreData(){
        expandableListTitle= expandableListTitle_org;
        expandableListDetail = original_expandableListDetail;

    }

    public void filterData(String query){

        query = query.toLowerCase();
        expandableListDetail.clear();
        expandableListTitle.clear();

        if(query.isEmpty()){
            expandableListTitle= expandableListTitle_org;
            expandableListDetail = original_expandableListDetail;
        }
        else {
            //do the filter
            Log.i("patient", "enter "+ expandableListTitle_org.size());
            JSONObject response = db.getAllPetNames(query);
            try {
                if (response == null) {
                    expandableListTitle.add("No pets");
                    expandableListDetail.put("No pets", new ArrayList<>());
                } else {
                    JSONArray pets = response.getJSONArray("response");
                    for (int i = 0; i < pets.length(); i++) {
                        String name = pets.getJSONObject(i).getString("name");
                        int petid = pets.getJSONObject(i).getInt("petid");
                        expandableListTitle.add(petid + " " + name);

                        ArrayList<String> petVitalInfo = new ArrayList<>();
                        JSONObject vitals = db.getVitals(String.valueOf(petid));
                        JSONArray vitalsList = vitals.getJSONArray("response");
                        for (int j = 0; j < vitalsList.length(); j++) {
                            JSONObject visit = vitalsList.getJSONObject(j);
                            petVitalInfo.add(visit.getString("Visit_Date"));
                        }

                        expandableListDetail.put(petid + " " + name, petVitalInfo);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
                if(expandableListDetail.size() <= 0){
                    new AlertDialog.Builder(context)
                            .setTitle("Alert Message")
                            .setMessage("No Result found, please try again")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

            }
        }
        notifyDataSetChanged();

    }
}