package com.tmaxfintech.fintecharenabe.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class Article extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // mysql은 auto로 하면 안됨
    private Long id; // id는 setter를 걸지 않는다. jpa 가 자동으로 해주는 것이므로.

    @Setter @Column(nullable = false) private String title; // 제목
    @Setter @Column(nullable = false, length = 10000) private String content; // 본문

    @Setter private String hashtag; // 해시태그, @Transient 같은게 있지 않다면 기본적으로 @Column은 있다고 본다

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL) @ToString.Exclude // 운영에서 문제 발생 할 수 있음. 댓글을 백업시켜야할 수 있다, 순환참조 막음
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    protected Article() {} // 외부에서 사용할 일 없음. 하이버네이트는 기본 생성자가 필요

    private Article(String title, String content, String hashtag) {  // private으로 막고 팩토리에서 접근
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Article of(String title, String content, String hashtag) { // factory
        return new Article(title, content, hashtag);
    }

    // @EqualsAndHashCode를 안쓰는 이유는 논리적으로 id가 같으면 같은것. 애노테이션을 사용한다면 모든 필드에 대해 판단하므로 불필요
    // 필요한 이유는 예로 리스트에 넣는다고 가정하면, 정렬이나 비교같은걸 해야하므로 equals와 hashcode가 필요하다
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        return id != null && id.equals(article.id); // 영속화 고려
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
