package com.frogman.boot.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserVo {
    private Long id;
    private String userName;
    private String nickName;
    private String phonenumber;
    private String status;
    private String createTime;


}
