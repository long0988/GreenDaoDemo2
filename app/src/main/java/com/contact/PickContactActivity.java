package com.contact;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.BaseActivity.BaseActivity;
import com.adapter.ContactAdapter2;
import com.entity.UserEntity;
import com.github.promeg.pinyinhelper.Pinyin;
import com.greendaodemo2.R;
import com.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.indexablerv.IndexableAdapter;
import me.yokeyword.indexablerv.IndexableHeaderAdapter;
import me.yokeyword.indexablerv.IndexableLayout;
import me.yokeyword.indexablerv.SimpleFooterAdapter;
import me.yokeyword.indexablerv.SimpleHeaderAdapter;


/**
 * Created by qlshi on 2018/8/13.
 */

public class PickContactActivity extends BaseActivity {
    private ContactAdapter2 mAdapter;
    private IndexableLayout indexableLayout;
    private MenuHeaderAdapter mMenuHeaderAdapter;
    private BannerHeaderAdapter mBannerHeaderAdapter;
    private int mRequestCode=111;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_pick_contact2);
        //getSupportActionBar().setTitle("联系人");
        initView();
        initAdapter();
    }

    private void initView() {
        indexableLayout = (IndexableLayout) findViewById(R.id.indexableLayout);
        indexableLayout.setLayoutManager(new LinearLayoutManager(this));
//        indexableLayout.setLayoutManager(new GridLayoutManager(this, 3));
    }

    private void initAdapter() {
        mAdapter = new ContactAdapter2(this);
        indexableLayout.setAdapter(mAdapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},mRequestCode);
        }else {
            mAdapter.setDatas(initDatas());
        }

        indexableLayout.setOverlayStyle_Center();
//        indexableLayout.setOverlayStyle_MaterialDesign(Color.RED);
        // 全字母排序。  排序规则设置为：每个字母都会进行比较排序；速度较慢
        indexableLayout.setCompareMode(IndexableLayout.MODE_ALL_LETTERS);

        // set Listener
        mAdapter.setOnItemContentClickListener(new IndexableAdapter.OnItemContentClickListener<UserEntity>() {
            @Override
            public void onItemClick(View v, int originalPosition, int currentPosition, UserEntity entity) {
                if (originalPosition >= 0) {
                    ToastUtil.showShort(PickContactActivity.this, "选中:" + entity.getNick() + "  当前位置:" + currentPosition + "  原始所在数组位置:" + originalPosition);
                } else {
                    ToastUtil.showShort(PickContactActivity.this, "选中Header/Footer:" + entity.getNick() + "  当前位置:" + currentPosition);
                }
            }
        });
        mAdapter.setOnItemTitleClickListener(new IndexableAdapter.OnItemTitleClickListener() {
            @Override
            public void onItemClick(View v, int currentPosition, String indexTitle) {
                ToastUtil.showShort(PickContactActivity.this, "选中:" + indexTitle + "  当前位置:" + currentPosition);
            }
        });
        // 添加我关心的人
        indexableLayout.addHeaderAdapter(new SimpleHeaderAdapter<>(mAdapter, "☆", "我关心的", initFavDatas()));
        // 构造函数里3个参数,分别对应 (IndexBar的字母索引, IndexTitle, 数据源), 不想显示哪个就传null, 数据源传null时,代表add一个普通的View
        mMenuHeaderAdapter = new MenuHeaderAdapter("↑", null, initMenuDatas());
        // 添加菜单
        indexableLayout.addHeaderAdapter(mMenuHeaderAdapter);
        mMenuHeaderAdapter.setOnItemHeaderClickListener(new IndexableHeaderAdapter.OnItemHeaderClickListener<MenuEntity>() {
            @Override
            public void onItemClick(View v, int currentPosition, MenuEntity entity) {
                ToastUtil.showShort(PickContactActivity.this, entity.getMenuTitle());
            }
        });
        // 这里BannerView只有一个Item, 添加一个长度为1的任意List作为第三个参数
        List<String> bannerList = new ArrayList<>();
        bannerList.add("");
