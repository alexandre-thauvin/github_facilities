package alexandre.thauvin.github_facilities;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class RecyclerSubReddit extends RecyclerView.Adapter<RecyclerSubReddit.ViewHolder> {

    private final List<Post> mValues;

    public RecyclerSubReddit(List<Post> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTitle.setText(mValues.get(position).getTitle());
        holder.mSubreddit.setText(mValues.get(position).getSubreddit());
        holder.mUrl.setText(mValues.get(position).getUrl());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final View mView;
        private final TextView mTitle;
        private final TextView mSubreddit;
        private final TextView mUrl;
        private Post mItem;

        private ViewHolder(View view) {
            super(view);
            mView = view;
            mUrl = view.findViewById(R.id.url);
            mTitle =  view.findViewById(R.id.title);
            mSubreddit =  view.findViewById(R.id.subreddit);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mSubreddit.getText() + "'";
        }
    }
}
