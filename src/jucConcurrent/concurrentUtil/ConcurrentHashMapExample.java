package jucConcurrent.concurrentUtil;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapExample {
    public static void main(String[] args) {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("key1", 1);
        map.put("key2", 2);

        map.forEach((k, v) -> System.out.println(k + ":" + v));

        map.computeIfPresent("key3", (k, v) -> v + 1);
    }
}
