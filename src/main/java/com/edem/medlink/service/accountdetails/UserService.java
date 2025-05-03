package com.edem.medlink.service.accountdetails;

import com.edem.medlink.dto.AboutUserResponse;
import com.edem.medlink.dto.GenericResponseMessage;
import com.edem.medlink.dto.UpdateUserProfileRequest;
import org.springframework.security.core.Authentication;
public interface UserService {
    AboutUserResponse aboutMe(Authentication authentication);
    GenericResponseMessage updateUserSettings(UpdateUserProfileRequest request, Authentication authentication);
}
