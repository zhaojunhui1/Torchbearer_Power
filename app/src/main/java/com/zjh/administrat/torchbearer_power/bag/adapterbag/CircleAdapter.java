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
import com.zjh.administrat.torchbearer_power.bag.beanbag.CircleBeanLB;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CircleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CircleBeanLB.ResultBean> mCircle;
    private Context mContext;

    public CircleAdapter(Context context) {
        this.mContext = context;
        mCircle = new ArrayList<>();
    }

    public void setDataAll(List<CircleBeanLB.ResultBean> result) {
         if (result != null){
             mCircle.addAll(result);
         }
         notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.circle_item_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder mHolder = (ViewHolder) viewHolder;
        mHolder.circle_name.setText(mCircle.get(i).getNickName());
        //展示发布时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = formatter.format(mCircle.get(i).getCreateTime());
        mHolder.circle_time.setText(format);
        mHolder.circle_title.setText(mCircle.get(i).getContent());
        //mHolder.circle_ok.setText(mCircle.get(i).getGreatNum());

        mHolder.circle_simple.setImageURI(mCircle.get(i).getHeadPic());
        //截取字符串
        String image = mCircle.get(i).getImage();
        String[] split = image.split(",");
        mHolder.circle_image_simple.setImageURI(split[0]);
    }

    @Override
    public int getItemCount() {
        return mCircle.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public SimpleDraweeView circle_simple, circle_image_simple;
        public TextView circle_name, circle_time, circle_title, circle_ok;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circle_simple = itemView.findViewById(R.id.circle_simple);
            circle_name = itemView.findViewById(R.id.circle_name);
            circle_time = itemView.findViewById(R.id.circle_time);
            circle_title = itemView.findViewById(R.id.circle_title);
            circle_image_simple = itemView.findViewById(R.id.circle_image);
            circle_ok = itemView.findViewById(R.id.circle_ok);

        }
    }

}
