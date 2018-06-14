package com.example.krzysiek.astro;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.krzysiek.astro.databinding.Fragment2LayoutBinding;


public class Fragment2 extends Fragment {


    Fragment2LayoutBinding fragment2LayoutBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        fragment2LayoutBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment2_layout, container, false);

        View view = fragment2LayoutBinding.getRoot();

        fragment2LayoutBinding.setMoon(MainActivity.moon);


        return view;
    }
}
