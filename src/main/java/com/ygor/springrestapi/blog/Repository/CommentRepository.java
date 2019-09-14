package com.ygor.springrestapi.blog.Repository;

import com.ygor.springrestapi.blog.Domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
