package com.bridgelabz.bs.book.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class CartDto {
	@Min(value = 1, message = "quantity should be at least 1")
    public int quantity;
    @NotNull(message = "Book id should not be empty")
    public int bookId;
}
