package com.example.event.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 도서 반납
 */
@Getter
@AllArgsConstructor
public class BookReturnedEvent {
    private String bookId;
}
