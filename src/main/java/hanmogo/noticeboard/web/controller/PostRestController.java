package hanmogo.noticeboard.web.controller;


import hanmogo.noticeboard.service.PostService.PostService;
import hanmogo.noticeboard.web.dto.post.PostCreateRequestDto;
import hanmogo.noticeboard.web.dto.post.PostEditRequestDto;
import hanmogo.noticeboard.web.dto.post.PostResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostRestController {


    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(
            @Valid @RequestBody PostCreateRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails) { // 인증된 사용자 정보 받기

        // userDetails.getUsername()을 통해 현재 로그인된 사용자의 이메일(또는 ID)을 가져옴
        String email = userDetails.getUsername();
        PostResponseDto responseDto = postService.createPost(requestDto, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto); // 객체를 응답 본문에 답아 클라이언트에게 전달
    }

    // 게시글 하나 조회 API

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long postId) {
        PostResponseDto responseDto = postService.getPost(postId);
        return ResponseEntity.ok(responseDto);
    }

    // 게시글 목록 페이징 조회 API

    @GetMapping
    public ResponseEntity<Page<PostResponseDto>> getPostList(
            @PageableDefault(size = 10, sort = "createdAt,desc") Pageable pageable) {
        Page<PostResponseDto> postList = postService.getPostList(pageable);
        return ResponseEntity.ok(postList);
    }

    // 게시글 수정 API

    @PatchMapping("/{postId}")
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable Long postId,
            @Valid @RequestBody PostEditRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails) {

        String email = userDetails.getUsername();
        PostResponseDto updatedPost = postService.updatePost(postId, requestDto, email);
        return ResponseEntity.ok(updatedPost);
    }

    // 게시글 삭제 API

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetails userDetails) {

        String email = userDetails.getUsername();
        postService.deletePost(postId, email);
        // 성공적으로 삭제되었음을 204 No Content 상태 코드로 응답
        return ResponseEntity.noContent().build();
    }
}
