package com.example.VaibhavProject.repository;

import com.example.VaibhavProject.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepo extends ElasticsearchRepository<Article,String> {
    @Query("{\"match\": {\"createdBy\": {\"query\": \"?0\"}}}")
    Page<Article> findByName(String name, Pageable pageable);

    /**
     * Function to find articles on the basis of username
     */
    List<Article> findByCreatedBy(String name);

    /**
     * Function to find articles on the basis of articleBody and isPublic
     */
    List<Article> findByArticleBodyAndIsPublic(String query,boolean isPublic);

    /**
     * Function to find articles on the basis of isPublic or createdBy
     */
    List<Article> findByIsPublicOrCreatedBy(boolean isPublic,String createdBy);

    /**
     * Function to find articles on the basis of createdBy and isPublic
     */
    List<Article> findByCreatedByAndIsPublic(String createdBy,boolean isPublic);

    /**
     * Function to find articles on the basis of articleBody containing 's' and isPublic
     */
    List<Article> findByArticleBodyContainingAndIsPublic(String s,boolean isPublic);

    /**
     * Function to find articles on the basis of heading containing 's' and isPublic
     */
    List<Article> findByHeadingContainingAndIsPublic(String s,boolean isPublic);

    /**
     * Function to find articles on the basis of heading and createdBy
     */
    List <Article> findByHeadingAndCreatedBy(String s,String username);

    /**
     * Function to find articles on the basis of articleBody containing 'a' and createdBy
     */
    List<Article> findByArticleBodyContainingAndCreatedBy(String a,String b);
}
