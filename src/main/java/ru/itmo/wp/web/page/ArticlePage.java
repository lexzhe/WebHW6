package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class ArticlePage {
    private final ArticleService articleService = new ArticleService();
    private final UserService userService = new UserService();

    public User getUser(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        return user;
    }



    private void action(HttpServletRequest request, Map<String, Object> view) {
        if (request.getSession().getAttribute("user") == null) {
            throw new RedirectException("/index");
        }
        view.put("users", userService.findAll());
        view.put("articles", articleService.findAll());
    }

//    private void action(HttpServletRequest request, Map<String, Object> view) {
//        if(request.getSession().getAttribute("user") != null){
////            request.getSession().setAttribute("message", "You successfully created article!");
//        }else{
//            request.getSession().setAttribute("message", "You are not authorized!");
//            throw new RedirectException("/index");
//        }
//    }

    private void create(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        Article article = new Article();
        article.setTitle(request.getParameter("title"));
//        String password = request.getParameter("password");
        long userId = ((User)(request.getSession().getAttribute("user"))).getId();

        article.setText(request.getParameter("text"));
        article.setUserId(userId);
        articleService.validateRegistration(article);
        articleService.save(article);

        request.getSession().setAttribute("message", "You successfully created article!");
        throw new RedirectException("/index");
    }
}