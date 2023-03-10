package icu.aierx.word.controller;

import icu.aierx.word.repository.WordRepository;
import icu.aierx.word.dao.WorkBookDao;
import icu.aierx.word.model.NameVO;
import icu.aierx.word.model.ResultVO;
import icu.aierx.word.model.WordVO;
import icu.aierx.word.util.CommonUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@RestController
@Slf4j
@CrossOrigin
public class SyncController {

    @Autowired
    WordRepository wordRepository;

    @Autowired
    WorkBookDao workBookDao;


    private final ElasticsearchOperations elasticsearchOperations;

    public SyncController(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @PostMapping("/word")
    public String save(@RequestBody WordVO word) {
        IndexQuery indexQuery = new IndexQueryBuilder()
                .withId(String.valueOf(word.getTopic()))
                .withObject(word)
                .build();
        return elasticsearchOperations.index(indexQuery, IndexCoordinates.of("word"));
    }

    @GetMapping("/word/{id}")
    public WordVO findById(@PathVariable("id") Long topic) {
        WordVO word = elasticsearchOperations
                .queryForObject(GetQuery.getById(topic.toString()), WordVO.class);
        return word;
    }

    @SneakyThrows
    @PostMapping("/upload")
    @CrossOrigin
    @Transactional
    public ResultVO upload(@RequestParam MultipartFile file, @RequestParam String name) {
        //??????????????????
        String OriginalFilename = file.getOriginalFilename();
        // ??????????????????????????????
        if (workBookDao.findByName(name) != null) {
            return ResultVO.error(name + " is existence");
        }
        // ????????????????????????????????????
        workBookDao.save(new NameVO(name));
        int id = workBookDao.queryMaxId();
        File temp = File.createTempFile("temp", null);
        file.transferTo(temp);
        List<WordVO> words = CommonUtil.readExcel(temp, name, id);
        elasticsearchOperations.save(words);
        temp.deleteOnExit();
        return new ResultVO();
    }
}
