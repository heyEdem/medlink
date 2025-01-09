package com.edem.medlink.util;

public class Validator {
    public static final String EMAIL_NOT_NULL = "Email is required";
    public static final String EMAIL_NOT_BLANK = "Email cannot be blaNk";

    public static final String NAME_NOT_NULL = "Name is required";
    public static final String NAME_NOT_BLANK = "Name cannot be blank";

    public static final String PASSWORD_NOT_BLANK = "Password is required";
    public static final String PASSWORD_NOT_NULL = "Password is required";

    public static final String PASSWORD_SIZE = "Password should be between 8 to 32 characters";

    public static final String FIELD_IS_REQUIRED = "This field is required";

    public static final int PASSWORD_MIN_LENGTH = 8;
    public static final int PASSWORD_MAX_LENGTH = 32;

    public static final String VERIFICATION_FAILED = "Something went wrong, could not send email";
    public static final String NOT_FOUND_MSG = "email is not valid";

    public static final String VERIFICATION_FAILED_MESSAGE = "Could not verify this OTP";
    public static final String EMAIL_MISMATCH_MESSAGE = "Could not verify email for this OTP";
    public static final String OTP_EXPIRED_MESSAGE = "OTP expired";
}
