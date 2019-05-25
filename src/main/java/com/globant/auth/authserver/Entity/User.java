package com.globant.auth.authserver.Entity;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.couchbase.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User {

    @Id
    @NotNull
    private String userName;
    @NotNull
    @Field
    private String password;
    @NotNull
    @Field
    private String email;
    @NotNull
    @Field
    private List<String> roles;
    @NotNull
    @Field
    private String firstName;
    @NotNull
    @Field
    private String lastName;

}
