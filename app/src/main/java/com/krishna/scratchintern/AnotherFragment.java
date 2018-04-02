package com.krishna.scratchintern;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Console;


public class AnotherFragment extends Fragment {
  Toolbar toolbar;
  String dummy1, dummy2;
  TextView anotherFragmentTextOne, anotherFragmentTextTwo;
  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    LayoutInflater lf = getActivity().getLayoutInflater();

    View view =  lf.inflate(R.layout.fragment_another_fragment, container, false);

    dummy1 = getArguments().getString("Dummy1");
    dummy2 = getArguments().getString("Dummy2");

    anotherFragmentTextOne = view.findViewById(R.id.anotherFragmentTextOne);
    anotherFragmentTextTwo = view.findViewById(R.id.anotherFragmentTextTwo);

    anotherFragmentTextOne.setText(dummy1);
    anotherFragmentTextTwo.setText(dummy2);

    return view;
  }

  //To hide Overflow options
  @Override
  public void onPrepareOptionsMenu(Menu menu) {
    super.onPrepareOptionsMenu(menu);
    //Hiding overflow menu in Another fragment
    MenuItem item=menu.findItem(R.id.overflow_logout);
    item.setVisible(false);
    item = menu.findItem(R.id.overflow_edit_profile);
    item.setVisible(false);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }
}
