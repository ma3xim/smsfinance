package com.smsfinance.service;

import com.smsfinance.model.Book;
import com.smsfinance.rabbitMq.BookEventProducer;
import com.smsfinance.repository.BookRepository;
import com.smsfinance.util.exception.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final BookEventProducer bookEventProducer;
    private static final String BOOK_NOT_FOUND = "Книга с таким id не найдена!";

    @Autowired
    public BookService(BookRepository bookRepository, BookEventProducer bookEventProducer) {
        this.bookRepository = bookRepository;
        this.bookEventProducer = bookEventProducer;
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Book findById(int id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.orElseThrow(() -> new BookNotFoundException(BOOK_NOT_FOUND));
    }

    @Transactional
    public void saveBook(Book book) {
        bookRepository.save(book);
        bookEventProducer.sendBookEvent("Книга с названием " + book.getTitle() + " была добавлена. timestamp: ");
    }

    @Transactional
    public void updateBook(int id, Book updatedBook) {
        Book existingBook = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(BOOK_NOT_FOUND));

        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setPublishedDate(updatedBook.getPublishedDate());

        bookRepository.save(existingBook);
        bookEventProducer.sendBookEvent("Книга с ID " + id + " была обновлена. timestamp: ");
    }

    @Transactional
    public void deleteBook(int id) throws BookNotFoundException {
        if (bookRepository.findById(id).isEmpty()) {
            throw new BookNotFoundException(BOOK_NOT_FOUND);
        } else {
            bookRepository.deleteById(id);
            bookEventProducer.sendBookEvent("Книга с ID " + id + " была удалена. timestamp: ");
        }
    }
}
