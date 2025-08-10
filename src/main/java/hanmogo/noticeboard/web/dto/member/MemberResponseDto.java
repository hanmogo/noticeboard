package hanmogo.noticeboard.web.dto.member;

import hanmogo.noticeboard.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberResponseDto {

    // 회원 정보를 클라이언트에게 응답하기 위한 데이터
    private Long id;
    private String name;
    private String phoneNum;
    private String birthDate;
    private String email;
    private String nickname;
    private String gender;


    // 엔티티를 DTO로 변환
    public static MemberResponseDto fromEntity(Member member) {
        return MemberResponseDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .nickname(member.getNickname())
                .phoneNum(member.getPhoneNum())
                .birthDate(member.getBirthdate())
                .gender(member.getGender() != null ? member.getGender().name() : null)
                .build();
    }
}