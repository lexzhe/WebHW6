package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import com.google.common.hash.Hashing;
import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.impl.ArticleRepositoryImpl;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;

import java.nio.charset.StandardCharsets;
import java.util.List;

/** @noinspection UnstableApiUsage*/
public class ArticleService {
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final ArticleRepository articleRepository = new ArticleRepositoryImpl();

    public void validatePostId(String str) throws ValidationException{
        if (Strings.isNullOrEmpty(str)) {
            throw new ValidationException("Id is required");
        }
        if (!str.matches("[0-9]+")) {
            throw new ValidationException("Id can contain only numbers");
        }
        if (str.length() > 12) {
            throw new ValidationException("Id can't be longer than 8 letters");
        }
    }

    public void validateRegistration(Article article) throws ValidationException {
        if (userRepository.find(article.getUserId()) == null) {
            throw new ValidationException("Author not specified");
        }
        if (Strings.isNullOrEmpty(article.getTitle())) {
            throw new ValidationException("Title is required");
        }
        if (article.getTitle().length() > 256) {
            throw new ValidationException("Title should not be longer than 256 characters");
        }
        if (Strings.isNullOrEmpty(article.getText())) {
            throw new ValidationException("Text is required");
        }
        if (article.getText().length() > 1024) {
            throw new ValidationException("Text should not be longer than 1024 characters");
        }
    }

    public void validateAuthor(long id, User user) throws ValidationException {
        Article article = find(id);
        if (article.getUserId() != user.getId()) {
            throw new ValidationException("Only author is allowed to hide/publish his articles");
        }
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public void setHidden(long id, boolean hidden) {
        articleRepository.setHidden(id, hidden);
    }

//    public List<Article> findAllChronologicallyNotHidden() {
//        return articleRepository.findAllChronologicallyNotHidden();
//    }

    public void save(Article article) {
        articleRepository.save(article);
    }

    public Article find(long id) {
        return articleRepository.find(id);
    }

    public List<Article> findAllByUserId(long userId) {
        return articleRepository.findAllByUserId(userId);
    }
//    public User validateAndFindByLoginAndPassword(String login, String password) throws ValidationException {
//        User user = userRepository.findByLoginAndPasswordSha(login, getPasswordSha(password));
//        if (user == null) {
//            throw new ValidationException("Invalid login or password");
//        }
//        return user;
//    }
}
