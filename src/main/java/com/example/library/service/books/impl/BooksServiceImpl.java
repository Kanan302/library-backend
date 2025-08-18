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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public BooksResponseDto createBook(BooksRequestDto booksRequestDto, MultipartFile imageFile) {
        List<Books> existingBooks = booksRepository.findByTitle(booksRequestDto.getTitle());
        if (!existingBooks.isEmpty()) {
            throw new RuntimeException("Kitab '" + booksRequestDto.getTitle() + "' artıq mövcuddur");
        }

        Author author = authorRepository.findById(booksRequestDto.getAuthorId())
                .orElseThrow(() -> new NotFoundException("Author tapılmadı"));

        Category category = categoryRepository.findById(booksRequestDto.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category tapılmadı"));

        if (!author.getCategories().contains(category)) {
            author.getCategories().add(category);
            authorRepository.save(author);
        }

        if (!category.getAuthors().contains(author)) {
            category.getAuthors().add(author);
            categoryRepository.save(category);
        }

        String base64Image;
        try {
            base64Image = java.util.Base64.getEncoder().encodeToString(imageFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Fayl Base64-a çevrilmədi", e);
        }

        Books book = booksMapper.toEntity(booksRequestDto, author, category);
        book.setImageBase64(base64Image);
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


        boolean alreadyAdded = book.getReaders()
                .stream()
                .anyMatch(u -> u.getId().equals(userId));

        if (!alreadyAdded) {
            book.getReaders().add(user);
            user.getReadBooks().add(book);
            userRepository.save(user);
        }

        book.setReadDate(plannedReadDate);
        Books savedBook = booksRepository.save(book);

        return booksMapper.toDto(savedBook);
    }

    @Override
    public BooksResponseDto updateBookById(Long bookId, BooksRequestDto booksRequestDto, MultipartFile imageFile) {
        Books book = booksRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Kitab tapılmadı"));

        if (booksRequestDto.getTitle() != null) {
            book.setTitle(booksRequestDto.getTitle());
        }

        if (booksRequestDto.getAuthorId() != null) {
            Author author = authorRepository.findById(booksRequestDto.getAuthorId())
                    .orElseThrow(() -> new NotFoundException("Author tapılmadı"));
            book.setAuthor(author);
        }

        if (booksRequestDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(booksRequestDto.getCategoryId())
                    .orElseThrow(() -> new NotFoundException("Category tapılmadı"));
            book.setCategory(category);
        }

        if (booksRequestDto.getReadDate() != null) {
            book.setReadDate(booksRequestDto.getReadDate());
        }

        if (booksRequestDto.getReview() != null) {
            book.setReview(booksRequestDto.getReview());
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String base64Image = java.util.Base64.getEncoder().encodeToString(imageFile.getBytes());
                book.setImageBase64(base64Image);
            } catch (IOException e) {
                throw new RuntimeException("Fayl Base64-a çevrilmədi", e);
            }
        }

        Books updatedBook = booksRepository.save(book);
        return booksMapper.toDto(updatedBook);
    }


}
