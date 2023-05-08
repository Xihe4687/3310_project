package edu.cuhk.csci3310.cublossom;

// TODO:
// Include your personal particular here
//

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

public class FlowerListAdapter extends Adapter<FlowerListAdapter.FlowerViewHolder>  {
    private Context context;
    private LayoutInflater mInflater;

    private final ArrayList<String> mImagePathList;
    private final ArrayList<Flower> mFlowerList;
    public static String extraImagePath = null;
    public static String extraFlowerName = null;
    public static String extraGenus = null;
    public static int extraRichness = 0;


    class FlowerViewHolder extends RecyclerView.ViewHolder {

        ImageView flowerImageItemView;
        RatingBar flowerRichnessBar;

        final FlowerListAdapter mAdapter;

        public FlowerViewHolder(View itemView, FlowerListAdapter adapter) {
            super(itemView);
            flowerImageItemView = itemView.findViewById(R.id.image);
            flowerRichnessBar = itemView.findViewById(R.id.flowerBar);
            this.mAdapter = adapter;

            // Event handling registration, page navigation goes here
            // Event handling registration, page navigation goes here
            flowerImageItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get the position of the item that was clicked.
                    int position = getLayoutPosition();
                    Toast t = Toast.makeText(v.getContext(), "Position " + position + " is clicked", Toast.LENGTH_SHORT);
                    t.show();
                    Intent intent = new Intent(context, DetailActivity.class);
                    extraImagePath = mImagePathList.get(position);
                    extraFlowerName = mFlowerList.get(position).getFlowerName();
                    extraGenus = mFlowerList.get(position).getGenus();
                    extraRichness = mFlowerList.get(position).getRichness();
                    intent.putExtra("imagePath", extraImagePath);
                    intent.putExtra("flowerName", extraFlowerName);
                    intent.putExtra("genus", extraGenus);
                    intent.putExtra("richness", extraRichness);
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });

            // End of ViewHolder initialization
        }
    }

    public FlowerListAdapter(Context context,
                            ArrayList<String> imagePathList, ArrayList<Flower> flowerList) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.mImagePathList = imagePathList;
        this.mFlowerList = flowerList;
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
        int rate = mFlowerList.get(position).getRichness();
        Uri uri = Uri.parse(mImagePath);
        // Update the following to display correct information based on the given position


        // Set up View items for this row (position), modify to show correct information read from the CSV
        holder.flowerImageItemView.setImageURI(null);
        holder.flowerImageItemView.setImageURI(uri);
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
