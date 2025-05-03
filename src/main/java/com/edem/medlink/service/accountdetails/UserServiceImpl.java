package com.edem.medlink.service.accountdetails;

import com.edem.medlink.dto.AboutUserResponse;
import com.edem.medlink.dto.GenericResponseMessage;
import com.edem.medlink.dto.UpdateUserProfileRequest;
import com.edem.medlink.entities.User.User;
import com.edem.medlink.exception.UserNotFoundException;
import com.edem.medlink.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import static com.edem.medlink.util.Validator.PROFILE_UPDATE_SUCCESS;
import static com.edem.medlink.util.Validator.USER_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public AboutUserResponse aboutMe(Authentication authentication) {
        User user = getUser(authentication);
        return getAboutUser(user);
    }

    @Override
    @Transactional
    public GenericResponseMessage updateUserSettings(UpdateUserProfileRequest request, Authentication authentication) {
        User user = getUser(authentication);

        if (request.firstName() != null && !request.firstName().isEmpty())
            user.setFirstname(request.firstName());

        if (request.lastName() != null && !request.lastName().isEmpty())
            user.setLastname(request.lastName());

        if (request.contact() != null && !request.contact().isEmpty())
            user.setContact(request.contact());

        if (request.bio() != null && !request.bio().isEmpty())
            user.setBio(request.bio());

        if (request.clinic() != null && !request.clinic().isEmpty())
            user.setClinic(request.clinic());

        if (request.specialty() != null && !request.specialty().isEmpty())
            user.setSpecialty(request.specialty());

        if (request.qualification() != null && !request.qualification().isEmpty())
            user.setQualification(request.qualification());

        if (request.dob() != null)
            user.setDob(request.dob());

        if (request.emergency_number() != null && !request.emergency_number().isEmpty())
            user.setEmergency_number(request.emergency_number());

        if (request.medical_history() != null && !request.medical_history().isEmpty())
            user.setMedical_history(request.medical_history());

        if (request.digital_address() != null && !request.digital_address().isEmpty())
            user.setDigital_address(request.digital_address());

        // Save the updated user details
        userRepository.save(user);

        return new GenericResponseMessage(PROFILE_UPDATE_SUCCESS);
    }

    private User getUser(Authentication authentication){
        return userRepository.findUserByEmailAndVerified(authentication.getName()).orElseThrow(()-> new UserNotFoundException(USER_NOT_FOUND));
    }

    private static AboutUserResponse getAboutUser(User user){
        return AboutUserResponse.builder()
                .firstName(user.getFirstname())
                .lastName(user.getLastname())
                .bio(user.getBio())
                .clinic(user.getClinic())
                .email(user.getEmail())
                .medical_history(user.getMedical_history())
                .qualification(user.getQualification())
                .contact(user.getContact())
                .digital_address(user.getDigital_address())
                .emergency_number(user.getEmergency_number())
                .clinic(user.getClinic())
                .roles(user.getRole())
                .build();
    }
}
