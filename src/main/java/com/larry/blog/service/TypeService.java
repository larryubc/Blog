package com.larry.blog.service;

import com.larry.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {
    //create
    Type saveType(Type type);

    Type getTypeByName(String name);

    //research
    Type getType(Long id);

    Page<Type> listType(Pageable pageable);//seprate page research


    //update
    Type updateType(Long id,Type type);

    //delete

    List<Type> listTypeTop(Integer size);

    List<Type> listType();

    void deleteType(Long id);




}
