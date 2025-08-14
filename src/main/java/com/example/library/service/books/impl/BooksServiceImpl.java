package com.example.library.service.books.impl;

import com.example.library.dto.books.request.BooksRequestDto;
import com.example.library.dto.books.response.BooksResponseDto;
import com.example.library.entity.author.Author;
import com.example.library.entity.books.Books;
import com.example.library.entity.category.Category;
import com.example.library.entity.user.User;
import com.example.library.exception.NotFoundException;
import com.example.library.mapper.books.BooksMapper;
import com.example.library.repository.author.AuthorRepository;
import com.example.library.repository.books.BooksRepository;
import com.example.library.repository.category.CategoryRepository;
import com.example.library.repository.user.UserRepository;
import com.example.library.service.books.BooksService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BooksServiceImpl implements BooksService {
    private final BooksRepository booksRepository;
    private final BooksMapper booksMapper;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public BooksServiceImpl(
            BooksRepository booksRepository,
            BooksMapper booksMapper,
            AuthorRepository authorRepository,
            CategoryRepository categoryRepository,
            UserRepository userRepository
    ) {
        this.booksRepository = booksRepository;
        this.booksMapper = booksMapper;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public BooksResponseDto createBook(BooksRequestDto booksRequestDto) {
        Author author = authorRepository.findById(booksRequestDto.getAuthorId())
                .orElseThrow(() -> new NotFoundException("Author tapılmadı"));

        Category category = categoryRepository.findById(booksRequestDto.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category tapılmadı"));
//
//        User user = userRepository.findById(booksRequestDto.getUserId())
//                .orElseThrow(() -> new NotFoundException("User tapılmadı"));

        // Əgər author-un categories siyahısında category yoxdursa, əlavə et
        if (!author.getCategories().contains(category)) {
            author.getCategories().add(category);
            authorRepository.save(author);
        }

        // Əgər category-in author siyahısında author yoxdursa, əlavə et
        if (!category.getAuthors().contains(author)) {
            category.getAuthors().add(author);
            categoryRepository.save(category);
        }

        Books book = booksMapper.toEntity(booksRequestDto, author, category);
        book.setReaders(List.of());

        Books savedBook = booksRepository.save(book);
        return booksMapper.toDto(savedBook);
    }


    @Override
    public BooksResponseDto getBookById(Long id) {
        Books book = booksRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Kitab tapılmadı"));
        return booksMapper.toDto(book);
    }

    @Override
    public List<BooksResponseDto> getAllBooks() {
        return booksRepository.findAll()
                .stream()
                .map(booksMapper::toDto)
                .toList();
    }

    @Override
    public void deleteBookById(Long id) {
        booksRepository.deleteById(id);
    }

    @Override
    public BooksResponseDto setReadDate(Long bookId, Long userId, LocalDate plannedReadDate) {
        Books book = booksRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Kitab tapılmadı"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User tapılmadı"));

        // Əgər user readBooks-da yoxdursa əlavə et
        if (!book.getReaders().contains(user)) {
            book.getReaders().add(user);
            user.getReadBooks().add(book); // bi-directional listi də update edirik
            userRepository.save(user);
        }

        book.setReadDate(plannedReadDate);
        Books savedBook = booksRepository.save(book);

        return booksMapper.toDto(savedBook);
    }

}
