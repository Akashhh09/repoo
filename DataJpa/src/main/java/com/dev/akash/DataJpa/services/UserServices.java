package com.dev.akash.DataJpa.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.zip.Deflater;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import com.dev.akash.DataJpa.bean.Admin;
import com.dev.akash.DataJpa.bean.AuthRequest;
import com.dev.akash.DataJpa.bean.Chat;
import com.dev.akash.DataJpa.bean.Likes;
import com.dev.akash.DataJpa.bean.User;
import com.dev.akash.DataJpa.dao.AdminRepository;
import com.dev.akash.DataJpa.dao.ChatRepository;
import com.dev.akash.DataJpa.dao.DataRepository;
import com.dev.akash.DataJpa.dao.LikesRepository;
import com.dev.akash.DataJpa.mail.MailConfig;
import com.dev.akash.DataJpa.mail.SmsConfig;


@Service
public class UserServices implements UserDetailsService{


	private String Name;
	private static String OTP;
	@Autowired
	DataRepository repo;
	
	@Autowired
	LikesRepository LikesRepo;
	
	@Autowired
	AdminRepository AdminRepo;

	@Autowired
	private SmsConfig smsConfig;
	
	@Autowired
	ChatRepository ChatRepo;

	
	  public Admin adminLogin(Admin admin) { return
	  AdminRepo.findByNameAndPassword(admin.getName(), admin.getPassword()); 
	  }
	 
	
	
	public User addUser(User user) {
		Boolean state;
		try {
			repo.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;

	}

	public Optional<User> getUserById(long Id) {
		return repo.findById(Id);

	}
	
	//GETLOGGEDUSER DATA
	
	public User getLoggedUserData(String name) {
		return repo.findByName(name);
	}


	/*
	 * public User getUserByUserName(User user) { return
	 * repo.findByName(user.getName()); }
	 */



	public User updateUser(User newUser,long Id) {
		Optional<User> user = repo.findById(Id);

		if(user.isPresent()) {
			User _user = user.get();
			_user.setName(newUser.getName());
			_user.setPassword(newUser.getPassword());
			return repo.save(_user);
		}else {
			return null;
		}


	}

	//Get All User
	public List<User> getAllUser(){
		return repo.findAll();
	}

	//Get By Name

	public User getUserByName(String Name){
		User user= repo.findByName(Name);
		if (user != null) {
			return user;

		} else {
			throw new EntityNotFoundException("User Not Found By This Name");

		}
	}


	//Login User

	/*
	 * public User loginUser(User user) { return
	 * repo.findByNameAndPassword(user.getName(),user.getPassword());
	 * 
	 * }
	 */


	// LOGIC TO GENERATE OTP

	public String generateOtp()
	{
		char[] otp = new char[6];
		Random obj = new Random();
		//String to = email;

		for(int i= 0;i<6;i++) {
			otp[i]  = (char)(obj.nextInt(10)+48);
		}

		String otp1 = new String(otp);
		return otp1;

	}

	//forgot password



	@Autowired
	public MailConfig mail;


	public String forgotPassword(String email) {
		String otp = mail.forgotPassword(email);

		OTP = otp;
		return otp;
	}

	public Boolean verifyOtp(String otp) {
		if (otp.contentEquals(OTP)) {
			return true;
		} else {

			return false;
		}

	}

	// SMS for OTP

	public String sendSms(String number) {
		String genOtp = generateOtp();
		String otp= smsConfig.sendSms(number, genOtp);
		//System.out.println(number+genOtp);
		return otp;


	}






	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		User user = repo.findByName(name);
		return new org.springframework.security.core.userdetails.User(user.getName(),user.getPassword(),new ArrayList<>());
	}

	//Update Password
	public User updatePassword(AuthRequest authRequest) {
		Optional<User> newUser = repo.findByNameAndPassword(authRequest.getName(), authRequest.getPassword());
		System.out.println(newUser);
		System.out.println(authRequest);
		if(newUser.isPresent()) {
			User updateUser = newUser.get();
			System.out.println(updateUser.getEmail());

			updateUser.setName(updateUser.getName());
			updateUser.setPassword(authRequest.getNewPassword());
			updateUser.setEmail(updateUser.getEmail());
			updateUser.setPhone(updateUser.getPhone());
			System.out.println(updateUser);
			return  repo.save(updateUser);

		}
		else {
			throw new EntityNotFoundException();

		}



	}


	// GET LOGGED USER Name
	public String loggedUserName(User user) {
		Name = user.getName();
		return "";
	}
	
	

	//UPDATE USER PIC
	public String uploadImage(MultipartFile file) throws IOException {
		Optional<User> newUser = repo.findByNam(Name);
		System.out.println(newUser);

		if(newUser.isPresent()) {
			User updateUser = newUser.get();

			updateUser.setPicByte(compressBytes(file.getBytes()));

			System.out.println(updateUser);
			repo.save(updateUser);
			return "Success";

		}
		else {
			throw new EntityNotFoundException();
			

		}


	} 

	public static byte[] compressBytes(byte[] data) {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);		 
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		} catch (IOException e) {
		}
		System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
		return outputStream.toByteArray();

	}
	
	public List<User> findByGender(String Gender){
		
		return repo.findByGender(Gender);
		
	}

	//GET LOGGED USERID
	
	public long findIdByName(String Name)
	{
		return repo.findIdByName(Name);
	}
	
	//
	
	public String addLike(Likes likes) {
		LikesRepo.save(likes);
		return "success";
	}
	
	// DELETE ACCOUNT
	public void deleteByNameAndPassword(String name,String password) {
		System.out.println(name);
		System.out.println(password);
		 repo.deleteByNameAndPassword(name,password);
		
	}
	
	
	//Chat
	
	public String sendMsg(Chat chat) {
		ChatRepo.save(chat);
		return "Message Sent";
	}
	
	public List<Chat> getMsg(long receiverId){
		return ChatRepo.findByReceiverId(receiverId);
	}





}
