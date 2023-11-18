package fpoly.truongtqph41980.petshop.Interface;

import fpoly.truongtqph41980.petshop.Model.SanPham;

public interface OnAddToCartListener {
    void onAddToCart(SanPham sanPham);
    void onDataAddedToCart(SanPham sanPham);
}
