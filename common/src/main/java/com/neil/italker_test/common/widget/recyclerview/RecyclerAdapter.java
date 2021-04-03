package com.neil.italker_test.common.widget.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.neil.italker_test.common.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class RecyclerAdapter<Data>
        extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder<Data>>
        implements View.OnClickListener, View.OnLongClickListener, AdapterCallback<Data> {

    private final List<Data> mDataList;
    private AdapterListener<Data> mDataAdapterListener;

//    @NonNull
//    @Override
//    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return null;
//    }

    /**
     * 构造函数
     */
    public RecyclerAdapter() {
        this(null);
    }
    public RecyclerAdapter(AdapterListener<Data> listener){
        this(new ArrayList<Data>(), listener);
    }

    public RecyclerAdapter(List<Data> dataList, AdapterListener<Data> listener){
        this.mDataList = dataList;
        this.mDataAdapterListener = listener;
    }

    /**
     * 复写默认的布局类型，复写后返回的都是xml文件的id
     * @name getItemViewType
     * @author nan2.zhong
     * @date 3/20/21 2:59 PM
     [position]
     * @return int
     */
    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position, mDataList.get(position));
    }

    /**
     * 得到布局文件的类型
     * @name getItemViewType
     * @author nan2.zhong
     * @date 3/20/21 3:00 PM 
     [position 坐标, data 当前数据]
     * @return int xml文件id，用于创建viewholder
     */
    @LayoutRes
    protected abstract int getItemViewType(int position, Data data);

    /**
     * 创建一个viewholder
     * @name onCreateViewHolder
     * @author nan2.zhong
     * @date 3/20/21 2:14 PM
     [parent, viewType] viewType:界面类型 约定为xml布局的界面id
     * @return com.neil.italker_test.common.widget.recyclerview.RecyclerAdapter.ViewHolder<Data>
     */
    @NonNull
    @Override
    public ViewHolder<Data> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // 得到layoutInflate用于把xml初始为View
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // 把XML id 为 viewtype的文件初始化为一个root view
        View root = layoutInflater.inflate(viewType,parent,false);
        //通过子类必须实现的方法，得到一个viewHolder
        ViewHolder<Data> holder = onCreateViewHolder(root, viewType);
        //设置/view的tag为viewholder 进行双向绑定
        root.setTag(R.id.tag_recycler_holder);
        //设置事件点击，因此可以通过回调触发点击
        root.setOnClickListener(this);
        root.setOnLongClickListener(this);


        //进行界面竹节绑定
        holder.mUnbinder = ButterKnife.bind(holder, root);
        //绑定callback
        holder.mCallback = this;
        return null;
    }

    /**
     * 得到一个新的ViewHolder
     * @name onCreateViewHolder
     * @author nan2.zhong
     * @date 3/20/21 2:56 PM
     [root, ViewType]
     * @return com.neil.italker_test.common.widget.recyclerview.RecyclerAdapter.ViewHolder<Data>
     */
    protected abstract ViewHolder<Data> onCreateViewHolder(View root, int ViewType);

    /**
     * 绑定数据到一个holder上
     * @name onBindViewHolder
     * @author nan2.zhong
     * @date 3/20/21 2:13 PM
     [holder, position]
     * @return void
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder<Data> holder, int position) {
        //得到需要绑定的数据
        Data data = mDataList.get(position);
        //触发holder的绑定方法
        holder.bind(data);
    }

//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//
//    }

    /**
     * 得到当前集合的数据量
     * @name getItemCount
     * @author nan2.zhong
     * @date 3/20/21 2:12 PM
     []
     * @return int
     */
    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    /**
     * 插入并通知插入
     * @name add
     * @author nan2.zhong
     * @date 3/22/21 6:46 PM
     [data]
     * @return void
     */
    public void add(Data data) {
        mDataList.add(data);
        notifyItemInserted(mDataList.size() - 1);
    }

    /**
     * 插入一堆数据并通知
     * @name add
     * @author nan2.zhong
     * @date 3/22/21 6:46 PM
     [dataList]
     * @return void
     */
    public void add(Data... dataList) {
        if(dataList != null && dataList.length > 0) {
            int startPos = mDataList.size();
            Collections.addAll(mDataList, dataList);
            notifyItemRangeChanged(startPos, dataList.length);
        }
    }

    /**
     * 插入一堆数据并通知
     * @name add
     * @author nan2.zhong
     * @date 3/22/21 6:48 PM
     [dataList]
     * @return void
     */
    public void add(Collection<Data> dataList) {
        if(dataList != null && dataList.size() > 0) {
            int startPos = mDataList.size();
            mDataList.addAll(dataList);
            notifyItemRangeChanged(startPos, dataList.size());
        }
    }

    /**
     * 删除操作
     * @name clear
     * @author nan2.zhong
     * @date 3/22/21 6:50 PM
     []
     * @return void
     */
    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    /**
     * 替换/避免重复刷新 直接先清空
     * @name replace
     * @author nan2.zhong
     * @date 3/22/21 6:50 PM
     [dataList]
     * @return void
     */
    public void replace(Collection<Data> dataList) {
        mDataList.clear();
        if(dataList == null || dataList.size() == 0) {
            return;
        }
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        ViewHolder<Data> viewHolder = (ViewHolder<Data>) v.getTag(R.id.tag_recycler_holder);
        if(this.mDataAdapterListener != null) {
            //得到viewholder当前对应的适配器中的坐标
            int pos = viewHolder.getAdapterPosition();
            this.mDataAdapterListener.onItemClick(viewHolder, mDataList.get(pos));
        }
    }

    @Override
    public boolean onLongClick(View v) {
        ViewHolder<Data> viewHolder = (ViewHolder<Data>) v.getTag(R.id.tag_recycler_holder);
        if(this.mDataAdapterListener != null) {
            //得到viewholder当前对应的适配器中的坐标
            int pos = viewHolder.getAdapterPosition();
            this.mDataAdapterListener.onItemLongClick(viewHolder, mDataList.get(pos));
            return true;
        }
        return false;
    }

    /**
     * 设置监听器
     * @name setDataAdapterListener
     * @author nan2.zhong
     * @date 3/22/21 7:01 PM
     [adapterListener]
     * @return void
     */
    public void setDataAdapterListener(AdapterListener<Data> adapterListener) {
        this.mDataAdapterListener = adapterListener;
    }

    /**
     * 自定义监听器
     * @name
     * @author nan2.zhong
     * @date 3/22/21 6:56 PM

     * @return
     */
    public interface AdapterListener<Data> {
        //当cell点击时触发
        void onItemClick(RecyclerAdapter.ViewHolder holder, Data data);
        //当cell长按时触发
        void onItemLongClick(RecyclerAdapter.ViewHolder holder, Data data);
    }

    /**
     * TO-DO
     * @name 自定义viewholder 范型类型
     * @author nan2.zhong
     * @date 3/22/21 6:43 PM

     * @return
     */
    public static abstract class ViewHolder<Data> extends RecyclerView.ViewHolder {

        private Unbinder mUnbinder;
        private AdapterCallback<Data> mCallback;
        protected Data mData;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        /**
         * 用于绑定事件的触发
         * @name bind
         * @author nan2.zhong
         * @date 3/20/21 2:07 PM
         [data]
         * @return void
         */
        void bind(Data data) {
            this.mData = data;
            onBind(data);
        }

        /**
         * 当绑定数据时触发的回调 必须复写
         * @name onBind
         * @author nan2.zhong
         * @date 3/20/21 2:07 PM
         [data]
         * @return void
         */
        protected abstract void onBind(Data data);

        /**
         * holder 自己对自己对应的Data进行更新操作
         * @name updateData
         * @author nan2.zhong
         * @date 3/20/21 2:54 PM
         [data]
         * @return void
         */
        public void updateData(Data data) {
            if(this.mCallback != null) {
                this.mCallback.update(data, this);
            }
        }
    }
}
