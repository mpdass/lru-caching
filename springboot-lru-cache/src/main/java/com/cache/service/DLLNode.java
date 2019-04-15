package com.cache.service;

public class DLLNode {
    int key;
    int value;
    DLLNode prev;
    DLLNode next;
    
    DLLNode(int key, int val) {
    	this.key = key;
    	this.value = val;
    }
}
