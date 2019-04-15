package com.cache.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootLruCacheApplication {
	//static LRUCachingServiceController lruCtrl = null;

	public static void main(String[] args) {
		if (args.length == 0) {
           System.out.println("SpringbootLruCacheApplication.main() - Missing Cache Size");
           //System.exit(-1);
	    }
		//lruCtrl = new LRUCachingServiceController(Integer.parseInt(args[0]));
		SpringApplication.run(SpringbootLruCacheApplication.class, args);
	}
}
