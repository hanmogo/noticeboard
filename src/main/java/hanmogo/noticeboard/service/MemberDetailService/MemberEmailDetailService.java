package hanmogo.noticeboard.service.MemberDetailService;

import hanmogo.noticeboard.domain.Member;
import hanmogo.noticeboard.repository.MemberRepository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.Collections;


 //Spring Security에서 회원 정보를 불러오는 클래스
 //UserDetailsService 인터페이스를 구현하여, DB에서 이메일로 회원을 조회하고 UserDetails 객체로 변환

@Service
@RequiredArgsConstructor // final 필드에 대한 생성자 자동 생성
public class MemberEmailDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // memberRepository를 사용하여 DB에서 회원 정보를 조회


        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Member not found with email: " + email));

        // 조회된 회원 정보를 UserDetails 객체로 변환하여 반환
        //    - Spring Security의 User 클래스는 UserDetails 인터페이스의 구현체
        return org.springframework.security.core.userdetails.User.builder()
                .username(member.getEmail()) // UserDetails의 username 필드에 로그인 식별자인 이메일을 설정
                .password(member.getPassword())
                // .roles() 또는 .authorities()를 사용해 회원에게 권한을 부여
                // MEMBER 역할을 부여
                .roles("MEMBER")
                .build();
    }
}