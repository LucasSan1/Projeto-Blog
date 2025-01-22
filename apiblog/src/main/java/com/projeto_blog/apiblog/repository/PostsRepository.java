package com.projeto_blog.apiblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto_blog.apiblog.entity.PostEntity;

public interface PostsRepository extends JpaRepository<PostEntity, Long>{

}
