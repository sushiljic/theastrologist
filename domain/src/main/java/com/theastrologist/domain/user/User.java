package com.theastrologist.domain.user;

import javax.persistence.*;

@Entity
@Table(name = "Users")
@NamedQueries({ @NamedQuery(name = User.QUERY_FIND_BY_USERNAME, query = "SELECT u from User u where u.userName = :userName") })
public class User {
    public static final String QUERY_FIND_BY_USERNAME = "UserEntity.finyByUserName";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    private String userName;

    public User(String userName) {
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [id=").append(id);
        sb.append(", userName='").append(userName).append('\'');
        sb.append(']');
        return sb.toString();
    }
}
