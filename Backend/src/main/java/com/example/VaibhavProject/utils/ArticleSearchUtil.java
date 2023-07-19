package com.example.VaibhavProject.utils;

import co.elastic.clients.elasticsearch._types.query_dsl.*;
import lombok.val;

import java.util.function.Supplier;

public class ArticleSearchUtil {
    /**
     * This supplier function provides matchAllQuery
     */
    public static Supplier<Query> supplier(String word){
        Supplier<Query> supplier=()->Query.of(q->q.match(matchAllQuery(word)));
        return supplier;
    }

    /**
     * This function performs matchAllQuery on articleBody field
     */
    public static MatchQuery matchAllQuery(String word){
        val matchAllQuery=new MatchQuery.Builder();
        return matchAllQuery.field("articleBody").query(word).fuzziness("1").build();
    }

    public static MatchPhraseQuery matchPhraseQuery(String word){
        val matchAllQuery=new MatchPhraseQuery.Builder();
        return matchAllQuery.field("articleBody").query(word).build();
    }

    public static Supplier<Query> supplier3(String word){
        Supplier<Query> supplier=()->Query.of(q->q.matchPhrase(matchPhraseQuery(word)));
        return supplier;
    }

    public static MatchPhraseQuery matchPhraseQueryHeading(String word){
        val matchAllQuery=new MatchPhraseQuery.Builder();
        return matchAllQuery.field("heading").query(word).build();
    }

    public static Supplier<Query> supplier4(String word){
        Supplier<Query> supplier=()->Query.of(q->q.matchPhrase(matchPhraseQueryHeading(word)));
        return supplier;
    }

    /**
     * This function acts as a supplier function of query made in function matchAllQueryHeading
     */
    public static Supplier<Query> supplier2(String word){
        Supplier<Query> supplier=()->Query.of(q->q.match(matchAllQueryHeading(word)));
        return supplier;
    }

    /**
     * This function builds and returns matchquery on heading field
     */
    public static MatchQuery matchAllQueryHeading(String word){
        val matchAllQuery=new MatchQuery.Builder();
        return matchAllQuery.field("heading").query(word).fuzziness("1").build();
    }

    /**
     * This is a supplier function of query made in function matchAllQueryWithWord
     */
    public static Supplier<Query> supplierQueryWithWord(String word){
        Supplier<Query> supplier=()->Query.of(q->q.fuzzy(matchAllQueryWithWord(word)));
        return supplier;
    }

    /**
     * This function builds and returns fuzzyquery on articleBody field
     */
    public static FuzzyQuery matchAllQueryWithWord(String word){
        val matchQuery=new FuzzyQuery.Builder();
        return matchQuery.field("articleBody").value(word).fuzziness("2").build();
    }

    /**
     * This function builds and returns fuzzyquery on heading field
     */
    public static FuzzyQuery headingFuzzySearch(String word){
        val matchQuery=new FuzzyQuery.Builder();
        return matchQuery.field("heading").value(word).fuzziness("2").build();
    }

    /**
     * This function acts as a supplier of query built in function headingFuzzySearch
     */
    public static Supplier<Query> headingFuzzySupplier(String word){
        Supplier<Query> supplier=()->Query.of(q->q.fuzzy(headingFuzzySearch(word)));
        return supplier;
    }
}
