package hanmogo.noticeboard.web.dto.reply;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReplyCreateRequestDto {

    @NotBlank(message = "댓글을 입력해주세요.")
    private String comment;
}
