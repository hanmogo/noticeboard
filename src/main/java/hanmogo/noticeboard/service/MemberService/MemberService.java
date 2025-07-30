package hanmogo.noticeboard.service.MemberService;


import hanmogo.noticeboard.domain.Member;
import hanmogo.noticeboard.domain.enums.UserStatus;
import hanmogo.noticeboard.exception.EntityNotFoundException;
import hanmogo.noticeboard.repository.MemberRepository.MemberRepository;
import hanmogo.noticeboard.web.dto.member.MemberResponseDto;
import hanmogo.noticeboard.web.dto.member.MemberSignUpRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 클래스 전체에 읽기 전용 트랜잭션 기본 적용
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder; // 보안을 위해 비밀번호 암호화


    //회원 가입

    @Transactional
    public MemberResponseDto signUp(MemberSignUpRequestDto requestDto) {
        // 이메일 중복 체크
        if (memberRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        // DTO -> Entity 변환
        Member member = Member.builder()
                .email(requestDto.getEmail())
                .password(encodedPassword)
                .name(requestDto.getName())
                .nickname(requestDto.getNickname())
                .status(UserStatus.ACTIVE) // 활성 상태로 초기화
                .build();

        Member savedMember = memberRepository.save(member);

        // Entity -> DTO 변환 후 반환
        return MemberResponseDto.fromEntity(savedMember);
    }

    //회원 조회
//    public MemberResponseDto getMemberInfo(Long memberId) {
//        Member member = memberRepository.findById(memberId)
//                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다. ID: " + memberId));
//
//        return MemberResponseDto.fromEntity(member);
//    }
}