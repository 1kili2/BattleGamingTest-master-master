package com.example.stuivenvolt.battlegamingtest;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsItemFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsItemFragment extends android.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ConstraintLayout mScreen;
    private TextView title,article,date;
    private ImageView image;


    private OnFragmentInteractionListener mListener;

    public NewsItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsItemFragment newInstance(String param1, String param2) {
        NewsItemFragment fragment = new NewsItemFragment();
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
        View view = inflater.inflate(R.layout.fragment_news_item, container, false);
        mScreen = (ConstraintLayout) view.findViewById(R.id.myScreen);
        mScreen.setBackgroundColor(getBackgroundColor());
        title=(TextView) view.findViewById(R.id.Title);
        article=(TextView) view.findViewById(R.id.Article);
        image=(ImageView) view.findViewById(R.id.NewsImage);
        date=(TextView) view.findViewById(R.id.Date);

        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setColor(getBackgroundColor());
        gd.setCornerRadius(15.0f);

        title.setBackground(gd);
        title.setTextColor(getTextColor());
        article.setTextColor(getTextColor());

        Picasso.with(getActivity()).load(getArguments().getString("Image")).into(image);
        title.setText(getArguments().getString("Title"));
        article.setText(getArguments().getString("Article"));
        date.setText(getArguments().getString("Date"));

        final FloatingActionButton backbtn = (FloatingActionButton) view.findViewById(R.id.backbutton);
        backbtn.setColorFilter(getTextColor());
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final android.app.FragmentManager fm = (getActivity()).getFragmentManager();
                fm.beginTransaction().replace(R.id.content_frame, new NewsFragment()).commit();
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
