package fpoly.truongtqph41980.petshop.Model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class viewmd extends ViewModel {
    private MutableLiveData<ArrayList<SanPham>> cartItems = new MutableLiveData<>();

    public LiveData<ArrayList<SanPham>> getCartItems() {
        if (cartItems.getValue() == null) {
            cartItems.setValue(new ArrayList<>());
        }
        return cartItems;
    }

    public void addToCart(SanPham sanPham) {
        ArrayList<SanPham> currentItems = cartItems.getValue();
        if (currentItems != null) {
            currentItems.add(sanPham);
            cartItems.setValue(currentItems);
        }
    }
}
