package com.ygor.springrestapi.blog.Controller;

import com.ygor.springrestapi.blog.Domain.Comment;
import com.ygor.springrestapi.blog.Domain.Post;
import com.ygor.springrestapi.blog.Exception.ResourceNotFoundException;
import com.ygor.springrestapi.blog.Repository.CommentRepository;
import com.ygor.springrestapi.blog.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/posts")
public class PostController {

    private PostRepository postRepository;

    private CommentRepository commentRepository;

    public PostController(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    //@ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public ResponseEntity<Post> createPost(@RequestBody @Valid Post post, BindingResult result) {

        if (result.hasErrors()) {
            return new ResponseEntity<>(post, HttpStatus.BAD_REQUEST);
        }

        HttpHeaders responseHeaders = new HttpHeaders();

        Post savedPost = this.postRepository.save(post);

        return new ResponseEntity<>(savedPost, responseHeaders, HttpStatus.CREATED);
    }

    @GetMapping("")
    public List<Post> listPosts() {
        return this.postRepository.findAll();
    }

    @GetMapping("/{id}")
    public Post getPost(@PathVariable("id") int id) {
        return this.postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No post found with ID = " + id)
        );
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable("id") int id, @RequestBody Post post) {
        this.postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No post found with ID = " + id)
        );

        return this.postRepository.save(post);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable("id") int id) {
        Post post = this.postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No post found with ID = " + id)
        );

        this.postRepository.deleteById(post.getId());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{id}/comments")
    public void createPostComment(@PathVariable("id") int id, @RequestBody Comment comment) {
        Post post = this.postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No post found with ID = " + id)
        );

        post.getComments().add(comment);
    }

    @DeleteMapping("{postId}/comments/{commentId}")
    public void deletePostComment(@PathVariable("postId") int postId, @PathVariable("commentId") int commentId) {
        Post post = this.postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("No post found with ID = " + postId)
        );

        Comment comment = this.commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("No comment found with ID = " + commentId)
        );

        this.commentRepository.deleteById(comment.getId());
    }
}
