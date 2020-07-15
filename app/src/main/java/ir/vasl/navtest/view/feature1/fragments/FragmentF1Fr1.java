package ir.vasl.navtest.view.feature1.fragments;

import android.os.Bundle;

import androidx.navigation.Navigation;

import butterknife.OnClick;
import ir.vasl.navigationcomponentimpl.R;
import ir.vasl.navigationcomponentimpl.utils.BaseClasses.BaseFragment;

public class FragmentF1Fr1 extends BaseFragment {

    @OnClick(R.id.button_go_to_fragment_2)
    public void OnGoToFragment2Clicked() {

        // temp value
        String temp_value = "here we go ;)";

        // get action use safeArg plugin with value
        FragmentF1Fr1Directions.ActionFragmentF1Fr1ToFragmentF1Fr2 action = FragmentF1Fr1Directions.actionFragmentF1Fr1ToFragmentF1Fr2();
        action.setArgTemp(temp_value);

        // navigate to destination
        Navigation.findNavController(this.getView()).navigate(action);

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