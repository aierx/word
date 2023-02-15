package icu.aierx.word.dao;

import icu.aierx.word.model.WordVO;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author aleiw
 */
@Component
public interface WordRepository extends Repository<WordVO, Integer> {

  @Query("{\n" +
          "  \"bool\": {\n" +
          "    \"must\": [\n" +
          "      {\"wildcard\": {\"word\": \"?0\"}},\n" +
          "      {\"match\": {\"category\": \"?1\"}}\n" +
          "    ]\n" +
          "  }\n" +
          "}")
  List<WordVO> queryByVague(String keyword,String category);


  @Query("{\n" +
          "  \"bool\": {\n" +
          "    \"must\": [\n" +
          "      {\"match\": {\"meanCN\": \"?0\"}},\n" +
          "      {\"match\": {\"category\": \"?1\"}}\n" +
          "    ]\n" +
          "  }\n" +
          "}")
  List<WordVO> queryByCN(String keyword,String category);

  int countByCategory(String category);

}