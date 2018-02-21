package com.example.stuivenvolt.battlegamingtest;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.LinkedList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CalenderFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalenderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalenderFragment extends android.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private final LinkedList<String> mDateList = new LinkedList<>();
    private int mCount = 1;
    private RecyclerView mRecyclerView;
    private CalenderAdapter cAdapter;
    FrameLayout mScreen;
    private FloatingActionButton btnPrev, btnNext;

    private OnFragmentInteractionListener mListener;

    public CalenderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalenderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalenderFragment newInstance(String param1, String param2) {
        CalenderFragment fragment = new CalenderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_calender, container, false);

        for (int i = 0; i < 28; i++) {
            mDateList.addLast(""+mCount++);
            Log.d("DateList", mDateList.getLast());
        }
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclercalender);
        // Create an adapter and supply the data to be displayed.
        cAdapter = new CalenderAdapter(getActivity(), mDateList);
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(cAdapter);
        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(), 2));
        mScreen = (FrameLayout) view.findViewById(R.id.myScreen);
        mScreen.setBackgroundColor(getBackgroundColor());

        btnNext = (FloatingActionButton) view.findViewById(R.id.btnNext);
        btnPrev = (FloatingActionButton) view.findViewById(R.id.btnPrev);

        //String bgC=""+getBackgroundColor();

        //btnNext.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(bgC)));
        //btnPrev.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(bgC)));

        btnNext.setColorFilter(getTextColor());
        btnPrev.setColorFilter(getTextColor());

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public int getBackgroundColor(){
        Context context = getActivity();
        SharedPreferences prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int bgColor = prefs.getInt("BGColor", 0xff000000);
        return bgColor;
    }

    public int getTextColor(){
        Context context = getActivity();
        SharedPreferences prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int tColor = prefs.getInt("TColor", 0xff000000);
        return tColor;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
