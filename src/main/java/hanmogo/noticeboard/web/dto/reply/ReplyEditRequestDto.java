package hanmogo.noticeboard.web.dto.reply;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Getter
@NoArgsConstructor
@AllArgsConstructor

public class ReplyEditRequestDto {

    @NotBlank(message = "댓글을 입력해주세요.")
    private String comment;
}

