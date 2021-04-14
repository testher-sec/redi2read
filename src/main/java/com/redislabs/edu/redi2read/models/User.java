package com.redislabs.edu.redi2read.models;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.annotation.Transient;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Data
@RedisHash
@JsonIgnoreProperties(value = { "password", "passwordConfirm" }, allowSetters = true)
public class User {

    @Id
    @ToString.Include
    private String id;

    @NotNull
    @Size(min = 2, max = 48)
    @ToString.Include
    private String name;

    @NotNull
    @Email
    @EqualsAndHashCode.Include
    @ToString.Include
    @Indexed
    private String email;

    @NotNull
    private String password;

    @Transient
    private String passwordConfirm;

    /**
     * We will add the @JsonIdentityReference with the alwaysAsId parameter set to true,
     * which, given the proper meta-information in the target classes (Book and Role),
     * will make Jackson serialize collections of these objects as IDs.
     */
    @Reference
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Role> roles = new HashSet<Role>();
    public void addRole(Role role) {
        roles.add(role);
    }

    @Reference
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Book> books = new HashSet<Book>();
    public void addBook(Book book) {
        books.add(book);
    }
}
