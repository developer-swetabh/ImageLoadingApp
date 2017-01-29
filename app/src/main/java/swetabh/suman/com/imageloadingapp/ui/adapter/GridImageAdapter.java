package swetabh.suman.com.imageloadingapp.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import swetabh.suman.com.imageloadingapp.R;
import swetabh.suman.com.imageloadingapp.data.model.ResponseModel;
import swetabh.suman.com.imageloadingapp.ui.ImageGridFragment;
import swetabh.suman.com.imageloadingapp.util.Utils;
import swetabh.suman.com.imageloadinglibrary.ImageFetcher;

/**
 * Created by swetabh on 25/01/17.
 */

public class GridImageAdapter extends RecyclerView.Adapter<GridImageAdapter.ItemHolder> {

    private ImageFetcher mImageFetcher;
    private List<ResponseModel> mList;
    private int mItemHeight = 0;
    private int mNumColumns = 0;
    private LinearLayout.LayoutParams mImageViewLayoutParams;
    private Context mContext;
    private ImageGridFragment mFragment;
    int previousPosition = 0;

    public GridImageAdapter(FragmentActivity activity, ImageFetcher fetcher, List<ResponseModel> modelList) {
        mImageFetcher = fetcher;
        mList = modelList;
        mContext = activity;
        mImageViewLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public GridImageAdapter.ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_grid_item, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(GridImageAdapter.ItemHolder holder, int position) {

        holder.vh_Title.setText(mList.get(position).getUser().getName());
        holder.vh_Subtitle.setText(mContext.getString(R.string.nature, mList.get(position).getCategories().get(0).getTitle()));
        // Check the height matches our calculated column width
        if (holder.vh_Image.getLayoutParams().height != mItemHeight) {
            holder.vh_Image.setLayoutParams(mImageViewLayoutParams);
        }

        if (position > previousPosition) {
            Utils.animate(holder, true);
        } else {
            Utils.animate(holder, false);
        }
        previousPosition = position;
        mImageFetcher.loadImage(mList.get(position).getUrls().getRegular(), holder.vh_Image);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void addData(List<ResponseModel> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public void addAllData(List<ResponseModel> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public ImageView vh_Image;
        public TextView vh_Title;
        public TextView vh_Subtitle;

        public ItemHolder(View itemView) {
            super(itemView);
            vh_Image = (ImageView) itemView.findViewById(R.id.imageView);
            vh_Title = (TextView) itemView.findViewById(R.id.textView_title);
            vh_Subtitle = (TextView) itemView.findViewById(R.id.textView_subtitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mFragment.setImageView(vh_Image, mList.get(getAdapterPosition()).getUrls().getFull());
                }
            });

        }
    }

    public void setNumColumns(int numColumns) {
        mNumColumns = numColumns;
    }

    public int getNumColumns() {
        return mNumColumns;
    }

    public void setItemHeight(int height) {
        if (height == mItemHeight) {
            return;
        }
        mItemHeight = height;
        mImageViewLayoutParams =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mItemHeight);
        mImageFetcher.setImageSize(height);
        notifyDataSetChanged();
    }

    public void setFragment(Fragment fragment) {
        mFragment = (ImageGridFragment) fragment;
    }
}
