package com.academy.base.config;

import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
@ConditionalOnProperty(name = "spring.enable.caching")
public class CacheConfiguration {

	public CacheManagerCustomizer<ConcurrentMapCacheManager> customizer() {
		return new ConcurrentCustomizer();
	}

	class ConcurrentCustomizer implements CacheManagerCustomizer<ConcurrentMapCacheManager> {

		@Override
		public void customize(final ConcurrentMapCacheManager cacheManager) {
			cacheManager.setAllowNullValues(false);
			cacheManager.setStoreByValue(true);
		}
	}
}
