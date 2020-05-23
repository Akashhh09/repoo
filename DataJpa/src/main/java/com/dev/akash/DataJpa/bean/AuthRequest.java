package com.dev.akash.DataJpa.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthRequest {
	
	
	private String name;
	private String password;
	private String NewPassword;
	
	

	

}