//        mBannerHeaderAdapter = new BannerHeaderAdapter("↑", null, bannerList,null);
        mBannerHeaderAdapter = new BannerHeaderAdapter("↑", null, bannerList);
        // 添加 Banner
        indexableLayout.addHeaderAdapter(mBannerHeaderAdapter);

        // FooterView
        indexableLayout.addFooterAdapter(new SimpleFooterAdapter<>(mAdapter, "尾", "我是FooterView", initFavDatas()));
    }

    /**
     * 自定义的MenuHeader
     */
    class MenuHeaderAdapter extends IndexableHeaderAdapter<MenuEntity> {
        private static final int TYPE = 1;

        public MenuHeaderAdapter(String index, String indexTitle, List<MenuEntity> datas) {
            super(index, indexTitle, datas);
        }

        @Override
        public int getItemViewType() {
            return TYPE;
        }

        @Override
        public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
            return new VH(LayoutInflater.from(PickContactActivity.this).inflate(R.layout.header_contact_menu, parent, false));
        }

        @Override
        public void onBindContentViewHolder(RecyclerView.ViewHolder holder, MenuEntity entity) {
            VH vh = (VH) holder;
            vh.tv.setText(entity.getMenuTitle());
            vh.img.setImageResource(entity.getMenuIconRes());
        }

        private class VH extends RecyclerView.ViewHolder {
            private TextView tv;
            private ImageView img;

            public VH(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.tv_title);
                img = (ImageView) itemView.findViewById(R.id.img);
            }
        }
    }

    private List<UserEntity> initDatas() {
        List<UserEntity> list = new ArrayList<>();
        // 初始化数据
//        List<String> contactStrings = Arrays.asList(getResources().getStringArray(R.array.contact_array));
//        List<String> mobileStrings = Arrays.asList(getResources().getStringArray(R.array.mobile_array));
//        for (int i = 0; i < contactStrings.size(); i++) {
//            UserEntity contactEntity = new UserEntity(contactStrings.get(i), mobileStrings.get(i));
//            list.add(contactEntity);
//        }
        // 插叙
        String queryTye[] = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, "sort_key", "phonebook_label",
                ContactsContract.CommonDataKinds.Phone.PHOTO_ID};
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, queryTye, null, null,
                "sort_key COLLATE LOCALIZED ASC");
        //        Cursor cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, queryTye, null, null, null);
        String phoneNumber;
        String name;
        String letter;
        while (cursor.moveToNext()) {
            phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            int SORT_KEY_INDEX = cursor.getColumnIndex("sort_key");
            int PHONEBOOK_LABEL = cursor.getColumnIndex("phonebook_label");
            String namePinyin = Pinyin.toPinyin(name, "").toUpperCase();
            Log.e("eeeee", "phone-" + phoneNumber + "-" + SORT_KEY_INDEX + "-" + PHONEBOOK_LABEL);
//            letter = namePinyin.substring(0, 1);
//            if (!letter.matches("[A-Z]")) {
//                letter = "#";
//            }
            UserEntity contactEntity = new UserEntity(name,phoneNumber);
            list.add(contactEntity);
        }
        cursor.close();
        return list;
    }

    private List<UserEntity> initFavDatas() {
        List<UserEntity> list = new ArrayList<>();
        list.add(new UserEntity("张三", "10000"));
        list.add(new UserEntity("李四", "10001"));
        return list;
    }
    private List<MenuEntity> initMenuDatas() {
        List<MenuEntity> list = new ArrayList<>();
        list.add(new MenuEntity("新的朋友", R.mipmap.icon_1));
        list.add(new MenuEntity("群聊", R.mipmap.icon_2));
        list.add(new MenuEntity("标签", R.mipmap.icon_3));
        list.add(new MenuEntity("公众号", R.mipmap.icon_4));
        return list;
    }
    /**
     * 自定义的Banner Header
     */
    class BannerHeaderAdapter extends IndexableHeaderAdapter {
        private static final int TYPE = 2;

        public BannerHeaderAdapter(String index, String indexTitle, List datas) {
            super(index, indexTitle, datas);
        }

        @Override
        public int getItemViewType() {
            return TYPE;
        }

        @Override
        public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
            View view = LayoutInflater.from(PickContactActivity.this).inflate(R.layout.header_contact_banner, parent, false);
            VH holder = new VH(view);
            holder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showShort(PickContactActivity.this, "---点击了Banner---");
                }
            });
            return holder;
        }

        @Override
        public void onBindContentViewHolder(RecyclerView.ViewHolder holder, Object entity) {
            // 数据源为null时, 该方法不用实现
        }

        private class VH extends RecyclerView.ViewHolder {
            ImageView img;

            public VH(View itemView) {
                super(itemView);
                img = (ImageView) itemView.findViewById(R.id.img);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(mRequestCode==requestCode){
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                mAdapter.setDatas(initDatas());
            }
        }
    }
}
