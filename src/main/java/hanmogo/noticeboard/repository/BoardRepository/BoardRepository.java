package hanmogo.noticeboard.repository.BoardRepository;

import hanmogo.noticeboard.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;



public interface BoardRepository extends JpaRepository<Board, Long> {
    // 필요시 쿼리 메서드 추가
}