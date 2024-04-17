package com.example.petbackend.pojo;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "case_index")
public class CaseIndex {
    @Id
    private Integer id;

    @Field(type = FieldType.Text)
    private String username;

    @Field(type = FieldType.Text)
    private String cate_name;

    @Field(type = FieldType.Text)
    private String ill_name;

    @Field(type = FieldType.Text)
    private String basic_situation;

    @Field(type = FieldType.Text)
    private String result;

    @Field(type = FieldType.Text)
    private String therapy;
}
