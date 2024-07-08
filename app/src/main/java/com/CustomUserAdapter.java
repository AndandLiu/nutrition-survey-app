package com.jla388.sfu.greenfoodchallenge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom adapter for Pledges
 */
public class CustomUserAdapter extends ArrayAdapter<Pledge> implements Filterable {

    private List<Pledge> pledgeOriginal; //original values

    private List<Pledge> filteredPledge = new ArrayList<>();
    private ArrayList<Pledge> pledgeDisplay;


    public CustomUserAdapter(Context context, ArrayList<Pledge> pledges){
        super(context,0,pledges);
        pledgeDisplay = pledges;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getCount(){
        if(pledgeDisplay == null){
            return 0;
        }
        return pledgeDisplay.size();
     }

    @Override
    public Filter getFilter(){
        return new CFilter();
    }

    //Filter seems to be working properly... hmm??
    private class CFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();
            filteredPledge = new ArrayList<>();

            if(pledgeOriginal == null) {
                pledgeOriginal = new ArrayList<>();
                pledgeOriginal.addAll(pledgeDisplay); //deep copy
            }
            if(constraint == null || constraint.length() == 0){
                results.count = pledgeOriginal.size();
                results.values = pledgeOriginal;
                filteredPledge.addAll(pledgeOriginal);
            }  else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Pledge p : pledgeOriginal) {
                    if (p.getMunicipality().toLowerCase().contains(filterPattern)) {
                        filteredPledge.add(p);
                    }
                }
                for(int i = 0; i< filteredPledge.size();i++){
                    System.out.println(filteredPledge.get(i).getMunicipality() + "      HIII");
                }
                results.values = filteredPledge;
                results.count = filteredPledge.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredPledge = (List<Pledge>) results.values;

            pledgeDisplay.clear();
            for(Pledge pledge : filteredPledge){
                pledgeDisplay.add(pledge);
            }

            notifyDataSetChanged();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_user_adapter_layout, parent, false);
        }

        // Lookup view for data population
        //Improve names later
        TextView Name = (TextView) convertView.findViewById(R.id.tvName);
        TextView Municipality = (TextView) convertView.findViewById(R.id.Municipality);
        TextView PledgedAmount = (TextView) convertView.findViewById(R.id.PledgedAmount);



        Pledge user = getItem(position);


            Name.setText(user.getName());
            Municipality.setText(user.getMunicipality());

            String pledgeText = " pledged " + user.getPledgedAmount() + " kg of C02e";
            PledgedAmount.setText(pledgeText);


        return convertView;
    }



}
