package com.bear.robotics.clients.cachebin;

public interface CacheBinClientSpec {
    Boolean deposit(Long dollar);
    Boolean withdraw(Long dollar);
}
