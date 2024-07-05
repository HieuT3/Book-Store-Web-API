package com.spring.bookstore.service;

import com.spring.bookstore.dto.CustomerProfileDto;
import com.spring.bookstore.dto.ReviewRequestDto;
import com.spring.bookstore.entity.Book;
import com.spring.bookstore.entity.Customer;
import com.spring.bookstore.entity.Review;
import com.spring.bookstore.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@ToString
public class ReviewService {

    private ReviewRepository reviewRepository;
    private UserService userService;
    private BookService bookService;
    private CustomerService customerService;
    private ModelMapper modelMapper;

    public Review getReviewById(int reviewId) {
        return this.reviewRepository.findById(reviewId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Review with id " + reviewId + " not found!")
                );
    }

    public List<Review> getAllReviews() {
        return this.reviewRepository.findAll();
    }

    public Review addReview(ReviewRequestDto review) {
        int userId = this.userService.getUserId();
        CustomerProfileDto customerProfileDto = this.customerService.getCustomerById(userId);
        Customer customer = this.modelMapper.map(customerProfileDto, Customer.class);
        Book book = this.bookService.getBookById(review.getBook().getBookId());
        Review newReview = new Review();
        newReview.setBook(book);
        newReview.setCustomer(customer);
        newReview.setComment(review.getComment());
        return this.reviewRepository.save(newReview);
    }

    public Review updateReview(int reviewId, ReviewRequestDto review) {
        Review existedReview = this.reviewRepository.findById(reviewId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Review with id " + reviewId + " not found!")
                );
        existedReview.setComment(review.getComment());
        return this.reviewRepository.save(existedReview);
    }

    public void deleteReview(int reviewId) {
        this.reviewRepository.findById(reviewId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Review with id " + reviewId + " not found!")
                );
        this.reviewRepository.deleteById(reviewId);
    }
}
