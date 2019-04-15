package com.cache.service;

public class KeyValue {
    int key;
    int value;

    KeyValue (int k, int v) {
       this.key = k;
       this.value = v;
    }

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
