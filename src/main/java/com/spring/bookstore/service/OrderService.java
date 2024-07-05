package com.spring.bookstore.service;

import com.spring.bookstore.dto.CustomerProfileDto;
import com.spring.bookstore.dto.OrderRequestDto;
import com.spring.bookstore.entity.*;
import com.spring.bookstore.repository.BookRepository;
import com.spring.bookstore.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;
    private UserService userService;
    private CustomerService customerService;
    private CartService cartService;
    private BookRepository bookRepository;
    private ModelMapper modelMapper;

    public Order placeOrder(OrderRequestDto orderRequestDto) {
        int userId = this.userService.getUserId();
        CustomerProfileDto customerProfileDto = this.customerService.getCustomerById(userId);
        Customer customer = this.modelMapper.map(customerProfileDto, Customer.class);
        Map<Integer, Integer> items = this.cartService.getItems();

        Order order = new Order();
        order.setCustomer(customer);
        order.setShippingAddress(orderRequestDto.getShippingAddress());
        order.setRecipientName(orderRequestDto.getRecipientName());
        order.setRecipientPhone(orderRequestDto.getRecipientPhone());
        order.setPaymentMethod(orderRequestDto.getPaymentMethod());
        order.setShippingCost(orderRequestDto.getShippingCost());
        order.setBookCopies(this.cartService.getTotalQuantities());

        Set<OrderDetail> orderDetails = new HashSet<>();
        items.forEach((key, value) -> {
            Book book = this.bookRepository.findById(key).get();

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrders(order);
            orderDetail.setBook(book);
            orderDetail.setQuantity(value);
            orderDetail.setSubTotal(book.getPrice() * value);

            orderDetails.add(orderDetail);
        });

        order.setTotal(this.cartService.getTotalAmount());
        order.setStatus(OrderStatusEnum.PENDING);
        order.setOrderDetails(orderDetails);

        this.cartService.removeCart();

        return this.orderRepository.save(order);
    }

    public Order getOrderById(int orderId) {
        return this.orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Order with id " + orderId + " not found!")
        );
    }

    public List<Order> getAllOrders() {
        return this.orderRepository.findAll();
    }

    public void deleteOrder(int orderId) {
        this.orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Order with id " + orderId + " not found!")
        );
        this.orderRepository.deleteById(orderId);
    }

    public List<Order> getAllOrderByCustomer() {
        int userId = this.userService.getUserId();
        CustomerProfileDto customerProfileDto = this.customerService.getCustomerById(userId);
        Customer customer = this.modelMapper.map(customerProfileDto, Customer.class);
        return this.orderRepository.findAllByCustomer(customer);
    }

    public Order updateOrderStatus(int orderId, OrderStatusEnum status) {
        Order order = this.orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Order with id " + orderId + " not found!")
        );
        order.setStatus(status);
        return this.orderRepository.save(order);
    }
}
