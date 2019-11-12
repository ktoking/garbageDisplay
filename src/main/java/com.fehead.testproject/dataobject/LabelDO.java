package com.fehead.testproject.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("images")
public class LabelDO {

    private Integer id;

    private String englishName;

    private String name;

    private String classes;

    private Integer label;

}
