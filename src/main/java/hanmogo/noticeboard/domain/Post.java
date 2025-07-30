package hanmogo.noticeboard.domain;


import hanmogo.noticeboard.domain.common.BaseEntity;
import hanmogo.noticeboard.domain.enums.PostStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob //일반적인 데이터보다 큰 데이터를 담는 필드라는 것을 명시
    // text타입으로 매핑
    private String body;

    @Column(nullable = false, length = 20)
    private String writer;

    private Long replyCnt;

    private Long viewCnt;

    private Long heartCnt;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(15) DEFAULT 'ACTIVE'")
    private PostStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostImage> postImageList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Reply> replyList = new ArrayList<>();

    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ContentHeart contentHeart;
}