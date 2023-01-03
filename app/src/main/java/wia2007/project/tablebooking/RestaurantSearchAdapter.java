package wia2007.project.tablebooking;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import wia2007.project.tablebooking.dao.RestaurantDAO;

public class RestaurantSearchAdapter extends RecyclerView.Adapter<RestaurantSearchHolder> {

    private Context context;
    private ArrayList<RestaurantDAO.RestaurantNameInfoPair> pairList;
    private final RecyclerView recyclerView;

    public RestaurantSearchAdapter(Context context, RecyclerView recyclerView, List<RestaurantDAO.RestaurantNameInfoPair> pairList){
        this.context = context;
        this.recyclerView = recyclerView;
        this.pairList = new ArrayList<>(pairList);
    }

    @NonNull
    @Override
    public RestaurantSearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(
                android.R.layout.simple_list_item_1,
                parent,
                false
        );
        RestaurantSearchHolder holder = new RestaurantSearchHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = recyclerView.getChildAdapterPosition(v);
                Intent restaurantMenuIntent = new Intent(recyclerView.getContext(), RestaurantMainActivity.class);
                restaurantMenuIntent.putExtra("ID", pairList.get(position).id);
                restaurantMenuIntent.putExtra("name", pairList.get(position).name);
                restaurantMenuIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(restaurantMenuIntent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantSearchHolder holder, int position) {
        TextView TVitem = (TextView) holder.itemView;
        String restaurantName = pairList.get(position).name;
        TVitem.setText(restaurantName);
    }

    @Override
    public int getItemCount() {
        return pairList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<RestaurantDAO.RestaurantNameInfoPair> newList){
        this.pairList = new ArrayList<>(newList);
        this.notifyDataSetChanged();
    }

}

// View Holder
class RestaurantSearchHolder extends RecyclerView.ViewHolder {
    public RestaurantSearchHolder(@NonNull View itemView) {
        super(itemView);
    }
}