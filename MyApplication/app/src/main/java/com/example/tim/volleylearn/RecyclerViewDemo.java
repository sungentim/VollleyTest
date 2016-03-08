package com.example.tim.volleylearn;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tim.volleylearn.recyclerview.DividerGridItemDecoration;
import com.example.tim.volleylearn.recyclerview.DivierItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RecyclerViewDemo extends AppCompatActivity {

    @InjectView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private List<String> mDatas;
    MyRecycelerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_demo);
        ButterKnife.inject(this);
        initData();
        initView();
    }

    private void initData() {
        mDatas = new ArrayList<String>();
        for (int i = 0; i < 80; i++) {
            mDatas.add("item" + i);
        }
    }

    private void initView() {
        adapter = new MyRecycelerAdapter(this,mDatas);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
//        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));


    }

    class MyRecycelerAdapter extends RecyclerView.Adapter<MyRecycelerAdapter.MyViewHolder> {
        private List<String> mDatas;
        private Context mContext;
        private LayoutInflater inflater;

        private List<Integer> mHeights ;
        public MyRecycelerAdapter(Context context, List<String> datas){
            this. mContext=context;
            this. mDatas=datas;
            inflater=LayoutInflater. from(mContext);
            mHeights = new ArrayList<>();
            for(String item:datas){
                mHeights.add((int) (100+Math.random()*300));
            }
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = inflater.inflate(R.layout.simple_recycler_item,parent,false);




            MyViewHolder viewHolder = new MyViewHolder(view);

            return viewHolder;
        }

        //填充onCreateViewHolder返回的holder控件
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            ViewGroup.LayoutParams params = holder.tv.getLayoutParams();

            params.height = mHeights.get(position);

            holder.tv.setLayoutParams(params);

            holder.tv.setText(mDatas.get(position));
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv;

            public MyViewHolder(View view) {
                super(view);
                this.tv = (TextView) view.findViewById(R.id.item_tv);
            }
        }

    }
}
