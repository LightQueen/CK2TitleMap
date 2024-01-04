package com.example.accessingdatajpa.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Callable;

@Service
public class TransactionService {
    @Transactional(propagation = Propagation.REQUIRED) // 默认的传播行为
    public<T> T doInTransaction(Callable<T> callback) {
        try{
            return callback.call();
        } catch ( Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
