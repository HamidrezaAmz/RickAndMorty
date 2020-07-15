package ir.vasl.navtest.view.feature1.fragments;

import android.os.Bundle;

import androidx.navigation.Navigation;

import butterknife.OnClick;
import ir.vasl.navigationcomponentimpl.R;
import ir.vasl.navigationcomponentimpl.utils.BaseClasses.BaseFragment;

public class FragmentF1Fr1 extends BaseFragment {

    @OnClick(R.id.button_go_to_fragment_2)
    public void OnGoToFragment2Clicked() {
        Navigation
                .findNavController(this.getView())
                .navigate(R.id.action_fragmentF1Fr1_to_fragmentF1Fr2);
    }

    public FragmentF1Fr1() {
        // Required empty public constructor
    }

    public static FragmentF1Fr1 newInstance() {

        Bundle args = new Bundle();

        FragmentF1Fr1 fragment = new FragmentF1Fr1();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_f1_fr1;
    }


}