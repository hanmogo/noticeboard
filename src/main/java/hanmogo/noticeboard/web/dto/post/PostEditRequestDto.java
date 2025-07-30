package hanmogo.noticeboard.web.dto.post;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostEditRequestDto {

    @NotBlank(message = "게시글 제목을 입력하세요.")
    private String title;

    @NotBlank(message = "게시글 내용을 입력하세요.")
    private String body;
}
