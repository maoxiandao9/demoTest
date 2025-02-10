package com.amumu.springbootdemo.repository;

import com.amumu.springbootdemo.pojo.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository // spring bean
public interface UserRepository extends CrudRepository<User, Integer> {
}
