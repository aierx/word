package icu.aierx.word.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "word")
public class WordVO {
    @Id
    int topic;

    @Field(store = true,type = FieldType.Text)
    String word;

    @Field(store = true,type = FieldType.Text)
    String accent;

    @Field(store = true,type = FieldType.Text,analyzer = "ik_smart")
    String meanCN;

    @Field(store = true,type = FieldType.Text)
    String freq;

    @Field(store = true,type = FieldType.Text)
    int wordLength;

    @Field(store = true,type = FieldType.Text)
    String category;

    public WordVO(int topic, String word, String accent, String meanCN, String freq, int wordLength) {
        this.topic = topic;
        this.word = word;
        this.accent = accent;
        this.meanCN = meanCN;
        this.freq = freq;
        this.wordLength = wordLength;
    }
}
