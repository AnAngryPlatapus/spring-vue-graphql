package com.sam.wealthmangement.blog.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
})
public class BlogUser {

    @Id
    @Column
    @GeneratedValue
    private Long blogUserId;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String email;
    @OneToMany(mappedBy = "blogUser")
    private List<Post> posts;
    @OneToMany(mappedBy = "blogUser")
    private List<Comment> comments;
    @Column
    private Role role;

}
