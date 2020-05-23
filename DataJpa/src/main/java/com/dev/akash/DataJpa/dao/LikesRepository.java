package com.dev.akash.DataJpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.akash.DataJpa.bean.Likes;

public interface LikesRepository extends JpaRepository<Likes,Long>{
	
	

}
