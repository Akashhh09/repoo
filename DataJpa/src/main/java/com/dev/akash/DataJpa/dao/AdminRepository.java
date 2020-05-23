package com.dev.akash.DataJpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dev.akash.DataJpa.bean.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long>{
	
	@Query("from Admin where name=?1 and password=?2")
	public Admin findByNameAndPassword(String name,String Passowrd);

}
