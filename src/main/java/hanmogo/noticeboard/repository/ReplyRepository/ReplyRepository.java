package hanmogo.noticeboard.repository.ReplyRepository;

import hanmogo.noticeboard.domain.Post;
import hanmogo.noticeboard.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {


    //특정 게시글에 달린 모든 댓글 조회
    List<Reply> findByPost(Post post);
}
