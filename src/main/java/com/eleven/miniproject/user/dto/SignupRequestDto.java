package com.eleven.miniproject.user.dto;

import com.eleven.miniproject.user.entity.ValidationGroups;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequestDto {
    @NotBlank(groups = ValidationGroups.NotBlankGroup.class, message = "아이디를 입력해주세요.")
    @Pattern(groups = ValidationGroups.PatternCheckGroup.class, message = "잘못된 이메일 형식입니다.", regexp = "^[A-Za-z0-9_\\.\\-]+@[A-Za-z0-9\\-]+\\.[A-Za-z0-9\\-]+$")
    private String username;
    @NotBlank(groups = ValidationGroups.NotBlankGroup.class, message = "비밀번호를 입력해주세요.")
    @Pattern(groups = ValidationGroups.PatternCheckGroup.class, message = "잘못된 비밀번호 형식입니다.", regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{8,15}")
    private String password;
}