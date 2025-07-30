package hanmogo.noticeboard.domain;


import hanmogo.noticeboard.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long    id;

    @Column(nullable = false, length = 20)
    private String name;

    @Lob
    private String description;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Post> postList = new ArrayList<>();

}
