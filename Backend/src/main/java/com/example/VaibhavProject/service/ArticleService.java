package com.example.VaibhavProject.service;

import com.example.VaibhavProject.model.Article;
import com.example.VaibhavProject.repository.ArticleRepo;
import org.elasticsearch.client.RequestOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.*;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepo articleRepo;

    public Page<Article> getById(String id){
        System.out.println("checker");
        Pageable pageable= PageRequest.of(0, 5, Sort.by(
                Order.asc("createdBy")));

        return articleRepo.findByName(id,pageable);
    }

    /**
     * Returns list of articles createdBy "id"
     */
    public List<Article> findbyusername(String id){
        return articleRepo.findByCreatedBy(id);
    }

    /**
     * Returns list of articles that has 'query' in articleBody and isPublic is true
     */
    public List<Article> searchPublicArticles(String query){
        return articleRepo.findByArticleBodyAndIsPublic(query,true);
    }

    /**
     * Returns list of public articles or createdBy "id"
     */
    public List<Article> findPublicArticles(String s){
        return articleRepo.findByIsPublicOrCreatedBy(true,s);
    }

    /**
     * Returns iterable list of all articles
     */
    public Iterable<Article> getArticles(){
        return articleRepo.findAll();
    }

    /**
     * Inserts article in the database
     */
    public Article insertArticle(Article article) {
        return articleRepo.save(article);
    }

    /**
     * Returns article that has given id
     */
    public Article findById(String id) throws NoSuchElementException {
        return articleRepo.findById(id).get();
    }

    public List<Article> findArticlesByUsername(String username){
        return articleRepo.findByCreatedByAndIsPublic(username,true);
    }

    public List<Article> finderFunc(String query,String username){
        Iterable<Article> ab=articleRepo.findAll();
        List<Article> list=new ArrayList<>();

        for(Article item:ab){
            if((item.isPublic || item.createdBy.equals(username)) && item.articleBody!=null && item.articleBody.contains(query)){
                list.add(item);
            }
        }
        return list;
    }

    /**
     * This function returns a list of all articles containing 'query' in articleBody and isPublic as true
     */
    public List<Article> infixFinder(String query){
        return articleRepo.findByArticleBodyContainingAndIsPublic(query,true);
    }

    /**
     * This function returns a list of all articles containing 'query' in articleBody and createdBy 'username'
     */
    public List<Article> infixFinder2(String query,String username){
        return articleRepo.findByArticleBodyContainingAndCreatedBy(query,username);
    }

    /**
     * This function returns a list of all articles containing 'query' in heading and isPublic as true
     */
    public List<Article> headingInfix(String query){
        return articleRepo.findByHeadingContainingAndIsPublic(query,true);
    }

    /**
     * This function returns a list of all articles containing 'query' in heading and createdBy 'username'
     */
    public List<Article> headingInfix2(String query ,String username){
        return articleRepo.findByHeadingAndCreatedBy(query,username);
    }

    /**
     * This function handles likes of an article by performing necessary operations
     */
    public Article updateArticleLikes(Article article, String id,String likedBy) {
        Article article1  = articleRepo.findById(id).get();

        if(article1.getLikedBy().contains(likedBy)){
            return article1;
        }

        if(article1.getDislikedBy().contains(likedBy)){
            article1.setDislikes(article1.getDislikes()-1);
        }

        article1.setLikes(article.getLikes()+1);

        String s=article1.getLikedBy();
        s+=likedBy+" ";
        article1.setLikedBy(s);

        String s2=article1.getDislikedBy();
        String replace = s2.replace(likedBy+" ", "");
        article1.setDislikedBy(replace);

        article=article1;

        articleRepo.save(article);
        return article;
    }

    /**
     * This function reduces likes by 1
     */
    public Article unlike(Article article,String id,String likedBy){
        Article article1  = articleRepo.findById(id).get();
        article1.setLikes(article.getLikes()-1);

        String s=article1.getLikedBy();
//        s+=likedBy+" ";
//        article1.setLikedBy(s);
        String replace = s.replace(likedBy+" ", "");
        article1.setLikedBy(replace);
        article=article1;
        articleRepo.save(article);
        return article;
    }

    /**
     * This function reduces dislike by one
     */
    public Article undislike(Article article,String id,String dislikedBy){
        Article article1  = articleRepo.findById(id).get();
        article1.setDislikes(article.getDislikes()-1);

        String s=article1.getDislikedBy();
//        s+=likedBy+" ";
//        article1.setLikedBy(s);
        String replace = s.replace(dislikedBy+" ", "");
        article1.setDislikedBy(replace);
        article=article1;
        articleRepo.save(article);
        return article;
    }

    /**
     * This function handles dislike request made from the frontend
     */
    public Article updateArticleDislikes(Article article, String id,String dislikedBy) {
        System.out.println("check");
        Article article1  = articleRepo.findById(id).get();

        if(article1.getDislikedBy().contains(dislikedBy)){
            return article1;
        }

        if(article1.getLikedBy().contains(dislikedBy)){
            article1.setLikes(article1.getLikes()-1);
        }

        article1.setDislikes(article.getDislikes()+1);
        String s=article1.getDislikedBy();
        s+=dislikedBy+" ";
        article1.setDislikedBy(s);

        String s2=article1.getLikedBy();
        String replace = s2.replace(dislikedBy+" ", "");
        article1.setLikedBy(replace);
        article=article1;

        articleRepo.save(article);

        return article1;
    }

    /**
     * This function increases views of article by 1 and updates it on database
     */
    public Article updateArticleViews(Article article, String id) {
        Article article1  = articleRepo.findById(id).get();
        article1.setViews(article.getViews()+1);

        article=article1;

        articleRepo.save(article);
        return article1;
    }

    /**
     * This function reduces views of an article by 1
     */
    public void updateArticleViewsTest(Article article, String id) {
        Article article1  = articleRepo.findById(id).get();
        article1.setViews(article.getViews()-1);

        article=article1;
        articleRepo.save(article);
        return ;
    }

    /**
     * This function updates an existing article or creates new one
     */
    public Article updateArticle(Article article) {
        articleRepo.save(article);
        return article;
    }

    /**
     * This function deletes all articles
     */
    public void deleteArticle(){
        articleRepo.deleteAll();
    }

}
