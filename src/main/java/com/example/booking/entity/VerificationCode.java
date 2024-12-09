package com.example.booking.entity;

import com.example.booking.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.util.Date;
import java.util.Random;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "Verification Code")
@SQLRestriction("deleted = false")
public class VerificationCode extends BaseEntity {

    @Id
    private String code;

    private Date expirationDate;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String email;

    //private Enums.TokenType type;

    public static VerificationCode generateCode(User user){

        VerificationCode vcode = new VerificationCode();
        vcode.setEmail(user.getEmail());
        vcode.setExpirationDate(new Date(System.currentTimeMillis() + 1800000));
        vcode.setCode(VerificationCode.randomSixDigitsNumber().toString());
        vcode.setUser(user);

        return vcode;
    }

    public static Integer randomSixDigitsNumber(){
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }
}
