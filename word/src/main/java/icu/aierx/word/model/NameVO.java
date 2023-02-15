package icu.aierx.word.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NameVO {
    String name;

    int id;

    int seq;
    Date lastuploaddate;

    Date createddate;
    
    public NameVO(String name) {
        this.name = name;
    }
}
