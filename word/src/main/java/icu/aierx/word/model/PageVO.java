package icu.aierx.word.model;

import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

@Data
public class PageVO<T> {
    List<T> data;
    long pageNum;
    long totalSize;

    public PageVO(long curPage, long totalSize) {
        this.pageNum = curPage;
        this.totalSize = totalSize;
    }
}
