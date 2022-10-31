package com.frogman.boot.runner;

import com.frogman.boot.domain.entity.Article;
import com.frogman.boot.service.ArticleService;
import com.frogman.boot.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/***
 * 程序开始时读取浏览量存入redis
 */
@Component
public class ViewCountRunner implements CommandLineRunner {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private RedisCache redisCache;
    @Override
    public void run(String... args) throws Exception {
        //查询博客信息  id viewcount
        List<Article> articleList = articleService.list();
        Map<String, Integer> map = articleList.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(), article -> article.getViewCount().intValue()));
        //存储到redis
        redisCache.setCacheMap("viewCount:",map);
    }
}
