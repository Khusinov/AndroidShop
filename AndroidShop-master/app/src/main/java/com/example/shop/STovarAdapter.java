package com.example.shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class STovarAdapter  extends ArrayAdapter<STovar> {

    public ArrayList<STovar> list;
    public ArrayList<STovar> originalList = new ArrayList<>();
    private Integer position=-1;
    private MyFilter filter;

    public STovarAdapter(Context context, int resource, ArrayList<STovar> STovars) {
        super(context, resource, STovars);
        list=STovars;
        originalList.addAll(STovars);
    }
    public void setPosition(int posit) {
        this.position = posit;
    }

    private int getPosition() {
        return this.position;
    }

    private String getModny(Double DoubleValue){
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        String formattedValue = decimalFormat.format(DoubleValue);
        return formattedValue;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        STovar STovar=getItem(position);
        LayoutInflater inflater =LayoutInflater.from(getContext());
        convertView=inflater.inflate(R.layout.stovar_item, parent, false);

        if(this.position.equals(position) ){
            convertView.setBackgroundResource(R.drawable.backgroun4ch);
        }
        else{
            convertView.setBackgroundResource(R.drawable.backgroun4);
        }


        ((TextView)convertView.findViewById(R.id.stovar_name)).setText(STovar.getNom());

        convertView.setTag(STovar);
        return convertView;
    }

    @Override
    public Filter getFilter() {
        if(filter==null)
        {
            filter=new MyFilter(originalList,this);
        }

        return filter;
    }

    class MyFilter extends Filter {

        private ArrayList<STovar> filterList;
        private STovarAdapter adapter;
        private FilterResults results;
        public MyFilter(ArrayList<STovar> filterList,STovarAdapter adapter) {
            this.filterList = filterList;
            this.adapter=adapter;
        }



        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            results=new FilterResults();

            if(charSequence != null && charSequence.length()>0) {

                charSequence=charSequence.toString().toUpperCase();

                String begin="";
                String end="";
                if(((String) charSequence).indexOf(' ')>0 && ((String) charSequence).indexOf(' ')<charSequence.length()-1){
                    begin=((String) charSequence).substring(0,((String) charSequence).indexOf(' '));
                    end=((String) charSequence).substring(((String) charSequence).lastIndexOf(' ')+1,charSequence.length());
                }
                ArrayList<STovar> filteredMovies=new ArrayList<>();

                if(!begin.isEmpty() && !end.isEmpty() ) {
//                Log.v("MyLog", begin + " " + end);
                    for(int i=0;i<filterList.size();i++) {
                        int index1=filterList.get(i).getNom().toUpperCase().indexOf(begin);
                        int index2=filterList.get(i).getNom().toUpperCase().indexOf(end);
                        if(index1>=0 && index2>index1) {
//                    Log.v("MyLog", String.valueOf(filterList.get(i).getName().toUpperCase().contains(end)));
                            filteredMovies.add(filterList.get(i));
                        }
                    }
                }
                else {
                    for (int i = 0; i < filterList.size(); i++) {
                        if (filterList.get(i).getNom().toUpperCase().contains(charSequence)) {
                            filteredMovies.add(filterList.get(i));
                        }
                        if(filterList.get(i).getShtrix().equals(charSequence)){
                            filteredMovies.add(filterList.get(i));
                        }
                        if(filterList.get(i).getShtrix1().equals(charSequence)){
                            filteredMovies.add(filterList.get(i));
                        }
                        if(filterList.get(i).getShtrix2().equals(charSequence)){
                            filteredMovies.add(filterList.get(i));
                        }
                        if(filterList.get(i).getShtrix_full().equals(charSequence)){
                            filteredMovies.add(filterList.get(i));
                        }


                    }
                }

                results.count=filteredMovies.size();
                results.values=filteredMovies;
            }
            else {
                results.count=adapter.originalList.size();
                results.values=adapter.originalList;
            }

            return results;
        }


        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            adapter.clear();
            adapter.addAll((ArrayList<STovar>) filterResults.values);
            adapter.notifyDataSetChanged();
        }

    }
}
