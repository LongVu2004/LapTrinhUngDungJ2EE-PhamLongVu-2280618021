package com.example.demoproduct.controller;

import java.util.List;

import com.example.demoproduct.model.Order;
import com.example.demoproduct.service.CartService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demoproduct.model.CartItem;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // Xem giỏ hàng
    @GetMapping
    public String viewCart(Model model, HttpSession session) {
        model.addAttribute("cartItems", cartService.getCartItems(session));
        return "product/cart";
    }

    // Thêm vào giỏ
    @PostMapping("/add")
    @ResponseBody // Annotation này báo cho Spring biết ta sẽ trả về dữ liệu (JSON/Text) chứ không phải HTML
    public ResponseEntity<Integer> addToCart(@RequestParam Long productId, @RequestParam(defaultValue = "1") int quantity, HttpSession session) {
        cartService.addToCart(productId, quantity, session);

        // Tính tổng số lượng tất cả sản phẩm đang có trong giỏ hàng
        List<CartItem> cartItems = cartService.getCartItems(session);
        int totalQuantity = 0;
        for (CartItem item : cartItems) {
            totalQuantity += item.getQuantity();
        }

        return ResponseEntity.ok(totalQuantity); // Trả về con số để JavaScript cập nhật
    }

    // Thực hiện action checkout
    @PostMapping("/checkout")
    public String checkout(HttpSession session, Model model) {
        try {
            Order order = cartService.checkout(session);
            model.addAttribute("order", order);
            return "product/checkout-success"; // Chuyển đến trang thành công
        } catch (RuntimeException e) {
            return "redirect:/cart?error=" + e.getMessage();
        }
    }
}
