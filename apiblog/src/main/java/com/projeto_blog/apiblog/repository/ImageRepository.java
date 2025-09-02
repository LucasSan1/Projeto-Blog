package com.projeto_blog.apiblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto_blog.apiblog.entity.ImagesEntity;

public interface ImageRepository extends JpaRepository<ImagesEntity, Long>  {

    
}
