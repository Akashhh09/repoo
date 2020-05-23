package com.dev.akash.DataJpa.dao;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dev.akash.DataJpa.bean.User;


@Repository
@Transactional
public interface DataRepository extends JpaRepository<User, Long> {
	

	@Query("from User where name=?1 AND password=?2")
	public Optional<User> findByNameAndPassword(String name,String password);

	@Query("from User where name = ?1")
	public User findByName(String name);
	
	@Query("from User where name = ?1")
	public Optional<User> findByNam(String username);
	
	public List<User> findAll();
	
	@Query("from User where gender= ?1")
	public List<User> findByGender(String Gender);
	
	@Query("select id from User where name = ?1")
	public long findIdByName(String name);
	
	public void deleteByNameAndPassword(String name,String Password);
	
	
	
	 
	

}
