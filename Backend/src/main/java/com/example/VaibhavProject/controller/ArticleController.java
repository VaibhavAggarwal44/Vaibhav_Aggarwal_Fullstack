package com.example.VaibhavProject.controller;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.VaibhavProject.model.Article;
import com.example.VaibhavProject.service.ArticleSearchService;
import com.example.VaibhavProject.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/apis")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleSearchService articleSearchService;

    /**
     * Returns list of all public articles
     */
    @GetMapping("/articles")
    public List<Article> getAllArticles(){
        List<Article> list=articleService.findPublicArticles("");
        return list;
    }

    /**
     * Returns list of articles that are createdBy 'username'
     */
    @GetMapping("/checker/{id}")
    public List<Article> findByUsername(@PathVariable String id){
        return articleService.findbyusername(id);
    }

    /**
     * Returns public articles that contain query in article body
     */
    @GetMapping("/public/{query}")
    public List<Article> findPublicArticles(@PathVariable String query){
        String word=query.replace("--"," ");
        return articleService.searchPublicArticles(word);
    }

    /**
     * Returns list of sorted articles on the basis of likes
     */
    @GetMapping("/articles/sortLike/{username}")
    public List<Article> sortByLikes(@PathVariable String username){
        List<Article> list=articleService.findPublicArticles(username);

        Article article=new Article();
        article.L_SORT(list);

        return list;
    }

    /**
     * Returns list of all public articles
     */
    @GetMapping("/articles/userArticles")
    public Iterable<Article> userArticles(){
        Iterable<Article> list1=articleService.getArticles();
        List<Article> list=new ArrayList<>();

        for(Article item:list1){
            if(item.isPublic) {
                list.add(item);
            }
        }

        list1=list;
        return list1;
    }

    /**
     * Returns list of articles sorted on the basis of dislikes
     */
    @GetMapping("/articles/sortDislike")
    public Iterable<Article> sortByDislikes(){
        Iterable<Article> list1=articleService.getArticles();
        List<Article> list=new ArrayList<>();

        for(Article item:list1){
            list.add(item);
        }

        Article article=new Article();
        article.D_SORT(list);

        list1=list;
        return list1;
    }

    /**
     * Returns list of articles sorted on the basis of views
     */
    @GetMapping("/articles/sortView/{username}")
    public List<Article> sortByViews(@PathVariable String username){
        List<Article> list=articleService.findPublicArticles(username);

        Article article=new Article();
        article.V_SORT(list);

        return list;
    }

    /**
     * This function helps us insert article recieved in request body in our
     * elasticsearch database. Article is returned as inserted.
     * @param article
     */
    @PostMapping("/insert")
    public Article insertArticle(@RequestBody Article article){
        return articleService.insertArticle(article);
    }

    /**
     * This function is to update article details as recieved as RequestBody.
     * @param article
     */
    @PostMapping("/update")
    public Article updateArticle(@RequestBody Article article){
        return articleService.updateArticle(article);
    }

    /**
     * This function helps us update likes or dislikes of an article. id is article id, user is the username
     * and ld is like, dislike, unlike or undislike whichever we are performing.
     * @param id
     * @param user
     * @param ld
     */
    @GetMapping("/{id}/{user}/{ld}")
    public Article updateArticleLike(@PathVariable String id,@PathVariable String user,@PathVariable String ld){
        System.out.println("check3");
        Article article=articleService.findById(id);
        if(ld.equals("like"))
            article=articleService.updateArticleLikes(article,id,user);
        else if(ld.equals("dislike"))
            article=articleService.updateArticleDislikes(article,id,user);
        else if(ld.equals("unlike")){
            article=articleService.unlike(article,id,user);
        }
        else if(ld.equals("undislike")){
            article=articleService.undislike(article,id,user);
        }

        return article;
    }

    /**
     * This function returns an article with given article id passed as PathVariable.
     * It also increases its views by 1.
     */
    @GetMapping("/{id}/{username}")
    public Article updateArticleView(@PathVariable String id,@PathVariable String username){
        Article article=articleService.findById(id);
        if(username.equals(article.getCreatedBy())){
            return article;
        }
        article=articleService.updateArticleViews(article,id);
        return article;
    }

    /**
     * This function decreases views of an article by 1 and updates it.
     * It was created because our frontend refreshes on inserting a reply to
     * a comment and to keep the number of views same, they had to be decreased by 1.
     * @param id
     */
    @GetMapping("/{id}/tester")
    public void updateViewTest(@PathVariable String id){
        Article article=articleService.findById(id);
        articleService.updateArticleViewsTest(article,id);
        return ;
    }

    /**
     * this function lets us delete all articles
     */
    @PostMapping("/final/delete")
    public void deleteAll() {
        articleService.deleteArticle();
    }


    /**
     * This function helps us to perform single and multisearch query. Single search query can perform substring matching
     * or if no substring match is found then fuzzy search (with a fuzziness of 2 ) is performed. In multiword search ,we
     * find if all or some words are present in the article. Search is performed on heading and articleBody. Username is passed
     * to search from user's private articles.
     * @param word
     * @param username
     * @return list of articles
     * @throws IOException
     */
    @GetMapping("/search/{word}/{username}")
    public List<Article> searchArticlesWithWord(@PathVariable String word,@PathVariable String username) throws IOException {
        if(!word.contains("--")) {
            System.out.println("search1");
            word=word.replace("XNX","/");
            SearchResponse<Article> searchResponse = articleSearchService.matchArticleWithWordService(word);

            SearchResponse<Article> searchResponse2 = articleSearchService.fuzzyHeadingSearch(word);

            System.out.println(searchResponse.hits().hits().toString());

            List<Hit<Article>> listOfHits = searchResponse.hits().hits(); //fuzzy
            List<Hit<Article>> listOfHits2=searchResponse2.hits().hits(); //fuzzy heading search

            List<Article> hlist=articleService.headingInfix(word);  //heading and ispublic substring matching
            List<Article> hlist2=articleService.headingInfix2(word,username); //heading and username substring matching

            List<Article> ab=articleService.finderFunc(word,username);
            List<Article> list = infixFinder(word);  //articlebody and ispublic
            List<Article> list2=articleService.infixFinder2(word,username);  //articlebody and username

            Article a=new Article();
            a.C_SORT(hlist);

            a.C_SORT(ab);

            a.C_SORT(hlist2);

            a.C_SORT(list2);

            a.C_SORT(list);

            hlist.addAll(ab);
            hlist.addAll(hlist2);
            hlist.addAll(list2);
            hlist.addAll(list);

            if(hlist.isEmpty()){
                for (Hit<Article> item : listOfHits2) {
                    hlist.add(item.source());
                }
                for (Hit<Article> item : listOfHits) {
                    hlist.add(item.source());
                }

                a.C_SORT(hlist);

                System.out.println("damn");

                Set<Article> set=new LinkedHashSet<>(hlist);
                hlist.clear();
                hlist.addAll(set);
                return hlist;
            }else{

                Set<Article> set=new LinkedHashSet<>(hlist);
                hlist.clear();
                hlist.addAll(set);
                return hlist;
            }
        }else{
            System.out.println("search 2");
            String query=word.replace("--"," ");
            query=query.replace("XNX","/");
            SearchResponse<Article> searchResponse = articleSearchService.matchAllArticleService(query);
            SearchResponse<Article> searchResponse2=articleSearchService.matchAllHeadingService(query);
            SearchResponse<Article> searchResponse3 = articleSearchService.matchPhrase(query);
            SearchResponse<Article> searchResponse4 = articleSearchService.matchPhraseHeading(query);
            SearchResponse<Article> searchResponse5 = articleSearchService.matchAllQueryOR(query);

            System.out.println(searchResponse.hits().hits().toString());

            List<Hit<Article>> listOfHits = searchResponse.hits().hits();
            List<Hit<Article>> listOfHits2 = searchResponse2.hits().hits();
            List<Hit<Article>> listOfHits3 = searchResponse3.hits().hits();
            List<Hit<Article>> listOfHits4 = searchResponse4.hits().hits();
            List<Hit<Article>> listOfHits5 = searchResponse5.hits().hits();


            List<Article> list = new ArrayList<>();
            List<Article> list2 = new ArrayList<>();
            List<Article> list3 = new ArrayList<>();
            List<Article> list4 = new ArrayList<>();
            List<Article> list5=new ArrayList<>();

            for (Hit<Article> item : listOfHits) {
                list.add(item.source());
            }

            for (Hit<Article> item : listOfHits2) {
                list2.add(item.source());
            }

            for (Hit<Article> item : listOfHits3) {
                list3.add(item.source());
            }

            for (Hit<Article> item : listOfHits4) {
                list4.add(item.source());
            }

            for (Hit<Article> item : listOfHits5) {
                list5.add(item.source());
            }

            Article a=new Article();

            a.C_SORT(list);
            a.C_SORT(list2);
            a.C_SORT(list3);
            a.C_SORT(list4);

            a.C_SORT(list5);

            list2.addAll(list);
            list2.addAll(list4);
            list2.addAll(list3);
            list2.addAll(list5);

            Set<Article> set=new LinkedHashSet<>(list2);
            list2.clear();
            list2.addAll(set);

            return list2;

        }
    }

    /**
     * This function helps us to perform 'containing' query on articleBody. It returns a list of
     * articles that contain a substring 'query' in articleBody.
     * @param query
     * @return list
     */
    @GetMapping("/infix/{query}")
    public List<Article> infixFinder(@PathVariable String query){
        return articleService.infixFinder(query);
    }

    /**
     * This function returns list of articles that are created by 'username' and are public.
     * @param username
     */
    @GetMapping("/{username}/getPublic")
    public List<Article> getArticlesByUsername(@PathVariable String username){
        return articleService.findArticlesByUsername(username);
    }

    /**
     * This function returns a Iterable list of article that contain all
     * the private articles. This function was made only for testing purposes.
     */
    @GetMapping("/articles/privateArticles")
    public Iterable<Article> privateArticles(){
        Iterable<Article> list1=articleService.getArticles();
        List<Article> list=new ArrayList<>();

        for(Article item:list1){
            if(!item.isPublic) {
                list.add(item);
            }
        }

        list1=list;
        return list1;
    }
}
