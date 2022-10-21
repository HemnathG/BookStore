package com.bridgelabz.bs.book.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class BookDto {
	@Pattern(regexp = "^[A-Za-z0-9\\s]+$", message = "Book Name invalid")
    public String bookName;
    @Pattern(regexp = "^[A-Za-z.\\s]+$", message = "Author name invalid")
    public String authorName;
    @NotBlank(message = "Book description should not be empty")
    public String bookDescription;
    @NotBlank(message = "Book image should not be empty")
    public String bookImage;
    @Min(value = 20, message = "Price should minimum 20")
    public double price;
    @Min(value = 1, message = "Quantity should be at least 1")
    public int quantity;
}
