package hanmogo.noticeboard.repository.MemberRepository;

import hanmogo.noticeboard.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {

    //이메일로 회원 조회
    Optional<Member> findByEmail(String email);

    //닉네임으로 회원 조회
    Optional<Member> findByNickname(String nickname);

    //해당 이메일이 이미 존재하는지 확인
    boolean existsByEmail(String email);

    //해당 닉네임이 존재하는지확인
    boolean existsByNickname(String nickname);


    String email(String email);
}
