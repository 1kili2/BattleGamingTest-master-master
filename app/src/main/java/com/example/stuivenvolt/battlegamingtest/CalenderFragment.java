package com.example.stuivenvolt.battlegamingtest;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CalenderFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalenderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalenderFragment extends android.app.Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FirebaseAuth mAuth;
    FirebaseUser user;

    private RecyclerView mRecyclerView;
    FrameLayout mScreen;
    private String[][] months={{"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"},{"31","28","31","30","31","30","31","31","30","31","30","31"}};

    private OnFragmentInteractionListener mListener;
    int currentMonth;

    public CalenderFragment() {
        // Required empty public constructor
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_calender, container, false);
        if (getArguments() != null) {
            for(int i=0;i<11;i++){
                if(months[0][i].equals(getArguments().getString("Month"))){
                    currentMonth = i;
                }
            }
        }
        else {
            DateFormat dateFormat = new SimpleDateFormat("M");
            Date month = new Date();
            currentMonth = Integer.parseInt(dateFormat.format(month)) - 1;
            Log.e("Month", dateFormat.format(month));
        }
        titlesetter(currentMonth);


        mRecyclerView = view.findViewById(R.id.recyclercalender);
        // Create an adapter and supply the data to be displayed.

        // Connect the adapter with the RecyclerView.

        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(), 2));
        mScreen = view.findViewById(R.id.myScreen);
        mScreen.setBackgroundColor(getBackgroundColor());
        FloatingActionButton btnNext = view.findViewById(R.id.btnNext);
        FloatingActionButton btnPrev = view.findViewById(R.id.btnPrev);
        new CalenderFetcher(currentMonth, months[0][currentMonth], months[1][currentMonth], mRecyclerView, getActivity()).execute();
        //getDayJsonData();
        btnNext.setColorFilter(getTextColor());
        btnPrev.setColorFilter(getTextColor());

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(currentMonth==0){
                    Toast toast = Toast.makeText(getActivity(), "Aqui empezo todo, el principio de la historia.", Toast.LENGTH_SHORT);
                    toast.show();
                }else {
                    currentMonth--;
                    titlesetter(currentMonth);
                    new CalenderFetcher(currentMonth, months[0][currentMonth], months[1][currentMonth], mRecyclerView, getActivity()).execute();
                    Log.e("Current month", months[0][currentMonth]);
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(currentMonth==11) {
                    Toast toast = Toast.makeText(getActivity(), "Llegastes al ultimo mes, se va a acabar el mundo!", Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    currentMonth++;
                    titlesetter(currentMonth);
                    new CalenderFetcher(currentMonth, months[0][currentMonth], months[1][currentMonth], mRecyclerView, getActivity()).execute();
                    Log.e("Current month", months[0][currentMonth]);
                }
            }
        });

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void titlesetter(int month) {
        getActivity().setTitle(months[0][month]);
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
