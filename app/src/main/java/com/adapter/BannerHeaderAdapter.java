package com.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.greendaodemo2.R;
import com.widget.CustomGridView;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.indexablerv.IndexableHeaderAdapter;

/**
 * Created by qlshi on 2018/8/13.
 */

public class BannerHeaderAdapter extends IndexableHeaderAdapter {
    private LayoutInflater inflate;
    private ArrayList<String> list;
    private Context mContext;
    private CityGridViewAdapter cityGridViewAdapter;

    public BannerHeaderAdapter(Context context, String index, String indexTitle, List datas, String[] city) {
        super(index, indexTitle, datas);
        this.mContext = context;
        inflate = LayoutInflater.from(context);
        list = new ArrayList<>();
        for (int i = 0; i < city.length; i++) {
            list.add(city[i]);
        }
    }

    @Override
    public int getItemViewType() {
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
        View view = this.inflate.inflate(R.layout.item_city_header, parent, false);
        VH holder = new VH(view);
        return holder;
    }

    @Override
    public void onBindContentViewHolder(RecyclerView.ViewHolder holder, Object entity) {
        final VH vh = (VH) holder;
        cityGridViewAdapter = new CityGridViewAdapter(mContext, list);
        vh.head_city_gridview.setAdapter(cityGridViewAdapter);
        vh.head_city_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setCity(list.get(position));
            }
        });
        vh.item_header_city_dw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCity(vh.item_header_city_dw.getText().toString());
            }
        });
    }

    private void setCity(String city) {
        if (mListener != null) {
            mListener.OnChangeCity(city);
        }
    }

    private class VH extends RecyclerView.ViewHolder {
        GridView head_city_gridview;
        TextView item_header_city_dw;

        public VH(View itemView) {
            super(itemView);
            head_city_gridview = (CustomGridView) itemView.findViewById(R.id.item_header_city_gridview);
            item_header_city_dw = itemView.findViewById(R.id.item_header_city_dw);
        }
    }

    public interface OnChangeCityListener {
        void OnChangeCity(String city);
    }

    public OnChangeCityListener mListener;

    public void setOnChangeCityListener(OnChangeCityListener listener) {
        this.mListener = listener;
    }
}
