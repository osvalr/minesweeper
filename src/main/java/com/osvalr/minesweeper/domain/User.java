package com.osvalr.minesweeper.domain;

import javax.annotation.Nonnull;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import java.util.Set;

import static java.util.Objects.requireNonNull;

@Entity
public class User extends BaseEntity {
    private String user;
    private String password;
    private String activeToken;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Game> games;


    public User() {
    }

    public User(@Nonnull String user, @Nonnull String password) {
        this.user = requireNonNull(user);
        this.password = requireNonNull(password);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getActiveToken() {
        return activeToken;
    }

    public void setActiveToken(String activeToken) {
        this.activeToken = activeToken;
    }

    public Set<Game> getGames() {
        return games;
    }
}
