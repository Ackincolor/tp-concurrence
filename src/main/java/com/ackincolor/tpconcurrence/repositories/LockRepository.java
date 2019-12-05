package com.ackincolor.tpconcurrence.repositories;

import com.ackincolor.tpconcurrence.entities.Lock;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LockRepository extends MongoRepository<Lock,String> {
    public Lock findByLockId(String lockId);
}
