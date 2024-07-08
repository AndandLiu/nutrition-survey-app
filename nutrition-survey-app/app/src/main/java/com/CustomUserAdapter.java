package com.jla388.sfu.greenfoodchallenge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomUserAdapter extends ArrayAdapter<Pledge> {


    private static final int NONE = 0;
    private static final int RICHMOND = 1;
    private static final int BURNABY = 2;

    private int filterSetting = 0;


    public void setFilterSetting(int filter){
        filterSetting = filter;

    }

    public CustomUserAdapter(Context context, ArrayList<Pledge> pledges){
        super(context,0,pledges);
    }


    public void displayRichmond(TextView name, TextView municipality, Pledge user){

        String Municipality = user.getMunicipality();

        if(Municipality.equals("Richmond")){
            name.setText(user.getName());
            municipality.setText(user.getMunicipality());
        } else{
            //Don't show anything
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_email, parent, false);
        }

        // Get the data item for this position
        Pledge user = getItem(position);

        // Lookup view for data population
        //Improve names later
        TextView Name = (TextView) convertView.findViewById(R.id.tvName);
        TextView Municipality = (TextView) convertView.findViewById(R.id.Municipality);


        if(filterSetting == 0) {
            Name.setText(user.getName());
            Municipality.setText(user.getMunicipality());
        } else if(filterSetting == RICHMOND){
            displayRichmond(Name,Municipality,user);
        } else if(filterSetting == BURNABY){

        }




        // Return the completed view to render on screen
        return convertView;

    }

}
