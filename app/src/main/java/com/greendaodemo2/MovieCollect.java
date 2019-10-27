package com.greendaodemo2;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

/**
 * Created by qlshi on 2018/6/28.
 */
/*
@Entity
用来声明类实体，表示它将映射为数据表
@Entity（）括号内可加入更详细的设置，如：
nameInDb =“TABLE_NAME” ——> 声明该表的表名，默认取类名
createInDb = true ——> 是否创建表，默认为true
generateConstructors = true ——> 是否生成含所有参数的构造函数，默认为true
generateGettersSetters = true ——> 是否生成getter/setter，默认为true

@Id
用来声明某变量为表的主键，类型使用Long
@Id（）括号可加入autoincrement = true表明自增长

@Unique
用来声明某变量的值需为唯一值

@NotNull
用来声明某变量的值不能为null

@Property
@Property(nameInDb = “URL”) 用来声明某变量在表中的实际字段名为URL

@Transient
用来声明某变量不被映射到数据表中

@ToOne、@ToMany
用来声明”对一”和“对多”关系，下面举例说明：
* */
@Entity
public class MovieCollect {
    @Id
    private Long id;
    private String movieImage;
    private String title;
    private int year;
    private Date date;
    private String desc;
    private  String movieName;
    private int sex;
    private String CC;
    private String AA;
    @Generated(hash = 1803549711)
    public MovieCollect(Long id, String movieImage, String title, int year,
            Date date, String desc, String movieName, int sex, String CC,
            String AA) {
        this.id = id;
        this.movieImage = movieImage;
        this.title = title;
        this.year = year;
        this.date = date;
        this.desc = desc;
        this.movieName = movieName;
        this.sex = sex;
        this.CC = CC;
        this.AA = AA;
    }
    @Generated(hash = 432838481)
    public MovieCollect() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMovieImage() {
        return this.movieImage;
    }
    public void setMovieImage(String movieImage) {
        this.movieImage = movieImage;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getYear() {
        return this.year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public Date getDate() {
        return this.date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getDesc() {
        return this.desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getMovieName() {
        return this.movieName;
    }
    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
    public int getSex() {
        return this.sex;
    }
    public void setSex(int sex) {
        this.sex = sex;
    }
    public String getCC() {
        return this.CC;
    }
    public void setCC(String CC) {
        this.CC = CC;
    }
    public String getAA() {
        return this.AA;
    }
    public void setAA(String AA) {
        this.AA = AA;
    }

}
