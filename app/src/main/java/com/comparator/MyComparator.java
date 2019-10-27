package com.comparator;

import com.entity.Contact;

import java.util.Comparator;

/**
 * Created by qlshi on 2018/7/18.
 */

public class MyComparator implements Comparator<Contact> {
    @Override
    public int compare(Contact o1, Contact o2) {
        //根据拼音进行排序
        if (o2.getHeaderLetter().equals("#")) {
            return -1;
        } else if (o1.getHeaderLetter().equals("#")) {
            return 1;
        } else {
            return o1.getPinyin().compareTo(o2.getPinyin());
        }
    }
}
