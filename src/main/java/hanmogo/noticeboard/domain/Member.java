package hanmogo.noticeboard.domain;

import com.fasterxml.jackson.databind.ser.Serializers;
import hanmogo.noticeboard.domain.common.BaseEntity;
import hanmogo.noticeboard.domain.enums.Gender;
import hanmogo.noticeboard.domain.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor

public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 20)
    private String phoneNum;

    @Column(nullable = false, length = 20)
    private String birthdate;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(5)")
    private Gender gender;

    @Column(nullable = false, length = 30, unique = true)
    private String email;

    @Column(nullable = false, length = 30)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(15) DEFAULT 'ACTIVE'")
    private UserStatus status;

    private LocalDate inactiveDate;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Reply> replyList = new ArrayList<>();

    @OneToMany(mappedBy =  "member", cascade = CascadeType.ALL)
    private List<ContentHeart> contentHeartList = new ArrayList<>();


}
