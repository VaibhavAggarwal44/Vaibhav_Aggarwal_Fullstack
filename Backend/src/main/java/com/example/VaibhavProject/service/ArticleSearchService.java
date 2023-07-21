package com.example.VaibhavProject.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.example.VaibhavProject.model.Article;
import com.example.VaibhavProject.utils.ArticleSearchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.function.Supplier;

@Service
public class ArticleSearchService {
    @Autowired
    private ElasticsearchClient elasticsearchClient;

    /**
     * Function that returns SearchResponse with query processed in supplier (matchQuery on articleBody)
     */
    public SearchResponse<Article> matchAllArticleService(String word) throws IOException  {
        Supplier<Query> supplier= ArticleSearchUtil.supplier(word);
        SearchResponse<Article> searchResponse=elasticsearchClient.search(s->s.index("article1").query(supplier.get()),Article.class);
        System.out.println("elasaticsearch query is: "+supplier.get().toString());
        return searchResponse;
    }

    /**
     * Function that returns SearchResponse with query processed in supplier3 (matchPhraseQuery on articleBody)
     */
    public SearchResponse<Article> matchPhrase(String word) throws IOException  {
        Supplier<Query> supplier= ArticleSearchUtil.supplier3(word);
        SearchResponse<Article> searchResponse=elasticsearchClient.search(s->s.index("article1").query(supplier.get()),Article.class);
        System.out.println("elasaticsearch query is: "+supplier.get().toString());
        return searchResponse;
    }

    /**
     * Function that returns SearchResponse with query processed in supplier6 (matchQuery on articleBody with operator OR)
     */
    public SearchResponse<Article> matchAllQueryOR(String word) throws IOException  {
        Supplier<Query> supplier= ArticleSearchUtil.supplier6(word);
        SearchResponse<Article> searchResponse=elasticsearchClient.search(s->s.index("article1").query(supplier.get()),Article.class);
        System.out.println("elasaticsearch query is: "+supplier.get().toString());
        return searchResponse;
    }

    /**
     * Function that returns SearchResponse with query processed in supplier4 (matchPhraseQuery on heading)
     */
    public SearchResponse<Article> matchPhraseHeading(String word) throws IOException  {
        Supplier<Query> supplier= ArticleSearchUtil.supplier4(word);
        SearchResponse<Article> searchResponse=elasticsearchClient.search(s->s.index("article1").query(supplier.get()),Article.class);
        System.out.println("elasaticsearch query is: "+supplier.get().toString());
        return searchResponse;
    }

    /**
     * Function that returns SearchResponse with query processed in supplierQueryWithWord (fuzzyQuery on articleBody with fuzziness 2)
     */
    public SearchResponse<Article> matchArticleWithWordService(String word) throws IOException  {
        Supplier<Query> supplier= ArticleSearchUtil.supplierQueryWithWord(word);
        SearchResponse<Article> searchResponse=elasticsearchClient.search(s->s.index("article1").query(supplier.get()),Article.class);
        System.out.println("elasaticsearch query is: "+supplier.get().toString());
        return searchResponse;
    }

    /**
     * Function that returns SearchResponse with query processed in supplier2 (matchQuery on heading with fuzziness 1 and operator AND)
     */
    public SearchResponse<Article> matchAllHeadingService(String word) throws IOException  {
        Supplier<Query> supplier= ArticleSearchUtil.supplier2(word);
        SearchResponse<Article> searchResponse=elasticsearchClient.search(s->s.index("article1").query(supplier.get()),Article.class);
        System.out.println("elasaticsearch query is: "+supplier.get().toString());
        return searchResponse;
    }

    /**
     * Function that returns SearchResponse with query processed in headingFuzzySupplier (fuzzyQuery on articleBody with fuzziness 2)
     */
    public SearchResponse<Article> fuzzyHeadingSearch(String word) throws IOException  {
        Supplier<Query> supplier= ArticleSearchUtil.headingFuzzySupplier(word);
        SearchResponse<Article> searchResponse=elasticsearchClient.search(s->s.index("article1").query(supplier.get()),Article.class);
        System.out.println("elasaticsearch query is: "+supplier.get().toString());
        return searchResponse;
    }
}
