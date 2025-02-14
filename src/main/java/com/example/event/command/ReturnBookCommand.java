package com.example.event.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 도서 반납 요청
 */
@Getter
@AllArgsConstructor
public class ReturnBookCommand {
    private String bookId;
}
