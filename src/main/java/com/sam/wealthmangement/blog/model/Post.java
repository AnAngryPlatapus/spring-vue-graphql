package com.sam.wealthmangement.blog.model;

import java.text.Normalizer;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(uniqueConstraints=  @UniqueConstraint(columnNames={"slug"}))
public class Post {

    @Id
    @Column
    @GeneratedValue
    private Long postId;
    @Column
    private String title;
    @Column
    private String content;
    @Column
    private String slug;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "blog_user_id", nullable = false)
    private BlogUser blogUser;
    @OneToMany(mappedBy = "post")
//    @JoinColumn(name = "comment_id", nullable = false)
    private List<Comment> comments;

    public Post(String theTitle, String theContent, BlogUser theBlogUser) {
        title = theTitle;
        content = theContent;
        blogUser = theBlogUser;
        slug = toSlug(theTitle);
    }

    private static final Pattern NON_LATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
    private static final Pattern EDGES_AND_HASHES = Pattern.compile("(^-|-$)");

    public static String toSlug(String input) {
        String noWhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(noWhitespace, Normalizer.Form.NFD);
        String slug = NON_LATIN.matcher(normalized).replaceAll("");
        slug = EDGES_AND_HASHES.matcher(slug).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }
}
