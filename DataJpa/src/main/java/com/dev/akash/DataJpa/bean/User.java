package com.dev.akash.DataJpa.bean;

import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "user")
//@Table(uniqueConstraints={@UniqueConstraint(columnNames={"name"}),@UniqueConstraint(columnNames={"email"}),@UniqueConstraint(columnNames={"phone"})})
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private long Id;

	
	//@NotNull
	@Size(max=50)
	@Column(name="name",nullable=false,unique = true)
	private String name;

	@NotNull
	private String password;

	
	//@NotNull
	@Size(max=50)
	@Email
	@Column(name="email",nullable=false,unique = true)
	private String email;

	
	//@NotNull
	@Column(name="phone",unique = true,nullable=false)
	private long phone;
	
	
	@NotNull
	private String Gender;

	@Column(name = "picByte", length = 1000)
	private byte[] PicByte;
	
	
	  @OneToMany(cascade = CascadeType.REMOVE)
	  @JoinColumn(name = "user_id",referencedColumnName ="user_id")
	  private List<Likes> likes;
	  
	 

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public byte[] getPicByte() {
		return PicByte;
	}

	public void setPicByte(byte[] picByte) {
		PicByte = picByte;
	}

	@Override
	public String toString() {
		return "User [Id=" + Id + ", name=" + name + ", password=" + password + ", email=" + email + ", phone=" + phone
				+ ", Gender=" + Gender + ", PicByte=" + Arrays.toString(PicByte) + "]";
	}

	
	

	
}
