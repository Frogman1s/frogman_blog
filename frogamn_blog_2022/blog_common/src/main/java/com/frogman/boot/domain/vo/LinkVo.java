package com.frogman.boot.domain.vo;

import lombok.Data;

@Data
public class LinkVo {

    private Long id;

    private String name;

    private String logo;

    private String description;

    private String address;

    private String status;
}