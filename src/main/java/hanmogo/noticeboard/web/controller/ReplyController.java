package hanmogo.noticeboard.web.controller;

import hanmogo.noticeboard.service.ReplyService.ReplyService;
import hanmogo.noticeboard.web.dto.reply.ReplyCreateRequestDto;
import hanmogo.noticeboard.web.dto.reply.ReplyEditRequestDto;
import hanmogo.noticeboard.web.dto.reply.ReplyResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    //댓글 생성 API
    @PostMapping("/posts/{postId}/replies")
    public ResponseEntity<ReplyResponseDto> createReply(
            @PathVariable Long postId,
            @Valid @RequestBody ReplyCreateRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails) {

        String email = userDetails.getUsername();
        ReplyResponseDto responseDto = replyService.createReply(postId, requestDto, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    //댓글 수정 API
    @PatchMapping("/replies/{replyId}")
    public ResponseEntity<ReplyResponseDto> updateReply(
            @PathVariable Long replyId,
            @Valid @RequestBody ReplyEditRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails) {

        String email = userDetails.getUsername();
        ReplyResponseDto updatedReply = replyService.updateReply(replyId, requestDto, email);
        return ResponseEntity.ok(updatedReply);
    }

    //댓글 삭제 API
    @DeleteMapping("/replies/{replyId}")
    public ResponseEntity<Void> deleteReply(
            @PathVariable Long replyId,
            @AuthenticationPrincipal UserDetails userDetails) {

        String email = userDetails.getUsername();
        replyService.deleteReply(replyId, email);
        // 성공적으로 삭제되었음을 204 No Content 상태 코드로 응답
        return ResponseEntity.noContent().build();
    }
}