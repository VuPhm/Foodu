package com.foodu.features.home.data;

import com.foodu.R;
import com.foodu.features.home.data.model.FoodCategory;
import com.foodu.features.home.data.model.FoodItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class HomeRepository {
    @Inject
    public HomeRepository() {}

    // Fake categories
    public List<FoodCategory> getCategories() {
        List<FoodCategory> list = new ArrayList<>();
        list.add(new FoodCategory("cat1", "Pizza", R.drawable.ic_pizza));
        list.add(new FoodCategory("cat2", "Sushi", R.drawable.ic_sushi));
        list.add(new FoodCategory("cat3", "Burger", R.drawable.ic_burger));
        list.add(new FoodCategory("cat4", "Drinks", R.drawable.ic_drinks));
        return list;
    }

    // Fake popular items
    public List<FoodItem> getPopularFoods() {
        List<FoodItem> list = new ArrayList<>();
        list.add(new FoodItem("food1", "Margherita Pizza", "", 7.99));
        list.add(new FoodItem("food2", "Classic Burger", "", 6.49));
        list.add(new FoodItem("food3", "Salmon Sushi", "", 9.99));
        return list;
    }
}
