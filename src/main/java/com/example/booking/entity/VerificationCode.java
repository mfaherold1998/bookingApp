package com.example.booking.entity;

import com.example.booking.utils.Enums;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.util.Date;
import java.util.Random;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "VerificationCode")
//@SQLRestriction("deleted = false")
public class VerificationCode {

    @Id
    private String code;

    private Date expirationDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    private String email;
    private Enums.TokenType type;

    public static VerificationCode generateCode(UserEntity userEntity){
        return VerificationCode.builder()
                .email(userEntity.getEmail())
                .expirationDate(new Date(System.currentTimeMillis() + 1800000))
                .code(VerificationCode.randomSixDigitsNumber().toString())
                .user(userEntity)
                .build();
    }

    public static Integer randomSixDigitsNumber(){
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }
}