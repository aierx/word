package icu.aierx.word.controller;

import com.alibaba.fastjson.JSON;
import icu.aierx.word.dao.WorkBookDao;
import icu.aierx.word.model.NameVO;
import icu.aierx.word.model.PageVO;
import icu.aierx.word.model.ResultVO;
import icu.aierx.word.util.CommonUtil;
import icu.aierx.word.dao.WordRepository;
import icu.aierx.word.model.WordVO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
public class QueryController {

    @Autowired
    WordRepository wordRepository;

    @Autowired
    WorkBookDao workBookDao;

    @Autowired
    RestHighLevelClient client;

    @SneakyThrows
    @CrossOrigin
    @GetMapping(value = "query")
    public PageVO<WordVO> queryByPrefix(@RequestParam String keyword, @RequestParam String category,@RequestParam int pageSize,@RequestParam int curpage){
        SearchRequest searchRequest = new SearchRequest("word");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(pageSize);
        searchSourceBuilder.from(curpage-1);
        List<QueryBuilder> builders = new ArrayList<>();
        builders.add(QueryBuilders.matchQuery("category",category));
        if( CommonUtil.isEnglish(keyword)){
            builders.add(QueryBuilders.wildcardQuery("word", keyword));
        }else {
            builders.add(QueryBuilders.matchQuery("meanCN", keyword));
        }
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must().addAll(builders);
        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] hits = search.getHits().getHits();
        List<WordVO> wordVOList = new ArrayList<>();
        for (SearchHit documentFields : hits) {
            String json = documentFields.getSourceAsString();
            WordVO jobDetail = JSON.parseObject(json, WordVO.class);
            wordVOList.add(jobDetail);
        }
        long totalNum = search.getHits().getTotalHits().value;
        PageVO<WordVO> pageVO = new PageVO(0,totalNum);
        pageVO.setData(wordVOList);
        return  pageVO;
    }

    @GetMapping("/count")
    @CrossOrigin
    public ResultVO getCount(@RequestParam String category){
        int count = wordRepository.countByCategory(category);
        return ResultVO.success(count);
    }

    @CrossOrigin
    @GetMapping("name")
    public List<NameVO> queryName(){
        return workBookDao.findAll();
    }
}
