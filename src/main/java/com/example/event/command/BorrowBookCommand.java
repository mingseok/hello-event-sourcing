package com.example.event.command;

import lombok.Getter;

/**
 * 사용자가 요청하는 동작 (ex. "책을 빌린다", "책을 반납한다")를 나타내는 객체
 * BorrowBookCommand, ReturnBookCommand 같은 클래스들이 포함됨.
 */
@Getter
public class BorrowBookCommand {
    private String bookId;
    private String userId;
}
