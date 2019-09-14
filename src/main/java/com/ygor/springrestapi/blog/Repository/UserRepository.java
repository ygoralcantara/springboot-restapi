package com.ygor.springrestapi.blog.Repository;

import com.ygor.springrestapi.blog.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
