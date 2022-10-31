package com.frogman.boot.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleListVo {
    private Long id;

    private String title;

    private String summary;

    private String thumbnail;

    private Long viewCount;

    private Date createTime;

    private String categoryName;


}
