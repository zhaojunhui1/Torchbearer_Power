package com.zjh.administrat.torchbearer_power.bag.adapterbag;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zjh.administrat.torchbearer_power.R;
import com.zjh.administrat.torchbearer_power.bag.beanbag.HomeDataBean;

import java.util.ArrayList;
import java.util.List;

public class HomeDataRxxpAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<HomeDataBean.ResultBean.RxxpBean.CommodityListBean> rxxpList;
    private Context mContext;

    public HomeDataRxxpAdapter(Context context) {
        this.mContext = context;
        rxxpList = new ArrayList<>();
    }

    //日销新品
    public void setRxxpData(List<HomeDataBean.ResultBean.RxxpBean.CommodityListBean> commodityList) {
        if (commodityList != null){
            rxxpList.addAll(commodityList);
        }
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rxxp_item_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder mHolder = (ViewHolder) viewHolder;
            mHolder.rxxp_title.setText(rxxpList.get(i).getCommodityName());
            mHolder.rxxp_price.setText("￥"+rxxpList.get(i).getPrice());
            mHolder.simple_image.setImageURI(rxxpList.get(i).getMasterPic());

    }

    @Override
    public int getItemCount() {
        return rxxpList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public SimpleDraweeView simple_image;
        public TextView rxxp_title, rxxp_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            simple_image = itemView.findViewById(R.id.simple_image);
            rxxp_title = itemView.findViewById(R.id.rxxp_title);
            rxxp_price = itemView.findViewById(R.id.rxxp_price);
        }
    }

}
