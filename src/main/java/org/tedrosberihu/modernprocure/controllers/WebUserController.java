package org.tedrosberihu.modernprocure.controllers;

import org.tedrosberihu.modernprocure.models.WebUserModel;
import org.tedrosberihu.modernprocure.security.AuthUserInfo;
import org.tedrosberihu.modernprocure.services.WebUserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class WebUserController {
    @NonNull
    private final WebUserService webUserService;

    @GetMapping("/list")
    public ResponseEntity<Page<WebUserModel>> get(@AuthenticationPrincipal AuthUserInfo user, Pageable pageable) {
        return ResponseEntity.ok(webUserService.getAllUsers(user, true, pageable));
    }

    @PostMapping("/user/auth/signup")
    public ResponseEntity<WebUserModel> signup(@RequestBody WebUserModel webUserModel) {
        return ResponseEntity.ok(webUserService.signup(webUserModel));
    }

    @GetMapping("/user/currentUser")
    public ResponseEntity<WebUserModel> getUser(@AuthenticationPrincipal AuthUserInfo userInfo) {
        return ResponseEntity.ok(webUserService.getUserDetails(userInfo));
    }

    @PutMapping("/user/currentUser")
    public ResponseEntity<WebUserModel> updateCurrentUser(@AuthenticationPrincipal AuthUserInfo userInfo, @RequestBody WebUserModel webUserModel) {
        return ResponseEntity.ok(webUserService.getUpdateDetails(userInfo, webUserModel));
    }
}
