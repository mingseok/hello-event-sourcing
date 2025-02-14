package com.example.event.controller;

import com.example.event.command.BorrowBookCommand;
import com.example.event.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private BookService bookService;

    @PostMapping("/{bookId}/borrow")
    public ResponseEntity<?> borrowBook(@PathVariable String bookId, @RequestBody BorrowBookCommand command) {
        bookService.borrowBook(command);
        return ResponseEntity.ok("Book borrowed successfully");
    }
}
