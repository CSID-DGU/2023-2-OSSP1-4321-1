package com.akobot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardVO {
    private long school_key;
    private String field;
    private Long bno;
    private String question;
    private String answer;
    private String email;
    private String name;
    //private String phone;
    private ZonedDateTime askDate;
    private ZonedDateTime answerDate;
    private int isAnswered;
}
