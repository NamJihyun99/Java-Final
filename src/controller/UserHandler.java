package controller;

import user.domain.User;
import user.dto.LoginRequest;
import user.dto.UserInfoResponse;
import user.dto.PasswordUpdateRequest;
import user.dto.UserUpdateRequest;
import user.service.UserService;
import view.MemberView;

public class UserHandler {

    private final MemberView memberView;
    private final UserService userService;

    public UserHandler(MemberView memberView, UserService userService) {
        this.memberView = memberView;
        this.userService = userService;
    }

    // 4. 회원 정보 조회
    void print(User member) {
        memberView.printUserInfo(UserInfoResponse.of(
                member.getUserId(),
                member.getName(),
                member.getPhone(),
                member.getRole().value())
        );
    }

    // 5. 회원 정보 수정
    void update(User member) {
        int option = memberView.updateInfoMenu();
        // 1. 비밀번호 변경 | 2. 이름 및 전화번호 수정 | 3. 뒤로 가기
        switch (option) {
            case 1 -> updatePassword(member);
            case 2 -> updateUserInfo(member);
        }
    }

    // 5 - (1) 비밀번호 변경
    void updatePassword(User member) {
        try {
            PasswordUpdateRequest passwordUpdateRequest = memberView.getPasswordUpdateRequest();
            passwordUpdateRequest.validate(member);
            userService.updatePassword(member, passwordUpdateRequest.getNewPassword());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    // 5 - (2) 이름 및 전화번호 수정
    void updateUserInfo(User member) {
        UserUpdateRequest updateRequest = memberView.getUpdateRequest();
        userService.updateInfo(member, updateRequest);
    }

    // 7. 회원 탈퇴
    void quit(User member) {
        LoginRequest removeRequest = memberView.getRemoveRequest();
        userService.quit(member, removeRequest);
    }

}
