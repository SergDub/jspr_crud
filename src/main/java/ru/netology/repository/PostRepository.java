package ru.netology.repository;

import ru.netology.model.Post;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class PostRepository {
  private final Map<Long, Post> posts = new ConcurrentHashMap<>();
  private final AtomicLong idGenerator = new AtomicLong(0);

  public Map<Long, Post> all() {
    return posts;
  }

  public Post getById(long id) {
    return posts.get(id);
  }

  public Post save(Post post) {
    if (post.getId() == 0) {
      long newId = idGenerator.incrementAndGet();
      post.setId(newId);
    }
    posts.put(post.getId(), post);
    return post;
  }

  public void removeById(long id) {
    posts.remove(id);
  }
}
