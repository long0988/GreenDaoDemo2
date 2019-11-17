package com.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TabFragment extends Fragment {
    public static String TAGFragment="TAGFragment";
    public static TabFragment newInstance(String tag){
        TabFragment tabFragment = new TabFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TAGFragment,tag);
        tabFragment.setArguments(bundle);
        return  tabFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        Bundle arguments = getArguments();
        String string = arguments.getString(TAGFragment);
        textView.setText(string);
        return textView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("eeeeeeeeeeeee","onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("eeeeeeeeeeeee","onCreate");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("eeeeeeeeeeeee","onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("eeeeeeeeeeeee","onDestroy");
    }
}
