package com.example.pensionatdb.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;


import java.sql.Date;

@Data
public class BokningDTO {
    private long id;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date start;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date end;
    private int nätter;
    private String namn;
    private long kundId;
    private long rumId;
    private boolean avbokad;
}