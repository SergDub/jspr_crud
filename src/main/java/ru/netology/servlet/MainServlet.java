package ru.netology.servlet;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.netology.config.JavaConfig;
import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
  private PostController controller;
  private static final String GET_METHOD = "GET";
  private static final String POST_METHOD = "POST";
  private static final String DELETE_METHOD = "DELETE";
  private static final String API_POSTS_PATH = "/api/posts";

  @Override
  public void init() {
      final var context = new AnnotationConfigApplicationContext(JavaConfig.class);
      controller = context.getBean(PostController.class);
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) {
    try {
      final var path = req.getRequestURI();
      final var method = req.getMethod();

      if (GET_METHOD.equals(method) && API_POSTS_PATH.equals(path)) {
        controller.all(resp);
        return;
      }

      if (GET_METHOD.equals(method) && path.matches("/api/posts/\\d+")) {
        final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1)); // Добавлено +1
        controller.getById(id, resp);
        return;
      }

      if (POST_METHOD.equals(method) && API_POSTS_PATH.equals(path)) {
        controller.save(req.getReader(), resp);
        return;
      }

      if (DELETE_METHOD.equals(method) && path.matches("/api/posts/\\d+")) {
        final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1)); // Добавлено +1
        controller.removeById(id, resp);
        return;
      }

      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } catch (Exception e) {
      e.printStackTrace();
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }

}
