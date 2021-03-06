package com.example.stuivenvolt.battlegamingtest.Calender;

import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stuivenvolt.battlegamingtest.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DateFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DateFragment extends android.app.Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    ConstraintLayout mScreen;

    private OnFragmentInteractionListener mListener;

    public DateFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DateFragment.
     */
    public static DateFragment newInstance(String param1, String param2) {
        DateFragment fragment = new DateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        View view = inflater.inflate(R.layout.fragment_date, container, false);
        mScreen = view.findViewById(R.id.myScreen);
        mScreen.setBackgroundColor(getBackgroundColor());
        TextView day = view.findViewById(R.id.Day);
        TextView date = view.findViewById(R.id.Date);
        TextView dateInfo = view.findViewById(R.id.DateInfo);

        day.setTextColor(getTextColor());
        date.setTextColor(getTextColor());
        dateInfo.setTextColor(getTextColor());

        date.setText(getArguments().getString("Date"));
        day.setText(getArguments().getString("Day"));

        String tosplit = getArguments().getString("DayInfo").replace("\"", "");
        String[] splitinfo=tosplit.split("\\{|\\}|\\,");
        String joininfo="test";
        for(int i=0;i<splitinfo.length;i++){
            if(joininfo.equals("test")) {
                joininfo = splitinfo[i];
            }else{
                joininfo = joininfo + splitinfo[i] + "\n";
            }
        }
        dateInfo.setText(joininfo);
        day.setBackgroundColor(getArguments().getInt("DayColor"));

        final FloatingActionButton addbtn = view.findViewById(R.id.addtraining);
        addbtn.setColorFilter(getTextColor());
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                DialogFragment newFragment = new CreateTrainingSession();
                Bundle bundle = new Bundle();
                bundle.putString("Date",getArguments().getString("Date"));
                bundle.putString("Month",getArguments().getString("Month"));
                Log.e("Month", getArguments().getString("Month"));
                newFragment.setArguments(bundle);
                newFragment.show(getFragmentManager(),"missiles");
            }
        });

        return view;
    }

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
        return prefs.getInt("BGColor", 0xffffffff);
    }

    public int getTextColor(){
        Context context = getActivity();
        SharedPreferences prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return prefs.getInt("TColor", 0xff000000);
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
        void onFragmentInteraction(Uri uri);
    }
}
