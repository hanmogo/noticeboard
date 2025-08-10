package hanmogo.noticeboard.service.PostService;

import hanmogo.noticeboard.domain.Member;
import hanmogo.noticeboard.domain.Post;
import hanmogo.noticeboard.repository.MemberRepository.MemberRepository;
import hanmogo.noticeboard.repository.PostRepository.PostRepository;
import hanmogo.noticeboard.web.dto.post.PostCreateRequestDto;
import hanmogo.noticeboard.web.dto.post.PostEditRequestDto;
import hanmogo.noticeboard.web.dto.post.PostResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.method.annotation.HandlerMethodValidationException;


import java.util.List;



@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    @Autowired
    private PostRepository postRepository;
    private MemberRepository memberRepository;

    //모든 게시글 정보를 가져옴
    public List<Post> listAllPosts(){
        return postRepository.findAll();
    }

    //게시글 목록 페이징 조회
    public Page<PostResponseDto> getPostList(Pageable pageable) {
        Page<Post> postPage = postRepository.findAllWithMemberAndBoard(pageable);

        return postPage.map(post -> PostResponseDto.fromEntity(post));
    }

    //새로운 게시글 생성
    @Transactional
    public PostResponseDto createPost(PostCreateRequestDto requestDto, String email){

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다. " +
                        "Email: " + email));

        // 2. DTO -> Entity 변환
        Post post = Post.builder()
                .title(requestDto.getTitle())
                .body(requestDto.getBody())
                .member(member)
                .build();

        Post savedPost = postRepository.save(post);
        return PostResponseDto.fromEntity(savedPost);
    }

    //하나의 게시글 정보를 가져옴
    public PostResponseDto getPost(Long postId) {

        Post post = postRepository.findByIdWithDetails(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다. "));

        //조회수 증가 로직 추가해야함

        return PostResponseDto.fromEntity(post);
    }

    //게시글 정보를 수정
    @Transactional
    public PostResponseDto updatePost(Long postId, PostEditRequestDto requestDto,
                                      String email){

        //게시글과 사용자 엔티티 조회
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다. ID: " + postId));
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다. Email: " + email));

        return PostResponseDto.fromEntity(post);
    }

    //게시글 정보를 삭제
    @Transactional
    public void deletePost(Long postId, String email) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다. ID: " + postId));
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다. Email: " + email));

        // 삭제 권한 확인
        if (!post.getMember().getId().equals(member.getId())) {
            throw new AccessDeniedException("게시글을 삭제할 권한이 없습니다.");
        }

        postRepository.delete(post);
    }


}
