package hanmogo.noticeboard.repository.PostRepository;

import hanmogo.noticeboard.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 게시글 목록 조회
    // Board와 Member 정보를 함께 조회하여 추가 쿼리 발생을 방지
    @Query(value = "SELECT p FROM Post p JOIN FETCH p.member JOIN FETCH p.board",
            countQuery = "SELECT COUNT(p) FROM Post p")
    Page<Post> findAllWithMemberAndBoard(Pageable pageable);


    @Query("SELECT p FROM Post p " +
            "JOIN FETCH p.member " +
            "LEFT JOIN FETCH p.replyList r " + // 댓글이 없을 수도 있으므로 LEFT JOIN
            "LEFT JOIN FETCH r.member " +      // 댓글 작성자 정보
            "WHERE p.id = :postId")
    Optional<Post> findByIdWithDetails(@Param("postId") Long postId);
}


