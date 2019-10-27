package com.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.BaseActivity.BaseActivity;
import com.adapter.ContactAdapter;
import com.comparator.MyComparator;
import com.entity.Contact;
import com.github.promeg.pinyinhelper.Pinyin;
import com.github.promeg.pinyinhelper.PinyinMapDict;
import com.github.promeg.tinypinyin.lexicons.android.cncity.CnCityDict;
import com.greendaodemo2.R;
import com.widget.CustomEditText;
import com.widget.SideLetterBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.bCallBack;

/**
 * Created by qlshi on 2018/7/17.
 */

public class ContactActivity extends BaseActivity implements SideLetterBar.OnLettersChangeListener {
    private TextView mTv;
    private SideLetterBar mSider;
    private ListView mListView;
    private ArrayList<Contact> list;
    private ContactAdapter mContactAdapter;
    private SearchView mSearchView;
    private int mRequestCode=111;
    private CustomEditText mCustomEditText;
    private scut.carson_ho.searchview.SearchView searchView2;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_concat);
        mListView = (ListView) findViewById(R.id.listview);
        mTv = ((TextView) findViewById(R.id.tv));
        mSider = ((SideLetterBar) findViewById(R.id.siderLetter));
        mSearchView = (SearchView) findViewById(R.id.search);
        mCustomEditText=(CustomEditText)findViewById(R.id.edit_text);
        // 3. 绑定组件
        searchView2 = (scut.carson_ho.searchview.SearchView) findViewById(R.id.search_view);
        mSider.setTextView(mTv);
//        mSider.setPaintChooseColor(getString(R.color.colorAccent));
//        mSider.setPaintColor("#aaafff");
//        mSider.setTextSize(30);
        listener();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},mRequestCode);
        }else {
            getNumber();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(mRequestCode==requestCode){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getNumber();
            }
        }
    }

    private void listener() {
        // 4. 设置点击键盘上的搜索按键后的操作（通过回调接口）
        // 参数 = 搜索框输入的内容
        searchView2.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {
                System.out.println("我收到了" + string);
            }
        });

        // 5. 设置点击返回按键后的操作（通过回调接口）
        searchView2.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
                finish();
            }
        });
        mSider.setOnLettersChangeListener(this);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //当滑动列表的时候，更新右侧字母列表的选中状态
                if (list!=null&&list.size() > 0) {
                    mSider.setTouchIndex(list.get(firstVisibleItem).getHeaderLetter());
                }
            }
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    ArrayList<Contact> search = search(newText);
                    if (search.size() > 0) {
                        mContactAdapter.updateListView(search);
                    }
                } else {
                    mContactAdapter.updateListView(list);
                }
                return false;
            }
        });
        mCustomEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString())) {
                    ArrayList<Contact> search = search(s.toString());
                    if (search.size() > 0) {
                        mContactAdapter.updateListView(search);
                    }
                } else {
                    mContactAdapter.updateListView(list);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private ArrayList<Contact> search(String str) {
        ArrayList<Contact> contactList = new ArrayList<>();
        if (str.matches("^([0-9]|[/+]).*")) {//匹配号码
            // 匹配以数字或者加号开头的字符串(包括了带空格及-分割的号码)
            String simpleStr = str.replaceAll("\\-|\\s", "");
            for (Contact contact : list) {
                if (contact.getName() != null && contact.getPhoneNumber() != null) {
                    if (contact.getPhoneNumber().contains(simpleStr) || contact.getName().contains(simpleStr)) {
                        if (!contactList.contains(contact)) {
                            contactList.add(contact);
                        }
                    }
                }
            }
        } else {
            for (Contact contact : list) {
                if (contact.getPhoneNumber() != null && contact.getName() != null) {
                    // 姓名全匹配,姓名首字母简拼匹配,姓名全字母匹配
                    if (contact.getName().toLowerCase(Locale.CHINESE).contains(str.toLowerCase(Locale.CHINESE))) {
                        if (!contactList.contains(contact)) {
                            contactList.add(contact);
                        }
                    }
                }
            }
        }
        return contactList;
    }

    /**
     * 初始化联系人列表信息
     */
    public void getNumber() {
        // 添加中文城市词典
        Pinyin.init(Pinyin.newConfig().with(CnCityDict.getInstance(this)));
        // 添加自定义词典
        Pinyin.init(Pinyin.newConfig()
                .with(new PinyinMapDict() {
                    @Override
                    public Map<String, String[]> mapping() {
                        HashMap<String, String[]> map = new HashMap<String, String[]>();
                        map.put("重庆", new String[]{"CHONG", "QING"});
                        map.put("长春", new String[]{"chang", "chun"});
                        return map;
                    }
                }));
        list = new ArrayList<>();
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
            letter = namePinyin.substring(0, 1);
            if (!letter.matches("[A-Z]")) {
                letter = "#";
            }
            Contact contact = new Contact(name, namePinyin, letter, phoneNumber);
            list.add(contact);
        }
//        //对集合排序
        Collections.sort(list, new MyComparator());
        mContactAdapter = new ContactAdapter(list, this);
        mListView.setAdapter(mContactAdapter);
    }

    @Override
    public void LettersChange(String letter) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            String headerLetter = list.get(i).getHeaderLetter();
            //将手指按下的字母与列表中相同字母开头的项找出来
            if (headerLetter.equals(letter)) {
                //将列表选中哪一个
                mListView.setSelection(i);
                //找到开头的一个即可
                return;
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
