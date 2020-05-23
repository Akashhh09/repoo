package com.dev.akash.DataJpa.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dev.akash.DataJpa.bean.Admin;
import com.dev.akash.DataJpa.bean.AuthRequest;
import com.dev.akash.DataJpa.bean.Chat;
import com.dev.akash.DataJpa.bean.Likes;
import com.dev.akash.DataJpa.bean.User;
import com.dev.akash.DataJpa.exceptions.MyExceptions;
import com.dev.akash.DataJpa.services.UserServices;
import com.dev.akash.DataJpa.util.JwtUtil;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class HomeController {

	static long Id;
	static String Name;
	static String Password;
	static String Gender;
	static String Email;
	static long Phone;

	@Autowired
	UserServices services;

	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome";
	}

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;



	@PostMapping("/adminLogin") public Admin adminLogin(Admin admin) { return
			services.adminLogin(admin);
	}

	@PostMapping("/authenticate")
	public String generateToken(@RequestBody User user) throws Exception {
		try {
			System.out.println(user.getName());
			System.out.println(user.getPassword());

			authenticationManager
			.authenticate(new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword())

					);

		} catch (Exception ex) {
			throw new Exception("inavalid username/password");
		}
		return jwtUtil.generateToken(user.getName());

	}

	@GetMapping("/getAllUser")
	public List<User> getAllUser() {
		return services.getAllUser();
	}

	@PostMapping("/addUser")
	public User addUser(@RequestBody User user) throws MyExceptions {
		services.addUser(user);
		return user;
	}

	@GetMapping("/getUserById/{Id}")
	public Optional<User> getUser(@PathVariable long Id) {
		System.out.println(Id);
		return services.getUserById(Id);

	}

	@PostMapping("/getUserByName")
	public User getUserByUserName(@RequestBody User user) {
		return services.getUserByName(user.getName());
	}

	@PutMapping("/updateUser/{Id}")
	public User updateUser(@RequestBody User newUser, @PathVariable long Id) {

		return services.updateUser(newUser, Id);

	}

	// Login User
	@GetMapping("/getLoggedUserData/{name}")
	public User getLoggedUserData(@PathVariable String name) {
		System.out.println(name);
		return services.getLoggedUserData(name);
	}

	public User getUserByName(String Name) {
		return services.getUserByName(Name);
	}

	@PostMapping("/loginUser")
	public String loginUser(@RequestBody User user) throws Exception {
		services.loggedUserName(user);
		try {
			
			authenticationManager
			.authenticate(new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword())

					
					);
			
		} catch (Exception ex) {
			throw new Exception("inavalid username/password");
		}

		User NewUser = getUserByName(user.getName());
		Id = NewUser.getId();
		Name = NewUser.getName();
		Password = NewUser.getPassword();
		Gender = NewUser.getGender();
		Email = NewUser.getEmail();
		Phone = NewUser.getPhone();

		System.out.println(Name);

		return jwtUtil.generateToken(user.getName());

	}

	// Update Password

	@PutMapping("/updatePassword")
	public User updatePassword(@RequestBody AuthRequest authRequest) {
		// System.out.println(authRequest);
		authRequest.setName(Name);
		return services.updatePassword(authRequest);

	}

	/*
	 * //MAIL SENDING
	 * 
	 * @Autowired private MailConfig mail;
	 * 
	 * @GetMapping("/sendMail") public String sendmaill() { return mail.sendMail();
	 * 
	 * }
	 */

	/*
	 * //OTP GENEATION
	 * 
	 * @GetMapping("/getOtp") public char[] genarateOtp() { return
	 * services.generateOtp(6); }
	 */

	// FORGOT PASSWORD

	@GetMapping("/resetPassword/{email}")
	public String resetPassword(@PathVariable String email) {
		String otp = services.forgotPassword(email);
		return otp;
	}

	// Verify OTP

	@GetMapping("/verifyOtp/{otp}")
	public Boolean verifyOtp(@PathVariable String otp) {

		return services.verifyOtp(otp);

	}

	// SMS

	@PostMapping("/sendSms/{number}")
	public String sendSms(@PathVariable String number) {
		System.out.println(number);

		String otp = services.sendSms(number);
		return otp;
	}

	@PostMapping("/uploadImage")
	public String uploadImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
		System.out.println(file.getBytes());

		services.uploadImage(file);

		return "Success";

	}

	@GetMapping("/getUserByGender/{Gender}")
	public List<User> findByGender(@PathVariable String Gender) {
		return services.findByGender(Gender);
	}


	@GetMapping("addLikes/{likedId}")
	public String addLike(@PathVariable long likedId) {
		Likes likes = new Likes();
		likes.setLiked(likedId);
		likes.setUser_id(Id);
		return services.addLike(likes);

	}


	@DeleteMapping("/deleteAccount/{name}/{password}")
	public void deleteByNameAndPassword(@PathVariable String name,@PathVariable String password) {
		System.out.println(name);
		System.out.println(password);
		
		services.deleteByNameAndPassword(name,password);
	}
	
	
	//CHAT
	@PostMapping("/sendMsg")
	public String sendMsg(@RequestBody Chat chat) {
		return services.sendMsg(chat);
		
	}
	
	@GetMapping("/getMsg/{receiverId}")
	public List<Chat> getMsg(@PathVariable long receiverId) {
		return services.getMsg(receiverId);
	}

}
