package com.example.krzysiek.astro;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.krzysiek.astro.databinding.Fragment1LayoutBinding;

public class Fragment1 extends Fragment {




    private Fragment1LayoutBinding fragment1LayoutBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragment1LayoutBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment1_layout, container, false);

        View view = fragment1LayoutBinding.getRoot();

        fragment1LayoutBinding.setSun(MainActivity.sun);



        return view;
    }
}
