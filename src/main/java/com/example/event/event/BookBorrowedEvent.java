package com.example.event.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 도서 대여
 *
 * Event Sourcing에서 가장 중요한 부분
 * 시스템에서 발생하는 모든 변경 사항을 이벤트 객체로 저장.
 * BookBorrowedEvent, BookReturnedEvent 같은 클래스들이 여기에 포함됨.
 */
@Getter
@AllArgsConstructor
public class BookBorrowedEvent {
    private String bookId;
    private String userId;
}
