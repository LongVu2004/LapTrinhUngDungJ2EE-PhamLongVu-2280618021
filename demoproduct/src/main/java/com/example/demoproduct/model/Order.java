package com.example.demoproduct.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "orders") // "order" là từ khóa gốc trong SQL nên đặt tên table là orders
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isPaid;

    private long totalAmount;

    // Giả sử bạn chưa làm login thì tạm thời để null hoặc String
    private String account;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;
}
