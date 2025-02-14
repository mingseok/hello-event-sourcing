package com.example.event.aggregate;

import com.example.event.event.BookAddedEvent;
import com.example.event.event.BookBorrowedEvent;
import com.example.event.event.BookReturnedEvent;
import lombok.Getter;

/**
 * 한 개체(도서)가 가지는 상태를 관리하는 핵심 단위.
 * 이벤트를 기반으로 현재 상태를 결정하는 역할.
 * BookAggregate 클래스에서 이벤트를 반영(apply)하여 상태를 변경.
 */
@Getter
public class BookAggregate {
    private String bookId;
    private String title;
    private boolean isBorrowed;

    public BookAggregate(BookAddedEvent event) {
        this.bookId = event.getBookId();
        this.title = event.getTitle();
        this.isBorrowed = false;
    }

    public void apply(BookBorrowedEvent event) {
        this.isBorrowed = true;

        // 사용하지 않는 event을 가져오는 이유
        // this.borrowedAt = event.getTimestamp(); 이벤트의 발생 시간을 저장하기 위해서도
    }

    public void apply(BookReturnedEvent event) {
        this.isBorrowed = false;
    }
}
