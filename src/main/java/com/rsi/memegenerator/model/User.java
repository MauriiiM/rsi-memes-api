package com.rsi.memegenerator.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", updatable = false, nullable = false)
    private Long uuid; //= java.util.UUID.randomUUID();
    private @NonNull
    String email;
    private @NonNull
    String password;

    @Override
    public String toString() {
        return uuid.toString() + ": email=\"" + email + "\"";
    }
}
