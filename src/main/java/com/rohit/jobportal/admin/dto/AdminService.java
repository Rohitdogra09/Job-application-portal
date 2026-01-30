package com.rohit.jobportal.admin.dto;


import com.rohit.jobportal.entity.User;
import com.rohit.jobportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createRecruiter(CreateRecruiterRequest req){
        if(userRepository.existsByEmail(req.email())){
            throw new IllegalArgumentException("Email already exists: "+ req.email());

        }
        User recuiter = new User();
        recuiter.setEmail(req.email());
        recuiter.setFullName(req.fullName());
        recuiter.setPasswordHash(passwordEncoder.encode(req.password()));

        //important : same style as our JWT -> scope string
        recuiter.setScope("ROLE_RECRUITER FACTOR_PASSWORD");



        recuiter.setEnabled(true);

        return userRepository.save(recuiter);

    }
}
