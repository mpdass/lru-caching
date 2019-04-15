package com.cache.service;

import java.util.HashMap;

//import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LRUCachingServiceController {
    private static int cacheSize = 2;
    private LRUCaching lruCache = new LRUCaching(cacheSize);

    //@Autowired
    //LRUCachingServiceController (int size) {
    	//this.cacheSize = size;
    	//lruCache = new LRUCaching(cacheSize);
    //}

    @RequestMapping(path = "api/v1/put/{key}", method = RequestMethod.PUT)
    public ResponseEntity<Object> addData (@PathVariable int key, @RequestParam(value="value") int value) {        
    	System.out.println("LRUCachingServiceController.addData() - processing put for key: '" + key + "', value: '" + value + "'");
        HashMap<Integer, KeyValue> kv = lruCache.doPut(key, value);
        KeyValue kval = null;
        if (kv.containsKey(1)) {	//   Put has evicted the tail
           kval = kv.get(1);
           return ResponseEntity.ok().body("200\n{\n key: " + kval.key + "\n value: " + kval.value + "\n}\n\n");
        } else {
           kval = kv.get(0);
           return ResponseEntity.ok().body("200\n{\n}\n");
        }
    }

    @RequestMapping(path = "/api/v1/get/{key}", method = RequestMethod.GET)
    public ResponseEntity<Object> getData (@PathVariable int key) {
        System.out.println("LRUCachingServiceController.getData() - processing get for key: '" + key + "'");
	    KeyValue kv = lruCache.doGet(key);
	    if (kv != null) {
           return ResponseEntity.ok().body("200\n{\n key: " + key + "\n value: " + kv.value + "\n}\n\n");
	    } else {
	       return ResponseEntity.status(HttpStatus.NOT_FOUND).body("404\n");
	    }
    }
}
