package com.xin.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xin
 * @since 2023/1/5 11:43
 */
public class TestEasyExcel {
    public static void main(String[] args) {
//        // ʵ��excelд�Ĳ���
//        // 1 ����д���ļ��е�ַ��excel�ļ�����
//        String filename = "D:\\write.xlsx";
//
//        // 2 ����easyexcel����ķ���ʵ��д����
//        EasyExcel.write(filename, DemoData.class).sheet("ѧ���б�").doWrite(getData());


        //ʵ��excel������
        String filename = "D:\\write.xlsx";
        EasyExcel.read(filename,DemoData.class,new ExcelListener()).sheet().doRead();

    }

    // ������������list����
    private static List<DemoData> getData() {
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("lucy"+i);
            list.add(data);
        }
        return list;
    }
}
