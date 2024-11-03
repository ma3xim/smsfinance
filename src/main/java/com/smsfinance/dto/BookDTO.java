package com.smsfinance.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class BookDTO {
    @Schema(description = "Название книги")
    @NotEmpty
    private String title;

    @Schema(description = "Автор книги")
    @NotEmpty
    private String author;

    @Schema(description = "Дата публикации книги")
    @NotNull
    private Date publishedDate;

}
