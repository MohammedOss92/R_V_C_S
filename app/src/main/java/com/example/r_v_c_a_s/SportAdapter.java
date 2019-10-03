package com.example.r_v_c_a_s;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SportAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final String TAG = "SportAdapter";
    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    private Callback mCallback;
    private List<Sport> mSportList;


public SportAdapter(List<Sport> sportList) {
        mSportList = sportList;
    }




    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
            case VIEW_TYPE_EMPTY:
            default:
                return new EmptyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_view, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mSportList != null && mSportList.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public int getItemCount() {
        if (mSportList != null && mSportList.size() > 0) {
            return mSportList.size();
        } else {
            return 1;
        }
    }

    public void addItems(List<Sport> sportList) {
        mSportList.addAll(sportList);
        notifyDataSetChanged();
    }

    public interface Callback {
        void onEmptyViewRetryClick();
    }
    /*****************************/

    public class ViewHolder extends BaseViewHolder {

        ImageView coverImageView;

        TextView titleTextView;

        TextView newsTextView;

        TextView infoTextView;


        public ViewHolder(View itemView) {
            super(itemView);
            coverImageView=(ImageView)itemView.findViewById(R.id.thumbnail); 
             titleTextView =(TextView)itemView.findViewById(R.id.title);
             newsTextView  =(TextView)itemView.findViewById(R.id.newsTitle);
             infoTextView  =(TextView)itemView.findViewById(R.id.newsInfo);
            
        }

        protected void clear() {
            coverImageView.setImageDrawable(null);
            titleTextView.setText("");
            newsTextView.setText("");
            infoTextView.setText("");
        }

        public void onBind(int position) {
            super.onBind(position);

            final Sport mSport = mSportList.get(position);

//            if (mSport.getImageUrl() != null) {
//                Glide.with(itemView.getContext())
//                        .load(mSport.getImageUrl())
//                        .into(coverImageView);
//            }

            if (mSport.getTitle() != null) {
                titleTextView.setText(mSport.getTitle());
            }

            if (mSport.getSubTitle() != null) {
                newsTextView.setText(mSport.getSubTitle());
            }

            if (mSport.getInfo() != null) {
                infoTextView.setText(mSport.getInfo());
            }

//            itemView.setOnClickListener(v -> {
//                if (mSport.getImageUrl() != null) {
//                    try {
//                        Intent intent = new Intent();
//                        intent.setAction(Intent.ACTION_VIEW);
//                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
//                        intent.setData(Uri.parse(mSport.getImageUrl()));
//                        itemView.getContext().startActivity(intent);
//                    } catch (Exception e) {
//                        Log.e(TAG, "onClick: Image url is not correct");
//                    }
//                }
//            });
        }
    }

    public class EmptyViewHolder extends BaseViewHolder {

        
        TextView messageTextView;
        
        TextView buttonRetry;
        

        EmptyViewHolder(View itemView) {
            super(itemView);
            messageTextView=(TextView)itemView.findViewById(R.id.tv_message); 
            buttonRetry=(TextView)itemView.findViewById(R.id.buttonRetry); 
            
            buttonRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onEmptyViewRetryClick();
                }
            });
        }

        @Override
        protected void clear() {

        }

    }
}
