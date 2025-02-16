package com.spring.bookstore.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.spring.bookstore.dto.BookDataDto;
import com.spring.bookstore.dto.BookDto;
import com.spring.bookstore.entity.Book;
import com.spring.bookstore.mapper.BookMapper;
import com.spring.bookstore.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookService {

    private BookRepository bookRepository;
    private Cloudinary cloudinary;
    private BookMapper bookMapper;
    private ModelMapper modelMapper;
    private final Map param = ObjectUtils.asMap(
            "unique_filename", false,
            "overwrite", true
    );


    public String upload(MultipartFile file) throws IOException {
        String fileNameWithExtension = file.getOriginalFilename();
        String fileNameWithoutExtension = fileNameWithExtension.substring(0, fileNameWithExtension.lastIndexOf('.'));

        this.param.put("public_id", fileNameWithoutExtension);
        Map upload = this.cloudinary.uploader().upload(file.getBytes(), this.param);
        return (String) upload.get("url");
    }

    public Book getBookById(int bookId) {
        return this.bookRepository.findById(bookId).orElseThrow(
                () -> new EntityNotFoundException("The book with id " + bookId + " not found!")
        );
    }

    public Page<BookDataDto> getBooksByPagingAndSorting(int page, int size, String sortBy, String direction) {
        Sort sort = Sort.unsorted();
        if(!sortBy.isEmpty() && !direction.isEmpty()) sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return this.bookRepository.findAll(pageable)
                .map((book) -> this.modelMapper.map(book, BookDataDto.class));
    }

    public Page<BookDataDto> searchBooksByTitlePagingAndSorting(int page, int size, String sortBy, String direction, String title) {
        Sort sort = Sort.unsorted();
        if(!sortBy.isEmpty() && !direction.isEmpty()) sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return this.bookRepository.findByTitleContainingIgnoreCase(title, pageable)
                .map((book) -> this.modelMapper.map(book, BookDataDto.class));
    }

    public List<BookDataDto> getAllBooksOnly() {
        return this.bookRepository.findAllBooksOnly()
                .stream()
                .map(((book) -> this.modelMapper.map(book, BookDataDto.class)))
                .toList();
    }

    public List<Book> getAllBooks() {
        return this.bookRepository.findAll();
    }

    public Book createBook(BookDto bookDto) throws IOException {
        boolean existBook = this.bookRepository.existsByTitle(bookDto.getTitle());
        if(existBook) {
            throw new IllegalArgumentException("The book's title already exists!");
        }
        MultipartFile file = bookDto.getMultipartFile();

        String imageURL = this.upload(file);

        Book book = this.bookMapper.convertToBook(bookDto, imageURL);
        return this.bookRepository.save(book);
    }

    public Book updateBook(int bookId, BookDto bookDto) throws IOException {
        Book bookById = this.bookRepository.findById(bookId).get();
        Book bookByTitle = this.bookRepository.findByTitle(bookDto.getTitle()).orElse(null);

        if(bookByTitle != null && bookById.getBookId() != bookByTitle.getBookId()) {
            throw new DataIntegrityViolationException("The book's title already exists!");
        }

        MultipartFile file = bookDto.getMultipartFile();

        String imageURL = this.upload(file);

        Book updatedBook = this.bookMapper.convertToBook(bookDto, imageURL);
        updatedBook.setBookId(bookId);

        return this.bookRepository.save(updatedBook);
    }

    public void deleteBook(int bookId) {
        this.bookRepository.findById(bookId).orElseThrow(
                () -> new EntityNotFoundException("The book with id " + bookId + " not found")
        );
        this.bookRepository.deleteById(bookId);
    }


}
