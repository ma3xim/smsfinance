package com.smsfinance.util.validator;

import com.smsfinance.model.Book;
import com.smsfinance.util.exception.BookNotValidException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class BookValidator implements Validator {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private final String DATE_PARSE_EXCEPTION = "Неверный формат даты! Необходим yyyy-MM-dd";

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        sdf.setLenient(false);

        //TODO улучшить валидацию даты
        try {
            sdf.parse(sdf.format(book.getPublishedDate()));
        } catch (ParseException e) {
            throw new BookNotValidException(DATE_PARSE_EXCEPTION);
        }

    }
}
