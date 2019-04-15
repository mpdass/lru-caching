package com.cache.service;

import java.util.HashMap;

public class LRUCaching {
    private int maximum;
    private HashMap<Integer, DLLNode> itemMap = new HashMap<Integer, DLLNode>();
    private DLLNode head = null;
    private DLLNode tail = null;
    
    public LRUCaching(int max) {
    	this.maximum = max;
    }

    public boolean remove(DLLNode data) {
    	boolean evicted = false;
        if(data.prev!= null) {
            data.prev.next = data.next;
        } else {
            head = data.next;
        }
        
        if(data.next != null) {
            data.next.prev = data.prev;
        } else {  // tail is removed
            tail = data.prev;
            evicted = true;
        }
        return evicted;
    }

    // Add given node to the head of the linked list
    private void addToHead(DLLNode node) {
        System.out.println("addToHead() - node key: '" + node.key + " with value: '" + node.value + "'");
        node.next = head;
        node.prev = null;
        
        if (head != null) {
           head.prev = node;
        }
        head = node;
        
        if (tail == null)
           tail = head;
        System.out.println("addToHead() - itemMap.size: '" + itemMap.size() + "'");
    }

    // Function to handle 'PUT' request from client
    public HashMap<Integer, KeyValue> doPut(int key, int val) {
    	boolean evicted = false;
    	DLLNode node = null;
    	HashMap<Integer, KeyValue> result = new HashMap<Integer, KeyValue>();
    	/**
    	 *  if key is in itemMap
    	 *     get node;
    	 *     update the node
    	 *     put the node to the map
    	 *     remove from the list 
    	 *     put it to head
    	 *  else if itemMap.size == maximum
    	 *          remove tail
    	 *          setHead(item)
    	 */
        System.out.println("put() - insert key: '" + key + " with value: '" + val + "'");
    	if (itemMap.containsKey(key)) {
           System.out.println("put() - key: '" + key + " is in itemMap");
     	   node = itemMap.get(key);
           node.value = val;
           evicted = remove(node);
     	   addToHead (node);
     	} else {
            System.out.println("put() - key: '" + key + "' is not in itemMap");
            node = new DLLNode(key, val);
            if (itemMap.size() >= maximum) {
               itemMap.remove(tail.key);
               node.key = tail.key;
               evicted = remove(tail);
     	       addToHead(node);
            } else {
               addToHead(node);
            }
     	    itemMap.put(key, node);
     	}
  	   if (evicted) {
  		  result.put(1, new KeyValue(node.key, node.value));
  	   } else {
  		  result.put(0, new KeyValue(node.key, node.value));
  	   }
    	return result;
    }

    // Function to handle 'GET' request from the client
    public KeyValue doGet(int key) {
        DLLNode node = null;
    	if (itemMap.containsKey(key)) {   	   
    	   node = itemMap.get(key);
           remove(node);
           addToHead(node);
           //value = node.value;
    	} else {
     	   return null;
    	}
    	return new KeyValue(key, node.value);
    }
/**
    // Stand alone function to test functions in this class
    public static void main(String[] argv) {
    	LRUCaching lru = new LRUCaching(3);
    	lru.put(1, 1*2);
    	lru.put(2, 2*2);
    	lru.put(3, 3*2);
    	lru.put(4, 4*2);
    	lru.put(1, 5*2);
    	lru.put(2, 6*2);
    	lru.put(5, 7*2);
    	lru.put(1, 8*2);
    	System.out.println("main() - itemMap size: '" + lru.itemMap.size() + "'");
    	for (DLLNode node = lru.head; node != null; node = node.next) {
    		System.out.println("main() - key: '" + node.key + "; value: '" + node.value + "'");
    	}
    }
**/
}
