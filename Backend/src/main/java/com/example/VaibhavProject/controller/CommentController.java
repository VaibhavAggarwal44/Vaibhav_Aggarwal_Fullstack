package com.example.VaibhavProject.controller;

import com.example.VaibhavProject.model.Comment;
import com.example.VaibhavProject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * Returns list of all comments
     */
    @GetMapping("/comments")
    public Iterable<Comment> allComments(){
        return commentService.getAllComments();
    }

    /**
     * inserts a comment in database
     */
    @PostMapping("/insert")
    public Comment createComment(@RequestBody Comment comment){
        return commentService.insertComment(comment);
    }

    /**
     * deletes comment with given commentid from database
     */
    @GetMapping("/delete/{commentId}")
    public void deleteComment(@PathVariable String commentId){
        commentService.deleteComment(commentId);
    }

    /**
     * inserts reply in replies array of a comment
     */
    @PostMapping("/{commentId}/insert")
    public Comment createReply(@RequestBody Comment reply,@PathVariable String commentId){
        return commentService.insertReply(reply,commentId);
    }

    /**
     * returns all the comments under an article with articleId
     */
    @GetMapping("/{articleId}")
    public List<Comment> getComments(@PathVariable String articleId){
        return commentService.findArticles(articleId);
    }

    /**
     * find a comment with comment id
     */
    @GetMapping("/{commentId}/f")
    public Comment getcommentbyid(@PathVariable String commentId){
        return commentService.findbyid(commentId);
    }

}
