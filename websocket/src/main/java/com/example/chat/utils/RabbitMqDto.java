package com.example.chat.utils;

import lombok.Data;

import java.io.Serializable;

@Data
public class RabbitMqDto implements Serializable {
    private String msg;
    private Integer type;
    private String key;
}
