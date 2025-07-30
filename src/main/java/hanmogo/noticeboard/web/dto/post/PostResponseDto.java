package hanmogo.noticeboard.web.dto.post;

import hanmogo.noticeboard.domain.Post;
import hanmogo.noticeboard.web.dto.reply.ReplyResponseDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor

public class PostResponseDto {

    private long id;
    private String title;
    private String body;
    private String writer;
    private long replyCnt;
    private long viewCnt;
    private long heartCnt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ReplyResponseDto> replies;
    private List<String> imageUrls;

    //dto로 변환
    public static PostResponseDto fromEntity(Post post){
        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .body(post.getBody())
                .writer(post.getMember().getNickname()) // member 엔티티에서 nickname만 추출
                .viewCnt(post.getViewCnt())
                .heartCnt(post.getHeartCnt())
                .replyCnt(post.getReplyCnt())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .replies(post.getReplyList().stream() // 댓글 목록도 재귀적으로 DTO로 변환
                        .map(ReplyResponseDto::fromEntity)
                        .collect(Collectors.toList()))
                .build();
    }
}
