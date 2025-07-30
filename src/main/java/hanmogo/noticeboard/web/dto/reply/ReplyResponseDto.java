package hanmogo.noticeboard.web.dto.reply;


import hanmogo.noticeboard.domain.Reply;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReplyResponseDto {

    private Long id;
    private String comment;
    private String writer;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long memberId;
    private Long postId;

    // 엔티티를 DTO로 변환하는 정적 팩토리 메서드
    public static ReplyResponseDto fromEntity(Reply reply) {
        return ReplyResponseDto.builder()
                .id(reply.getId())
                .comment(reply.getContent())
                .writer(reply.getMember().getNickname())
                .createdAt(reply.getCreatedAt())
                .updatedAt(reply.getUpdatedAt())
                .memberId(reply.getMember().getId())
                .postId(reply.getPost().getId())
                .build();
    }
}

