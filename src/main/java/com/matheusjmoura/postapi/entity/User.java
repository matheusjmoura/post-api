package com.matheusjmoura.postapi.entity;

import com.matheusjmoura.postapi.enums.UserRole;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document("user")
public class User {

    @Id
    private UUID id;

    private String name;

    @UniqueElements(message = "user.exception.email.exists")
    private String email;

    @UniqueElements(message = "user.exception.username.exists")
    private String username;

    private String password;

    private Boolean active;

    private UserRole role;

    public void activate() {
        if (Boolean.TRUE.equals(active)) {
            return;
        }
        this.active = true;
    }

    public void deactivate() {
        if (Boolean.FALSE.equals(active)) {
            return;
        }
        active = false;
    }

}
