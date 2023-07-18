package com.example.VaibhavProject.service;

import com.example.VaibhavProject.model.Comment;
import com.example.VaibhavProject.repository.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CommentService {
    @Autowired
    private CommentRepo commentRepo;

    /**
     * Returns a list of all comments
     */
    public Iterable<Comment> getAllComments(){
        return commentRepo.findAll();
    }

    /**
     * Returns a comment by given commentId
     */
    public Comment findbyid(String id) throws NoSuchElementException {
        return commentRepo.findById(id).get();
    }

    /**
     * Inserts given comment in database
     */
    public Comment insertComment(Comment comment){
        comment.setParentComment(true);
        return commentRepo.save(comment);
    }

    /**
     * Checks if a user with given username already exists or not
     */
    public List<Comment> findArticles(String articleId){
        return commentRepo.findByArticleIdAndParentComment(articleId,true);
    }

    /**
     * Deletes a comment with given commentId
     */
    public void deleteComment(String commentId){
        commentRepo.deleteById(commentId);
    }

    /**
     * Inserts a reply under given comment in its replies array
     */
    public Comment insertReply(Comment reply,String commentId){
        Comment comment=commentRepo.findById(commentId).get();
        comment.getReplies().add(reply);

        reply.setParentComment(false);

        commentRepo.save(comment);
        commentRepo.save(reply);
        return reply;
    }

}
