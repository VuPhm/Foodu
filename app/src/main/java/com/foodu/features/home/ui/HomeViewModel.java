package com.foodu.features.home.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.foodu.features.home.data.ProductRepository;
import com.foodu.features.home.data.model.Product;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class HomeViewModel extends ViewModel {
    private final ProductRepository repo;
    private final MutableLiveData<List<Product>> products = new MutableLiveData<>();

    @Inject
    public HomeViewModel(ProductRepository repo) {
        this.repo = repo;
        loadProducts();
    }

    private void loadProducts() {
        repo.getProducts().observeForever(products::setValue); // hoặc gọi repo.getProducts() là LiveData luôn
    }

    public LiveData<List<Product>> getProducts() { return products; }
}
