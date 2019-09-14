package com.ygor.springrestapi.blog.Repository;

import com.ygor.springrestapi.blog.Domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
