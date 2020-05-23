package com.dev.akash.DataJpa.bean;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.stereotype.Component;


@Entity
@Table(name = "likes")
public class Likes{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;


	/*
	 * @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY, optional =
	 * false)
	 * 
	 * @JoinColumn(name="user_id", nullable = false) private User user;
	 */
	private long liked;
	
	private long user_id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getLiked() {
		return liked;
	}

	public void setLiked(long liked) {
		this.liked = liked;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	@Override
	public String toString() {
		return "Likes [id=" + id + ", liked=" + liked + ", user_id=" + user_id + "]";
	}
	

	
	



}
