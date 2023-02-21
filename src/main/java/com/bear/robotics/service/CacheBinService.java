package com.bear.robotics.service;


import com.bear.robotics.clients.cachebin.CacheBinClientSpec;
import com.bear.robotics.clients.cachebin.CacheBinTestClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CacheBinService {

    private final CacheBinClientSpec cacheBinClient = new CacheBinTestClient();

    public Boolean deposit(Long dollar){
        return cacheBinClient.deposit(dollar);
    }
    public Boolean withdraw(Long dollar){
        return cacheBinClient.withdraw(dollar);
    }
}
