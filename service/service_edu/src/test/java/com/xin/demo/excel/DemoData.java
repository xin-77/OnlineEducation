package com.xin.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author xin
 * @since 2023/1/5 11:42
 */
@Data
public class DemoData {
    //����excel��ͷ����
    @ExcelProperty(value = "ѧ�����",index = 0)
    private Integer sno;
    @ExcelProperty(value = "ѧ������",index = 1)
    private String sname;
}

