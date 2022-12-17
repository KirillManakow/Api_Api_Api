package com.example.apiwork;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AdapterMask  extends BaseAdapter implements Filterable {

    private Context mContext;
    List<Mask> maskList;

    public AdapterMask(Context mContext, List<Mask> listZacazis) {
        this.mContext = mContext;
        this.maskList = listZacazis;
    }

    @Override
    public int getCount() {
        return maskList.size();
    }

    @Override
    public Object getItem(int i) {
        return maskList.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return maskList.get(i).getId_zakaza();
    }

    private Bitmap getUserImage(String encodedImg)
    {

        if(encodedImg!=null&& !encodedImg.equals("null")) {
            byte[] bytes = Base64.decode(encodedImg, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        else
        {
            return  BitmapFactory.decodeResource(mContext.getResources(),R.drawable.ic_launcher_playstore);

        }
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        View v = View.inflate(mContext,R.layout.item_layuot,null);
        TextView konfiguraciaa = v.findViewById(R.id.Konfiguration);
        TextView zenaa = v.findViewById(R.id.Zena);
        TextView userr = v.findViewById(R.id.User);
        ImageView imageView = v.findViewById(R.id.imageView);
        Mask mask = maskList.get(i);
        userr.setText(mask.getUser());
        konfiguraciaa.setText(mask.getKonfiguracia());
        zenaa.setText(Integer.toString(mask.getZena()));

      imageView.setImageBitmap(getUserImage(mask.getImg()));

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenDetalis=new Intent(mContext,UpdateApi.class);
                intenDetalis.putExtra("zakaz",mask);
                mContext.startActivity(intenDetalis);

            }
        });
        return v;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                maskList = (ArrayList<Mask>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                ArrayList<Mask> FilteredArrList = new ArrayList<>();

                if (maskList == null) {
                    maskList = new ArrayList<>(maskList);
                }

                if (constraint == null || constraint.length() == 0) {

                    results.count = maskList.size();
                    results.values = maskList;
                } else {
                    constraint = constraint.toString().toLowerCase();

                    for (int i = 0; i < maskList.size(); i++) {
                        String data = maskList.get(i).getUser();
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(new Mask(maskList.get(i).getId_zakaza(),
                                    maskList.get(i).getUser(),
                                    maskList.get(i).getKonfiguracia(),
                                    maskList.get(i).getZena(),
                                    maskList.get(i).getImg()));
                        }
                    }
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
    }

}

