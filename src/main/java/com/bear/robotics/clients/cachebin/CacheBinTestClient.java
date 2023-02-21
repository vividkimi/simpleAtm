package com.bear.robotics.clients.cachebin;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheBinTestClient implements CacheBinClientSpec {


    @Override
    public Boolean deposit(Long dollar) {
        return Boolean.TRUE;
    }

    @Override
    public Boolean withdraw(Long dollar) {
        return Boolean.TRUE;
    }
}
