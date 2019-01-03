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

public class HomeDataPzshAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<HomeDataBean.ResultBean.PzshBean.CommodityListBeanX> pzshList;
    private Context mContext;

    public HomeDataPzshAdapter(Context context) {
        this.mContext = context;
        pzshList = new ArrayList<>();
    }

    //品质生活
    public void setPzshData(List<HomeDataBean.ResultBean.PzshBean.CommodityListBeanX> commodityList) {
        if (commodityList != null){
            pzshList.addAll(commodityList);
        }
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.pzsh_item_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder mHolder = (ViewHolder) viewHolder;
            mHolder.pzsh_title.setText(pzshList.get(i).getCommodityName());
            mHolder.pzsh_price.setText("￥"+pzshList.get(i).getPrice());
            mHolder.pzsh_simple.setImageURI(pzshList.get(i).getMasterPic());

    }

    @Override
    public int getItemCount() {
        return pzshList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public SimpleDraweeView pzsh_simple;
        public TextView pzsh_title, pzsh_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pzsh_simple = itemView.findViewById(R.id.pzsh_simple);
            pzsh_title = itemView.findViewById(R.id.pzsh_title);
            pzsh_price = itemView.findViewById(R.id.pzsh_price);
        }
    }

}
