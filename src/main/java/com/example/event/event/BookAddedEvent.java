package com.example.event.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 새 도서 추가
 */
@Getter
@AllArgsConstructor
public class BookAddedEvent {
    private String bookId;
    private String title;
}
