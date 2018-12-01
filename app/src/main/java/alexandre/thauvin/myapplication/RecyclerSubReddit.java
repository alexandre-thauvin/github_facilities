package alexandre.thauvin.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class RequestSubReddit extends RecyclerView.Adapter<RequestSubReddit.ViewHolder> {

    private final List<Post> mValues;
    private final PostsFragment.OnListFragmentInteractionListener mListener;

    public RequestSubReddit(List<Post> items, PostsFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTitle.setText(mValues.get(position).getTitle());
        holder.mProfessor.setText(mValues.get(position).getProfessor());
        holder.mHour.setText(mValues.get(position).getHour());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitle;
        public final TextView mProfessor;
        public final TextView mHour;
        public Post mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mHour = view.findViewById(R.id.hour);
            mTitle =  view.findViewById(R.id.title);
            mProfessor =  view.findViewById(R.id.professor);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mProfessor.getText() + "'";
        }
    }
}
