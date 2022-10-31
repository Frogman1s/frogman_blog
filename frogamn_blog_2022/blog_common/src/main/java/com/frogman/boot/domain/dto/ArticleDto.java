package com.frogman.boot.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {
    private String title;
    private String content;
    private String summary;
    private Long categoryId;
    private String thumbnail;
    private String isTop;
    private String status;
    private String isComment;
    private List<Long> tags;
}
