package com.example.event.projection;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 이벤트 저장소는 Append-Only 방식이라 현재 상태를 바로 알 수 없다.
 * Projection을 통해 현재 도서의 상태(대여 중인지 등)를 쉽게 조회할 수 있도록 별도 테이블에 저장.
 * JPA 엔티티로 관리되며, BookRepository를 통해 조회 가능.
 */
@Entity
@Getter
@NoArgsConstructor
public class BookProjection {

    @Id
    private String bookId;
    private String title;
    private boolean isBorrowed;

    public BookProjection(String bookId, String title) {
        this.bookId = bookId;
        this.title = title;
        this.isBorrowed = false;
    }

    public void borrowBook() {
        this.isBorrowed = true;
    }

    public void returnBook() {
        this.isBorrowed = false;
    }
}
