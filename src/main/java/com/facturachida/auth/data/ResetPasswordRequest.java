package com.facturachida.auth.data;

import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import com.facturachida.auth.data.validation.annotation.MatchPasswords;
import com.facturachida.auth.utils.StaticAttributes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
@MatchPasswords(password="password", confirmPassword="confirmPassword", message=StaticAttributes.ERROR_PASSWORD_MATCH_MEESSAGE)
public class ResetPasswordRequest {
	
	String token;
	
	@Size(min = 8, message=StaticAttributes.ERROR_PASSWORD_MIN_LEN)
	String password;
	
	String confirmPassword;

}
