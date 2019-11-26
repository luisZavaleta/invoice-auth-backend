package com.facturachida.auth.utils;

public class StaticAttributes {
	
	public static final String MAIL_TOPIC = "sendverificationmail";
	public static final String MAIL_RECEIVE_TOPIC = "recieveverificationmail";
	public static final String MAIL_GROUP_ID = "verification_mail";
	public static final String SEND_MAIL_TO_RESET_PASSWORD_TOPIC = "mail-to-reset-password-topic";
	
	
	public static final String ERROR_EMAIL_MEESSAGE = "You must enter a valid e-mail address.";
	public static final String ERROR_PASSWORD_MATCH_MEESSAGE = "Password and Confirm Password fiels must have the same value.";
	public static final String ERROR_PASSWORD_MIN_LEN="Password must be a least 8 characters long.";
	public static final String ERROR_USERNAME_UNIQUE="You have already created an account with this e-mail, you can only create one account per e-mail.";

}
