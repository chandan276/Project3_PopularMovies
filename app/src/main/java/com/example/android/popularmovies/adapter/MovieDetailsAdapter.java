package com.example.android.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.model.MovieReviewsData;
import com.example.android.popularmovies.model.MovieVideosData;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int mNumberItems;

    private List<MovieVideosData> movieVideosData = new ArrayList<>();
    private List<MovieReviewsData> movieReviewsData = new ArrayList<>();

    final private MovieTrailerClickListener mOnClickListener;

    public interface MovieTrailerClickListener {
        void onTrailerClick(int clickedItemIndex);
    }

    public MovieDetailsAdapter(List<MovieVideosData> movieVideosData, List<MovieReviewsData> movieReviewsData, MovieTrailerClickListener mOnClickListener) {
        this.mNumberItems = movieVideosData.size() + movieReviewsData.size();
        this.movieVideosData = movieVideosData;
        this.movieReviewsData = movieReviewsData;
        this.mOnClickListener = mOnClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < movieVideosData.size()) {
            return 0;
        }

        return 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        switch (viewType) {
            case 0: {
                int layoutIdForListItem = R.layout.movie_trailers_list;
                View view = inflater.inflate(layoutIdForListItem, parent, false);
                return new MovieDetailsAdapter.TrailersViewHolder(view);
            }

            case 1: {
                int layoutIdForListItem = R.layout.movie_reviews_list;
                View view = inflater.inflate(layoutIdForListItem, parent, false);
                return new MovieDetailsAdapter.ReviewsViewHolder(view);
            }

                default:break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0:
                TrailersViewHolder trailersViewHolder = (TrailersViewHolder)holder;
                MovieVideosData videosData = movieVideosData.get(position);
                trailersViewHolder.trailerTextView.setText(videosData.getMovieTrailerName());
                break;

            case 1:
                ReviewsViewHolder reviewsViewHolder = (ReviewsViewHolder)holder;
                MovieReviewsData reviewsData = movieReviewsData.get(position - movieVideosData.size());
                reviewsViewHolder.reviewerNameTextView.setText(reviewsData.getMovieReviewAuthor());
                reviewsViewHolder.reviewTextView.setText(reviewsData.getMovieReviewContent());
                break;

                default:break;
        }
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    class TrailersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView trailerTextView;

        TrailersViewHolder(View itemView) {
            super(itemView);
            trailerTextView = (TextView) itemView.findViewById(R.id.movie_trailer_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onTrailerClick(clickedPosition);
        }
    }

    class ReviewsViewHolder extends RecyclerView.ViewHolder {

        TextView reviewerNameTextView;
        TextView reviewTextView;

        ReviewsViewHolder(View itemView) {
            super(itemView);
            reviewerNameTextView = (TextView) itemView.findViewById(R.id.movie_reviewer_name);
            reviewTextView = (TextView) itemView.findViewById(R.id.movie_review);
        }
    }
}
