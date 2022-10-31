package com.frogman.boot.domain.vo;

import com.frogman.boot.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPutVo {
    private Long id;
    private List<Long> roleIds;
    private String userName;
    private String nickName;
    private String phonenumber;
    private String sex;
    private String password;
    private String email;
    private String status;
    private String createTime;
}
