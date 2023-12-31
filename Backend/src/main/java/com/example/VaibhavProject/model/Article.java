package com.example.VaibhavProject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

//@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(indexName = "article1")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Article {
//    public int id;
    public String id;

    public int likes;

    public int dislikes;

    public boolean isPublic;

    public int views;

    public String articleBody;

    public String heading;

    public String displayBody;

    public String createdBy;

    public String likedBy;

    public String dislikedBy;

    /**
     * Generates unique id for our article.
     */
    public static String generateUniqueID() {
        // Generate a random UUID (Universally Unique Identifier)
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString().replaceAll("-", "");

        return id;
    }

    public Article(){
        this.likedBy="";
        this.dislikedBy="";
        this.id=generateUniqueID();
    }

    /**
     *  Custom comparator that compares articles on the basis of likes, views and dislikes.
     */
    static class customComparator implements Comparator<Article>{
        public int compare(Article o1, Article o2){
            if(o1.getLikes()>o2.getLikes()){
                return -1;
            }
            else if(o2.getLikes()>o1.getLikes()){
                return 1;
            }
            else{
                if(o1.getViews()>o2.getViews()){
                    return -1;
                }
                else if(o2.getViews()>o1.getViews()){
                    return 1;
                }
                else{
                    if(o1.getDislikes()<o2.getDislikes()){
                        return -1;
                    }
                    else{
                        return 1;
                    }


                }

            }
        }

    }


    /**
     * comparator to sort on the basis of likes
     */
    static class LikeComparator implements Comparator<Article> {
        @Override
        public int compare(Article article1, Article article2) {
            return Integer.compare(article2.getLikes(), article1.getLikes());
        }
    }

    /**
     * comparator to sort on the basis of dislikes
     */
    static class DisLikeComparator implements Comparator<Article> {
        @Override
        public int compare(Article article1, Article article2) {
            return Integer.compare(article1.getLikes(), article2.getLikes());
        }
    }

    /**
     * comparator to sort on the basis of views
     */
    static class ViewComparator implements Comparator<Article> {
        @Override
        public int compare(Article article1, Article article2) {
            return Integer.compare(article2.getViews(), article1.getViews());
        }
    }

    /**
     * function to sort on the basis of likes
     */
    public List<Article> L_SORT(List<Article> list){
        Collections.sort(list,new Article.LikeComparator());
        return list;
    }

    /**
     * function to apply our custom sort
     */
    public List<Article> C_SORT(List<Article> list){
        Collections.sort(list,new Article.customComparator());
        return list;
    }

    /**
     * comparator to sort on the basis of dislikes
     */
    public List<Article> D_SORT(List<Article> list){
        Collections.sort(list,new Article.DisLikeComparator());
        return list;
    }

    /**
     * comparator to sort on the basis of views
     */
    public List<Article> V_SORT(List<Article> list){
        Collections.sort(list,new Article.ViewComparator());
        return list;
    }

}
