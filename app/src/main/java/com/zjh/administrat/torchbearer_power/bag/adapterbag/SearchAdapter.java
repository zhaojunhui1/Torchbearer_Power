package com.zjh.administrat.torchbearer_power.bag.adapterbag;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zjh.administrat.torchbearer_power.R;
import com.zjh.administrat.torchbearer_power.bag.beanbag.HomeSearchBean;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<HomeSearchBean.ResultBean> mData;
    private Context mContect;

    public SearchAdapter(Context mContect) {
        this.mContect = mContect;
        mData = new ArrayList<>();
    }

    public void setDatas(List<HomeSearchBean.ResultBean> result) {
        if (result != null){
            mData.addAll(result);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_data_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder mHolder = (ViewHolder) viewHolder;
        mHolder.search_title.setText(mData.get(i).getCommodityName());
        mHolder.search_price.setText("￥"+mData.get(i).getPrice());
        mHolder.search_number.setText("已售"+mData.get(i).getSaleNum()+"件");

        ImageLoader.getInstance().displayImage(mData.get(i).getMasterPic(), mHolder.search_imageView);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView search_imageView;
        public TextView search_title, search_price, search_number;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            search_imageView = itemView.findViewById(R.id.search_imageView);
            search_title = itemView.findViewById(R.id.search_title);
            search_price = itemView.findViewById(R.id.search_price);
            search_number = itemView.findViewById(R.id.search_number);
        }
    }

}
