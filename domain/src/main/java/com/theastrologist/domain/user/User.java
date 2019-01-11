package com.theastrologist.domain.user;

import com.theastrologist.domain.individual.Individual;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Users")
@NamedQueries({ @NamedQuery(name = User.QUERY_FIND_BY_USERNAME, query = "SELECT u from User u where u.userName = :userName") })
public class User {
    public static final String QUERY_FIND_BY_USERNAME = "UserEntity.findByUserName";
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(255)")
    private UUID id;

    @Basic
    private String userName;

    private List<Individual> individuals;

    public User() {}

    public User(String userName) {
        this.userName = userName;
    }

    public List<Individual> getIndividuals() {
        return individuals;
    }

    public void setIndividuals(List<Individual> individuals) {
        this.individuals = individuals;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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
