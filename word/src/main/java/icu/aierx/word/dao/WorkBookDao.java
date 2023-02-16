package icu.aierx.word.dao;

import icu.aierx.word.model.NameVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkBookDao {

    List<NameVO> findAll();

    NameVO findByName(@Param("name") String name);

    int save(@Param("nameVO") NameVO nameVO);

    int queryMaxId();
}
