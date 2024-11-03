package com.smsfinance.controller;

import com.smsfinance.dto.BookDTO;
import com.smsfinance.model.Book;
import com.smsfinance.service.BookService;
import com.smsfinance.util.exception.BookNotValidException;
import com.smsfinance.util.validator.BookValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
@Tag(name = "Библиотека")
public class BookController {
    private final BookService bookService;
    private final ModelMapper mapper;
    private final BookValidator bookValidator;

    private static final String NOT_VALID = "Поля не должны быть пустыми и должны иметь верные значения!";

    @Autowired
    public BookController(BookService bookService, ModelMapper mapper, BookValidator bookValidator) {
        this.bookService = bookService;
        this.mapper = mapper;
        this.bookValidator = bookValidator;
    }

    @Operation(summary = "Получить весь список книг")
    @GetMapping
    public List<BookDTO> getBooks() {
        return bookService.findAllBooks()
                .stream()
                .map(this::convertToBookDTO)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Найти книгу по ID")
    @GetMapping("/{id}")
    public BookDTO getBook(@PathVariable("id") int id) {
        return convertToBookDTO(bookService.findById(id));
    }

    @Operation(summary = "Добавить новую книгу")
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addBook(@RequestBody @Valid BookDTO bookDTO,
                                              BindingResult bindingResult) {
        Book book = convertToBook(bookDTO);

        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new BookNotValidException(NOT_VALID);
        }
        bookService.saveBook(book);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "Изменить информацию о книге")
    @PostMapping("/change/{id}")
    public ResponseEntity<HttpStatus> changeBook(@PathVariable int id,
                                                 @RequestBody @Valid BookDTO bookDTO,
                                                 BindingResult bindingResult) {
        Book book = convertToBook(bookDTO);

        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new BookNotValidException(NOT_VALID);
        }

        bookService.updateBook(id, book);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "Удалить книгу по ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private BookDTO convertToBookDTO(Book book) {
        return mapper.map(book, BookDTO.class);
    }

    private Book convertToBook(BookDTO bookDTO) {
        return mapper.map(bookDTO, Book.class);
    }
}
