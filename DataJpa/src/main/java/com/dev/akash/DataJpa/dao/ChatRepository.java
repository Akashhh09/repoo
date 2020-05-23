package com.dev.akash.DataJpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dev.akash.DataJpa.bean.Chat;

public interface ChatRepository extends JpaRepository<Chat,Long> {
	
	@Query("from Chat where receiverId = ?1")
	public List<Chat> findByReceiverId(long receiverId);

}
