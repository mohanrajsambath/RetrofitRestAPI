package com.apple.contactsretrofitapi;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by gokulrajkumar on 05/11/16.
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder>{

    private List<UserPojo.ContactsBean> ItemsBean;


    public UserAdapter(List<UserPojo.ContactsBean> itemsBean) {
        ItemsBean = itemsBean;


        System.out.println("printa" + itemsBean );

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.eachrowlayout,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.title.setText(ItemsBean.get(position).getName());
        holder.score.setText(ItemsBean.get(position).getEmail());
        holder.txtVw_test.setText(ItemsBean.get(position).getPhone().getMobile());

    }

    @Override
    public int getItemCount() {
        return ItemsBean.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title,score,txtVw_test;

        public MyViewHolder(View itemView) {

            super(itemView);

            title = (TextView) itemView.findViewById(R.id.txtVw_title);
            score = (TextView) itemView.findViewById(R.id.txtVw_count);
            txtVw_test = (TextView) itemView.findViewById(R.id.txtVw_test);
        }
    }

}
