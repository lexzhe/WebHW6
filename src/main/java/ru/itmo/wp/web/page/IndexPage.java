package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.model.service.UserService;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** @noinspection unused*/
public class IndexPage {
    private final ArticleService articleService = new ArticleService();
    private final UserService userService = new UserService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        putMessage(request, view);
    }

    private void findAll(HttpServletRequest request, Map<String, Object> view) {
        List<Article> articles = articleService.findAll();
        List<User> authors = new ArrayList<>();
        for (Article article : articles) {
            authors.add(userService.find(article.getUserId()));
        }
        view.put("articles", articles);
        view.put("authors", authors);
    }

    private void putMessage(HttpServletRequest request, Map<String, Object> view) {
        String message = (String) request.getSession().getAttribute("message");
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            request.getSession().removeAttribute("message");
        }
    }

//    private void findUser(HttpServletRequest request, Map<String, Object> view){
//        long id = Long.parseLong(request.getParameter("id"));
//        User user = userService.find(id);
//        view.put("requested-user", user);
//    }
}
