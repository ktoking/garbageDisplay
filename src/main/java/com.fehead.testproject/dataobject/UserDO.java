package com.fehead.testproject.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class UserDO {

    private Integer id;

    private String userName;

    private String userPhone;

    private Integer userAge;

    private String userEmail;
}
