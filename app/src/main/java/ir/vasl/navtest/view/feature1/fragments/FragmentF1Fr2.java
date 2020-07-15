package ir.vasl.navtest.view.feature1.fragments;

import android.content.Intent;
import android.os.Bundle;

import butterknife.OnClick;
import ir.vasl.navigationcomponentimpl.R;
import ir.vasl.navigationcomponentimpl.utils.BaseClasses.BaseFragment;
import ir.vasl.navtest.view.feature2.ActivityFeature2;

public class FragmentF1Fr2 extends BaseFragment {

    @OnClick(R.id.button_go_to_feature_2)
    public void OnGoToFeature2Clicked() {
        Intent intent = new Intent(getActivity(), ActivityFeature2.class);
        startActivity(intent);
    }

    public static FragmentF1Fr2 newInstance() {

        Bundle args = new Bundle();

        FragmentF1Fr2 fragment = new FragmentF1Fr2();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_f1_fr2;
    }

}