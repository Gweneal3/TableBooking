package wia2007.project.tablebooking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import wia2007.project.tablebooking.dao.RestaurantDAO;
import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.Cuisine;
import wia2007.project.tablebooking.entity.Restaurant;

public class MainMenuFragment extends Fragment {

    private Button menuTypeAll;
    private Button menuTypeCategory;
    private ViewGroup viewGroupAll;
    private ViewGroup viewGroupCategory;

    public MainMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        /** ALL RESTAURANT **/
        Context context = requireActivity();
        // get all restaurant
        TableBookingDatabase database = TableBookingDatabase.getDatabase(context);
        RestaurantDAO dao = database.restaurantDAO();
        //allList = dao.listAllRestaurantInfo();
        // TODO: temporary list
        ArrayList<RestaurantDAO.RestaurantNameInfo> allList = new ArrayList<>();
        allList.add(new RestaurantDAO.RestaurantNameInfo(1,"Atmosphere 360", 1, "https://formfacade.com/itemembed/1FAIpQLSdrvYn-kBK2Afy94R__AdiF3G7PpxTv7tISQFkk-EvCryANTw/item/1151574515/image/1pUZ6fLyXnch5NUqZtwoFGuiXtG9tJRuMw4GHplxLJoeZPWU"));
        allList.add(new RestaurantDAO.RestaurantNameInfo(2,"Cons Transphere", 2, ""));
        allList.add(new RestaurantDAO.RestaurantNameInfo(3,"KFC Malaysia", 1, "https://play-lh.googleusercontent.com/MQDfTBh4VBrD4MQt5hX4b26OnGb9l57_pBWaBFw-mvfrfwOY9aHcwgF2mtDKvE0W-Bw=w240-h480-rw"));
        allList.add(new RestaurantDAO.RestaurantNameInfo(4,"Malaysia Cuisine", 3, ""));
        allList.add(new RestaurantDAO.RestaurantNameInfo(5,"Domino's 360", 7, ""));

        // get recycler view and bind view holder
        RecyclerView recyclerView = view.findViewById(R.id.main_menuListAllRV);
        // set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        // set adapter
        MainMenuRestaurantAdapter arrayAdapter = new MainMenuRestaurantAdapter(context, allList);
        recyclerView.setAdapter(arrayAdapter);

        /** CATEGORY **/
        // listener for category section
        View.OnClickListener categoryOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.setAction(Intent.ACTION_ASSIST);
                intent.putExtra("cuisineType", (Integer) v.getTag());
                startActivity(intent);
            }
        };
        // fill in by category section
        GridLayout categoryGridLayout = (GridLayout) view.findViewById(R.id.main_menuListCategoryLayout);
        for (int i=0; i < Cuisine.getSize(); i++){
            //get layout file and inflate it
            View temp = inflater.inflate(R.layout.individual_main_category_item, categoryGridLayout, false);
            //get each cuisine item
            Cuisine.CuisineItem item = Cuisine.getCuisineItem(i);
            //get text, set text
            TextView temp_cuisineName = temp.findViewById(R.id.main_category_name);
            temp_cuisineName.setText(item.name);
            //get image, set image
            ImageView temp_categoryIcon = temp.findViewById(R.id.main_category_picture);
            temp_categoryIcon.setImageResource(item.iconResource);
            //add item to grid
            categoryGridLayout.addView(temp);
            //set tag for onClickListener
            temp.setTag(i+1);
            temp.setOnClickListener(categoryOnClickListener);
        }

        /** Horizontal scroll **/
        // get the buttons and views
        this.menuTypeAll = view.findViewById(R.id.main_BtnAllCuisine);
        this.menuTypeCategory = view.findViewById(R.id.main_BtnByCategory);
        this.viewGroupAll = view.findViewById(R.id.main_menuListAll);
        this.viewGroupCategory = view.findViewById(R.id.main_menuListCategory);

        //bind onclick to button
        menuTypeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewGroupAll.setVisibility(View.VISIBLE);
                viewGroupCategory.setVisibility(View.GONE);
            }
        });
        menuTypeCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewGroupCategory.setVisibility(View.VISIBLE);
                viewGroupAll.setVisibility(View.GONE);
            }
        });

        /** Search bar **/
        //get search bar
        View searchButton = view.findViewById(R.id.main_searchView);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}