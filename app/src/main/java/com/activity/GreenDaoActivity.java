package com.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.BaseActivity.BaseActivity;
import com.daomanager.DaoManager;
import com.greendao.db.MovieCollectDao;
import com.greendaodemo2.MovieCollect;
import com.greendaodemo2.R;

import java.util.Date;
import java.util.List;

/**
 * Created by qlshi on 2018/8/14.
 */

public class GreenDaoActivity extends BaseActivity{
    private TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greendao);
        tv=(TextView)findViewById(R.id.tv_greendao);
        initGreenDao();
    }

    private void initGreenDao() {
        MovieCollectDao mMovieCollectDao = DaoManager.getInstance().getDaoSession().getMovieCollectDao();
        //1.增加
        MovieCollect movieCollect = new MovieCollect();
        movieCollect.setTitle("123312321");
        movieCollect.setYear(2016);
        movieCollect.setDesc("appp");
        movieCollect.setDate(new Date());
        movieCollect.setMovieName("萧十一郎");
        movieCollect.setCC("一点点");
        movieCollect.setSex(11);
        movieCollect.setAA("AA");
        mMovieCollectDao.insert(movieCollect);
        /*
        //插入一组数据
        List<MovieCollect> movieCollect;
        mMovieCollectDao.insertInTx(movieCollect);
        //插入替换
        //插入的数据如果已经存在表中，则替换掉旧数据（根据主键来检测是否已经存在）
        MovieCollect movieCollect;
        mMovieCollectDao.insertOrReplace(movieCollect);//单个数据
        List<MovieCollect> listMovieCollect;
        mMovieCollectDao.insertOrReplace(listMovieCollect);//一组数据
        * */
        //2.删
        //mMovieCollectDao.delete(movieCollect);
        //4、查
        List<MovieCollect> movieCollects = mMovieCollectDao.loadAll();
        StringBuilder stringBuilder = new StringBuilder();
        for (MovieCollect movie : movieCollects) {
            stringBuilder.append(movie.getId() + "--" + movie.getTitle() + "-year-" + movie.getYear()+"--"+movie.getDate()+"///"+movie.getDesc()+"-"+movie.getMovieName()+"-"+movie.getSex()+"-"+movie.getCC()+"-"+movie.getAA());
            Log.e("eeeeee", movie.getId() + "--" + movie.getTitle() + "-year-" + movie.getYear()+"--"+movie.getDate()+"///"+movie.getDesc()+"-"+movie.getMovieName()+"-"+movie.getSex()+"-"+movie.getCC()+"-"+movie.getAA());
        }
        tv.setText(stringBuilder.toString());
        /*//查询电影名为“肖申克的救赎”的电影
MovieCollect movieCollect =
mMovieCollectDao.queryBuilder().where(MovieCollectDao.Properties.Title.eq("肖申克的救赎")).unique();

//查询电影年份为2017的电影
List<MovieCollect> movieCollect =
mMovieCollectDao.queryBuilder().where(MovieCollectDao.Properties.Year.eq(2017)).list();

//模糊查询-查询电影名含有“传奇”的电影
List<MovieCollect> movieCollect = mMovieCollectDao.queryBuilder().where(MovieCollectDao.Properties.Title.like("传奇")).list();

//查询电影名以“我的”开头的电影
List<MovieCollect> movieCollect = mMovieCollectDao.queryBuilder().where(MovieCollectDao.Properties.Title.like("我的%")).list();

//区间查询 大于
//查询电影年份大于2012年的电影
List<MovieCollect> movieCollect = mMovieCollectDao.queryBuilder().where(MovieCollectDao.Properties.Year.gt(2012)).list();

//大于等于
//查询电影年份大于等于2012年的电影
List<MovieCollect> movieCollect = mMovieCollectDao.queryBuilder().where(MovieCollectDao.Properties.Year.ge(2012)).list();

//小于
//查询电影年份小于2012年的电影
List<MovieCollect> movieCollect = mMovieCollectDao.queryBuilder().where(MovieCollectDao.Properties.Year.lt(2012)).list();

//小于等于
//查询电影年份小于等于2012年的电影
List<MovieCollect> movieCollect = mMovieCollectDao.queryBuilder().where(MovieCollectDao.Properties.Year.le(2012)).list();

//介于中间
//查询电影年份在2012-2017之间的电影
List<MovieCollect> movieCollect = mMovieCollectDao.queryBuilder().where(MovieCollectDao.Properties.Year.between(2012,2017)).list();
        * */
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
