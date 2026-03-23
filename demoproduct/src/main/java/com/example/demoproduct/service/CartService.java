package com.example.demoproduct.service;

import com.example.demoproduct.model.CartItem;
import com.example.demoproduct.model.Order;
import com.example.demoproduct.model.OrderDetail;
import com.example.demoproduct.model.Product;
import com.example.demoproduct.repository.OrderRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    private static final String CART_SESSION_KEY = "cart";

    // 1. Lấy giỏ hàng từ Session
    @SuppressWarnings("unchecked")
    public List<CartItem> getCartItems(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute(CART_SESSION_KEY);
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute(CART_SESSION_KEY, cart);
        }
        return cart;
    }

    // 2. Thêm vào giỏ hàng (lưu vào session)
    public void addToCart(Long productId, int quantity, HttpSession session) {
        List<CartItem> cart = getCartItems(session);
        Product product = productService.getProductById(productId);

        for (CartItem item : cart) {
            if (item.getProductId().equals(productId)) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        cart.add(new CartItem(product.getId(), product.getName(), product.getPrice(), quantity, product.getImage()));
    }

    // 3. Logic Checkout theo yêu cầu đề bài
    public Order checkout(HttpSession session) {
        List<CartItem> cartItems = getCartItems(session);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Giỏ hàng đang trống");
        }

        Order order = new Order();
        order.setPaid(false); // Chưa thanh toán

        long totalAmount = 0;
        List<OrderDetail> orderDetails = new ArrayList<>();

        for (CartItem item : cartItems) {
            OrderDetail detail = new OrderDetail();
            detail.setProduct(productService.getProductById(item.getProductId()));
            detail.setPrice(item.getPrice());
            detail.setQuantity(item.getQuantity());
            detail.setOrder(order); // Map ngược lại Order

            orderDetails.add(detail);
            totalAmount += item.getPrice() * item.getQuantity();
        }

        order.setTotalAmount(totalAmount);
        order.setOrderDetails(orderDetails);

        // Lưu Order vào database (sẽ tự động lưu cả OrderDetail nhờ CascadeType.ALL)
        Order savedOrder = orderRepository.save(order);

        // Xóa session giỏ hàng
        session.removeAttribute(CART_SESSION_KEY);

        return savedOrder;
    }
}
