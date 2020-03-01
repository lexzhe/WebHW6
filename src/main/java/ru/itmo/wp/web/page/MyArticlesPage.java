package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class MyArticlesPage {
    private final ArticleService articleService = new ArticleService();

    private void setHidden(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        if (request.getSession().getAttribute("user") == null) {
            request.getSession().setAttribute("message", "This domain is available for authorized users only.");
            throw new RedirectException("/index");
        }

        User user = ((User) request.getSession().getAttribute("user"));
        String postId = request.getParameter("id");
        articleService.validatePostId(postId);

        long id = Long.parseLong(postId);
        boolean value = Long.parseLong(request.getParameter("value")) == 1;

        articleService.validateAuthor(id, user);
        articleService.setHidden(id, value);
    }

    private void action(HttpServletRequest request, Map<String, Object> view) {
        if (request.getSession().getAttribute("user") == null) {
            request.getSession().setAttribute("message", "This domain is available for authorized users only.");
            throw new RedirectException("/index");
        }
        long userId = ((User) request.getSession().getAttribute("user")).getId();
        view.put("userArticles", articleService.findAllByUserId(userId));
    }
}