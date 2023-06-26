package cuong.run.config;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import jakarta.annotation.PostConstruct;


@Configuration
@EnableCaching
@EnableScheduling
public class CacheConfig {
    private CacheManager cacheManager;
    private Map<String, String> mapCache = new HashMap<>();

    @Bean
    public CacheManager cacheManager() {
    	ConcurrentMapCacheManager concurrentMapCacheManager 
    			= new ConcurrentMapCacheManager();
    	concurrentMapCacheManager.setCacheNames(mapCache.values());
    	concurrentMapCacheManager.setAllowNullValues(true);
        this.cacheManager = concurrentMapCacheManager;

        return this.cacheManager;
    }

    @Scheduled(fixedDelay = 360000) // Xóa cache sau mỗi 360000 giây (6 phút)
    public void evictCache() {
    	Collection<String> caches = cacheManager.getCacheNames();
    	for (String name : caches) {
    		if(name.equals("productDate") || name.equals("productPrice")
    				|| name.equals("productSearch") || name.equals("categories")
    				|| name.equals("orderDetail") || name.equals("orderStatus")) {
    			cacheManager.getCache(name).clear();;
    		}
        }
    }
    
    @PostConstruct
    public void add() {
    	mapCache.put("product", "product");
    	mapCache.put("productDate", "productDate");
    	mapCache.put("productPrice", "productPrice");
    	mapCache.put("productSearch", "productSearch");
    	mapCache.put("categories", "categories");
    	mapCache.put("orderDetail", "orderDetail");
    	mapCache.put("orderStatus", "orderStatus");
    }
}
