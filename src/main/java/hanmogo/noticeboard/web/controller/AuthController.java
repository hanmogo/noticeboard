package hanmogo.noticeboard.web.controller;

import hanmogo.noticeboard.service.MemberService.MemberService;
import hanmogo.noticeboard.web.dto.member.MemberLoginRequestDto;
import hanmogo.noticeboard.web.dto.member.MemberResponseDto;
import hanmogo.noticeboard.web.dto.member.MemberSignUpRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;


    //회원가입 api
    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signUp(@Valid @RequestBody MemberSignUpRequestDto requestDto) {
        MemberResponseDto responseDto = memberService.signUp(requestDto);
        // 생성된 리소스를 응답 본문에 담아 201 Created 상태 코드로 응답
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

}
    //로그인 api
//    @PostMapping("/login")
//    public ResponseEntity<MemberLoginRequestDto> login(@Valid @RequestBody MemberLoginRequestDto requestDto) {
//        MemberLoginRequestDto
//    }
//}