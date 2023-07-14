package com.dans.multipro.technicaltest.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uid;

    @Column(nullable = false, unique = true)
    @Size(max = 32)
    private String username;

    @Column(nullable = false, unique = true)
    private String password;

    public User() {
    }

    private User(Builder builder) {
        uid = builder.uid;
        username = builder.username;
        password = builder.password;
    }

    public static Builder builder() {
        return new Builder();
    }

    public UUID getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(uid, user.uid) && Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, username, password);
    }


    public static final class Builder {
        private UUID uid;
        private @NotBlank(message = "username cannot be blank") @NonNull @Size(max = 32) String username;
        private @NotBlank(message = "password cannot be blank") @NonNull String password;

        private Builder() {
        }

        public Builder uid(UUID val) {
            uid = val;
            return this;
        }

        public Builder username(@NotBlank(message = "username cannot be blank") @NonNull @Size(max = 32) String val) {
            username = val;
            return this;
        }

        public Builder password(@NotBlank(message = "password cannot be blank") @NonNull String val) {
            password = val;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
