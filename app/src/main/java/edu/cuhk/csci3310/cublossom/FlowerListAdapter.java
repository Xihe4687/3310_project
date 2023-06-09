package edu.cuhk.csci3310.cublossom;

// TODO:
// Include your personal particular here
//

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

public class FlowerListAdapter extends Adapter<FlowerListAdapter.FlowerViewHolder>  {
    private Context context;
    private LayoutInflater mInflater;

    private final ArrayList<String> mImagePathList;
    private final ArrayList<Canteen> mCanteenList;
    public static String extraImagePath = null;
    public static String extraCanteenName = null;
    public static String extraOpeningTime = null;
    public static int extraRating = 0;


    class FlowerViewHolder extends RecyclerView.ViewHolder {

        ImageView canteenImageItemView;
        RatingBar flowerRichnessBar;

        final FlowerListAdapter mAdapter;

        public FlowerViewHolder(View itemView, FlowerListAdapter adapter) {
            super(itemView);
            canteenImageItemView = itemView.findViewById(R.id.image);
            flowerRichnessBar = itemView.findViewById(R.id.ratingBar);
            this.mAdapter = adapter;

            // Event handling registration, page navigation goes here
            // Event handling registration, page navigation goes here
            canteenImageItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get the position of the item that was clicked.
                    int position = getLayoutPosition();
                    Toast t = Toast.makeText(v.getContext(), "Position " + position + " is clicked", Toast.LENGTH_SHORT);
                    t.show();
                    Intent intent = new Intent(context, DetailActivity.class);
                    extraImagePath = mImagePathList.get(position);
                    extraCanteenName = mCanteenList.get(position).getCanteenName();
                    extraOpeningTime = mCanteenList.get(position).getOpeningTime();
                    extraRating = mCanteenList.get(position).getRating();
                    intent.putExtra("imagePath", extraImagePath);
                    intent.putExtra("canteenName", extraCanteenName);
                    intent.putExtra("openingTime", extraOpeningTime);
                    intent.putExtra("rating", extraRating);
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });

            // End of ViewHolder initialization
        }
    }

    public FlowerListAdapter(Context context,
                            ArrayList<String> imagePathList, ArrayList<Canteen> canteenList) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.mImagePathList = imagePathList;
        this.mCanteenList = canteenList;
    }

    @NonNull
    @Override
    public FlowerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.imagelist_item, parent, false);
        return new FlowerViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull FlowerViewHolder holder, int position) {
        String mImagePath = mImagePathList.get(position);
        int rate = mCanteenList.get(position).getRating();
        Uri uri = Uri.parse(mImagePath);
        // Update the following to display correct information based on the given position


        // Set up View items for this row (position), modify to show correct information read from the CSV
        holder.canteenImageItemView.setImageURI(null);
        holder.canteenImageItemView.setImageURI(uri);
        holder.flowerRichnessBar.setRating(rate);

    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mImagePathList.size();
    }

}
