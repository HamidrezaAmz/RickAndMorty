package ir.vasl.navtest.view.feature2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import ir.vasl.navigationcomponentimpl.R;

public class FragmentF2Fr1 extends Fragment {

    public static FragmentF2Fr1 newInstance() {

        Bundle args = new Bundle();

        FragmentF2Fr1 fragment = new FragmentF2Fr1();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_f2_fr1, container, false);
    }
}