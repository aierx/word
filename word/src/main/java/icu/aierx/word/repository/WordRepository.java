package icu.aierx.word.repository;

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

    int countByCategory(String category);

}