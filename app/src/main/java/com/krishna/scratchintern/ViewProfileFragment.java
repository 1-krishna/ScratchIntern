package com.krishna.scratchintern;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ViewProfileFragment extends Fragment {

  TextView nameTextView, emailTextView, mobileTextView;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



    LayoutInflater lf = getActivity().getLayoutInflater();

    View view =  lf.inflate(R.layout.fragment_view_profile, container, false);

    nameTextView = view.findViewById(R.id.nameTextView);
    emailTextView = view.findViewById(R.id.emailTextView);
    mobileTextView = view.findViewById(R.id.mobileTextView);

    nameTextView.setText(MainActivity.name);
    emailTextView.setText(MainActivity.email);
    mobileTextView.setText(MainActivity.mobile);

    return view;
  }

  @Override
  public void onResume() {
    super.onResume();

    nameTextView.setText(MainActivity.name);
    emailTextView.setText(MainActivity.email);
    mobileTextView.setText(MainActivity.mobile);

  }
}
