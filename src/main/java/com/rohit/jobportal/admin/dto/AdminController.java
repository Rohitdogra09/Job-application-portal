package com.rohit.jobportal.admin.dto;


import com.rohit.jobportal.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @PostMapping("/recruiters")
    public Map<String, Object> createRecruiter(@Valid @RequestBody CreateRecruiterRequest req){

        User saved = adminService.createRecruiter(req);

        return Map.of(
                "message","Recruiter created",
                "id", saved.getId(),
                "email", saved.getEmail(),
                "scope", saved.getScope()
        );
    }
}
