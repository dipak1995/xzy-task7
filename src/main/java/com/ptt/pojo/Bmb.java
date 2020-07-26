package com.ptt.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bmb implements Serializable {

    private Integer id;
    private String name;
    private String qq;
    private String type;
    private String jointime;
    private String school;
    private String studyid;
    private String dailylink;
    private String hope;
    private Timestamp create_at;
    private Timestamp update_at;

}

