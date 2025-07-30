package hanmogo.noticeboard.service.ReplyService;

import hanmogo.noticeboard.domain.Member;
import hanmogo.noticeboard.domain.Post;
import hanmogo.noticeboard.domain.Reply;
import hanmogo.noticeboard.exception.AccessDeniedException;
import hanmogo.noticeboard.exception.EntityNotFoundException;
import hanmogo.noticeboard.repository.MemberRepository.MemberRepository;
import hanmogo.noticeboard.repository.PostRepository.PostRepository;
import hanmogo.noticeboard.repository.ReplyRepository.ReplyRepository;
import hanmogo.noticeboard.web.dto.reply.ReplyCreateRequestDto;
import hanmogo.noticeboard.web.dto.reply.ReplyEditRequestDto;
import hanmogo.noticeboard.web.dto.reply.ReplyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    //댓글생성
    @Transactional
    public ReplyResponseDto createReply(Long postId, ReplyCreateRequestDto requestDto, String memberEmail) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다. ID: " + postId));
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다. Email: " + memberEmail));

        Reply reply = Reply.builder()
                .content(requestDto.getComment())
                .post(post)
                .member(member)
                .build();

        Reply savedReply = replyRepository.save(reply);
        return ReplyResponseDto.fromEntity(savedReply);
    }

    //댓글 수정
    @Transactional
    public ReplyResponseDto updateReply(Long replyId, ReplyEditRequestDto requestDto, String memberEmail) {

        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다. Email: " + memberEmail));
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다. ID: " + replyId));

        return ReplyResponseDto.fromEntity(reply);
    }

    //댓글 삭제
    @Transactional
    public void deleteReply(Long replyId, String memberEmail) {

        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다. ID:" + replyId));
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다. Email: " + memberEmail));
        if (!reply.getMember().getId().equals(member.getId())) {
            throw new AccessDeniedException("댓글을 삭제할 권한이 없습니다.");
        }

        replyRepository.delete(reply);
    }

}