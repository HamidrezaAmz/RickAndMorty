package ir.vasl.navtest.view.feature1.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import butterknife.BindView;
import butterknife.OnClick;
import ir.vasl.navigationcomponentimpl.R;
import ir.vasl.navigationcomponentimpl.utils.BaseClasses.BaseFragment;
import ir.vasl.navtest.view.feature2.ActivityFeature2;

public class FragmentF1Fr2 extends BaseFragment {

    @BindView(R.id.textView_temp_arg)
    TextView textViewTempArg;

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
    protected int getFragmentLayout() {
        return R.layout.fragment_f1_fr2;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // get data from navigation
        if (getArguments() != null) {
            String tempArg = FragmentF1Fr2Args.fromBundle(getArguments()).getArgTemp();
            textViewTempArg.setText(tempArg);
        }
    }
}