package com.globant.auth.authserver.repository;

import com.globant.auth.authserver.Entity.User;
import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, String> {

    @Query("#{#n1ql.selectEntity} USE KEYS [\"#{#userName}\"]")
    User findByUserName(@Param("userName") String userName);

}
