package com.frogman.boot.job;

import com.frogman.boot.constants.SystemConstants;
import com.frogman.boot.domain.entity.Article;
import com.frogman.boot.service.ArticleService;
import com.frogman.boot.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UpdateViewCountJob {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ArticleService articleService;

    @Scheduled(cron = "0/60 * * * * ?")
    public void updateViewCountJob(){
        //从redis获取viewCount
        Map<String, Integer> map = redisCache.getCacheMap(SystemConstants.VIEW_COUNT);
        List<Article> articles = map.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());
        //存入数据库
        articleService.updateBatchById(articles);
    }
}
