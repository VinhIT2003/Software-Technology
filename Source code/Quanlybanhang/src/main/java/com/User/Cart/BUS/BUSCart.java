
package com.User.Cart.BUS;
import com.User.Cart.DTO.DTOCart;
import com.User.Cart.DAO.DAOCart;
import java.util.ArrayList;

public class BUSCart {
    private DAOCart daoCart = new DAOCart();
    
    public boolean addToCart(DTOCart cartItem) {
        // Kiểm tra số lượng hợp lệ
        if (cartItem.getQuantity() <= 0) {
            return false;
        }
        
        // Kiểm tra sản phẩm đã có trong giỏ chưa
        if (daoCart.isProductInCart(cartItem.getCustomerID(), cartItem.getProductID())) {
            return daoCart.updateCartItem(cartItem);
        } else {
            return daoCart.addToCart(cartItem);
        }
    }
    
    public ArrayList<DTOCart> getCartItemsByCustomer(String customerID) {
        return daoCart.getCartItemsByCustomer(customerID);
    }
    
    public boolean removeFromCart(String customerId, String productId) {
        return daoCart.deleteCartItem(customerId, productId);
    }
    
    public boolean clearCart(String customerID) {
        return daoCart.clearCart(customerID);
    }
}