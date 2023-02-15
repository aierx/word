package icu.aierx.word.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVO {
    int code;

    String msg;

    Object t;

    public static ResultVO error(String msg) {
        return new ResultVO(500, msg, null);
    }

    public static ResultVO success(Object t) {
        return new ResultVO(200, "success", t);
    }
}
