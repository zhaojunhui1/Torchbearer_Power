package com.zjh.administrat.torchbearer_power.bag.adapterbag;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
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

public class HomeDataMlssAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<HomeDataBean.ResultBean.MlssBean.CommodityListBeanXX> mlssList;
    private Context mContext;

    public HomeDataMlssAdapter(Context context) {
        this.mContext = context;
        mlssList = new ArrayList<>();
    }

    //魔力时尚
    public void setMlssData(List<HomeDataBean.ResultBean.MlssBean.CommodityListBeanXX> commodityList) {
        if (commodityList != null){
            mlssList.addAll(commodityList);
        }
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.mlss_item_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder mHolder = (ViewHolder) viewHolder;
        mHolder.mlss_title.setText(mlssList.get(i).getCommodityName());
        mHolder.mlss_price.setText("￥"+mlssList.get(i).getPrice());
        mHolder.mlss_simple.setImageURI(mlssList.get(i).getMasterPic());

    }

    @Override
    public int getItemCount() {
        return mlssList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        public SimpleDraweeView mlss_simple;
        public TextView mlss_title, mlss_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mlss_simple = itemView.findViewById(R.id.mlss_simple);
            mlss_title = itemView.findViewById(R.id.mlss_title);
            mlss_price = itemView.findViewById(R.id.mlss_price);
        }
    }
}
