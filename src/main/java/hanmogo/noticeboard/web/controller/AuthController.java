package hanmogo.noticeboard.web.controller;

import hanmogo.noticeboard.service.MemberService.MemberService;
import hanmogo.noticeboard.web.dto.member.MemberLoginRequestDto;
import hanmogo.noticeboard.web.dto.member.MemberResponseDto;
import hanmogo.noticeboard.web.dto.member.MemberSignUpRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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


    // 로그인 API
    @PostMapping("/login")
    public ResponseEntity<MemberResponseDto> login(@Valid @RequestBody MemberLoginRequestDto memberLoginRequestDto) {
        // 1. UsernamePasswordAuthenticationToken 생성 (인증 시도 객체)
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(memberLoginRequestDto.getEmail(), memberLoginRequestDto.getPassword());

        // 2. AuthenticationManagerBuilder를 통해 인증 시도
        //    - loadUserByUsername 메서드가 호출되며 사용자 정보와 비밀번호를 검증
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 SecurityContext에 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 4. 인증된 Authentication 객체로 JWT 토큰 생성
        String jwt = jwtTokenProvider.createToken(authentication);

        // 5. 생성된 JWT 토큰을 응답 본문에 담아 반환
        return ResponseEntity.ok(new MemberResponseDto(jwt));
    }

}
