package com.xin.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xin
 * @since 2023/1/10 11:03
 */
@Data
public class CourseQuery {

    @ApiModelProperty("课程名称，模糊查询")
    private String title;

    @ApiModelProperty("课程状态")
    private String status;

}
