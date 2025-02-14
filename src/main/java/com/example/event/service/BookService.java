package com.example.event.service;

import com.example.event.aggregate.BookAggregate;
import com.example.event.command.BorrowBookCommand;
import com.example.event.event.BookAddedEvent;
import com.example.event.event.BookBorrowedEvent;
import com.example.event.projection.BookProjection;
import com.example.event.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BookService {

    // 빠르게 조회하기 위한 용도
    private final BookRepository bookRepository;

    private final Map<String, BookAggregate> eventStore = new HashMap<>();

    // 신규 도서 추가
    public void addBook(String bookId, String title) {

        //"책이 추가됨"을 이벤트 객체인 'BookAddedEvent'로 기록한다.
        // - 나중에 재사용되거나, 이력을 조회할 때 활용
        BookAddedEvent event = new BookAddedEvent(bookId, title);

        // 책의 상태(언제 빌렸는지, 언제 반납되었는지 등)를 관리 한다.
        // BookAggregate는 BookAddedEvent(책 추가 이벤트)를 기반으로 책의 상태 설정한다.
        BookAggregate bookAggregate = new BookAggregate(event);

        // 이벤트 스토어에 저장 (나중에 상태를 찾기 위해)
        eventStore.put(bookId, bookAggregate);


        // Projection 저장
        // 빠른 조회를 위해 현재 상태를 따로 저장 (읽기 전용 모델).
        // 현재 상태를 빠르게 조회할 수 있도록 별도 저장하는 테이블이다.
        // 이벤트 저장소 자체는 상태를 직접 저장하지 않는다!
        // 하지만 조회 속도를 빠르게 하기 위해, Projection을 사용하여 "현재 상태"를 따로 유지한다.
        BookProjection bookProjection = new BookProjection(bookId, title);
        bookRepository.save(bookProjection);
    }

    // 도서 대여 처리
    public void borrowBook(BorrowBookCommand command) {
        // 도서관에 책이 있는지 확인
        if (!eventStore.containsKey(command.getBookId())) {
            throw new RuntimeException("책이 없음");
        }

        // 현재 상태 확인
        BookAggregate bookAggregate = eventStore.get(command.getBookId());

        // 이미 빌려진 책인지 확인
        if (bookAggregate.isBorrowed()) {
            throw new RuntimeException("책은 이미 빌려졌다");
        }

        // "책이 대여됨" 이벤트 생성
        // 이벤트 로그를 남기기 위한 코드
        BookBorrowedEvent event = new BookBorrowedEvent(command.getBookId(), command.getUserId());

        // 현재 상태를 업데이트하기 위한 코드 / 이 책이 현재 빌려진 상태임(isBorrowed = true)
        bookAggregate.apply(event);

        // Projection 업데이트
        BookProjection bookProjection = bookRepository.findById(command.getBookId())
                .orElseThrow(() -> new RuntimeException("책을 찾을 수 없다."));
        bookProjection.borrowBook();
        bookRepository.save(bookProjection);
    }
}
