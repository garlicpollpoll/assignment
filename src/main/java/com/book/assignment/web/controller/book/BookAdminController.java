package com.book.assignment.web.controller.book;

import com.book.assignment.entity.Book;
import com.book.assignment.exception.book.NotFoundBook;
import com.book.assignment.web.controller.book.dto.BookRegisterDto;
import com.book.assignment.web.controller.book.dto.BookUpdateDto;
import com.book.assignment.web.repository.BookRepository;
import com.book.assignment.web.service.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/book/admin")
public class BookAdminController {

    private final BookService bookService;
    private final BookRepository bookRepository;

    @GetMapping("")
    public String admin() {
        return "book/admin";
    }

    @GetMapping("/register")
    public String register(Model model) {
        BookRegisterDto dto = new BookRegisterDto();
        model.addAttribute("book", dto);
        return "book/register";
    }

    @PostMapping("/register")
    public String registerPost(@Validated @ModelAttribute("register") BookRegisterDto dto, BindingResult bindingResult) throws IOException {
        if (dto.getImage().isEmpty()) {
            bindingResult.reject("ImageIsEmpty");
            return "book/register";
        }

        if (bindingResult.hasErrors()) {
            return "book/register";
        }

        bookService.uploadBook(dto);

        return "redirect:/book/admin";
    }

    @GetMapping("/update")
    public String detail() {
        return "book/find_book";
    }

    @GetMapping("/update/{bookId}")
    public String update(Model model, @PathVariable("bookId") Long bookId) {
        BookRegisterDto dto = new BookRegisterDto();

        Book findBook = bookRepository.findById(bookId).orElse(null);

        model.addAttribute("book", findBook);

        return "book/update";
    }

    @PostMapping("/update/{bookId}")
    @Transactional
    public String updatePost(@PathVariable("bookId") Long bookId,
                             @RequestParam("image")MultipartFile file,
                             @RequestParam("bookName") String bookName,
                             @RequestParam("publisher") String publisher,
                             @RequestParam("ISBN") String isbn,
                             @RequestParam("author") String author,
                             @RequestParam("stock") int stock,
                             RedirectAttributes redirectAttributes) {
        Book findBook = bookRepository.findById(bookId).orElseThrow(() -> new NotFoundBook("해당하는 책을 찾을 수 없습니다."));
        BookUpdateDto dto = new BookUpdateDto();
        dto.setImage(file);
        dto.setBookName(bookName);
        dto.setPublisher(publisher);
        dto.setISBN(isbn);
        dto.setAuthor(author);
        dto.setStock(stock);
        bookService.updateBook(findBook, dto);

        redirectAttributes.addAttribute("bookId", findBook.getId());

        return "redirect:/book/detail/{bookId}";
    }
}
