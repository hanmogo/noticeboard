package hanmogo.noticeboard.web.dto.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class MemberLoginRequestDto {

    //로그인을 위해 클라이언트가 서버로 보내는 데이터

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;


    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}