package com.example.stuivenvolt.battlegamingtest;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends android.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private boolean bgcvisible=false, tcvisible=false;
    private View view, main;
    private TextView bgroundColor,textColor;


    private int seekR, seekG, seekB,seekTR, seekTG, seekTB;
    SeekBar redSeekBar, greenSeekBar, blueSeekBar, titleR, titleG, titleB;
    FrameLayout mScreen;


    private OnFragmentInteractionListener mListener;

    public SettingsFragment() {
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
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
    public int getSeekR(){
        Context context = getActivity();
        SharedPreferences prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int seekR = prefs.getInt("seekR", 0xff000000);
        return seekR;
    }
    public int getSeekG(){
        Context context = getActivity();
        SharedPreferences prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int seekG = prefs.getInt("seekG", 0xff000000);
        return seekG;
    }
    public int getSeekB(){
        Context context = getActivity();
        SharedPreferences prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int seekB = prefs.getInt("seekB", 0xff000000);
        return seekB;
    }
    public int getSeekTR(){
        Context context = getActivity();
        SharedPreferences prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int seekTR = prefs.getInt("seekTR", 0xff000000);
        return seekTR;
    }
    public int getSeekTG(){
        Context context = getActivity();
        SharedPreferences prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int seekTG = prefs.getInt("seekTG", 0xff000000);
        return seekTG;
    }
    public int getSeekTB(){
        Context context = getActivity();
        SharedPreferences prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int seekTB = prefs.getInt("seekTB", 0xff000000);
        return seekTB;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_settings, container, false);
        main = inflater.inflate(R.layout.activity_main, container, false);

        mScreen = (FrameLayout) view.findViewById(R.id.myScreen);
        redSeekBar = (SeekBar) view.findViewById(R.id.mySeekingBar_R);
        greenSeekBar = (SeekBar) view.findViewById(R.id.mySeekingBar_G);
        blueSeekBar = (SeekBar) view.findViewById(R.id.mySeekingBar_B);
        titleR = (SeekBar) view.findViewById(R.id.titleSeekingBar_R);
        titleG = (SeekBar) view.findViewById(R.id.titleSeekingBar_G);
        titleB = (SeekBar) view.findViewById(R.id.titleSeekingBar_B);

        redSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        greenSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        blueSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        titleR.setOnSeekBarChangeListener(seekBarChangeListener);
        titleG.setOnSeekBarChangeListener(seekBarChangeListener);
        titleB.setOnSeekBarChangeListener(seekBarChangeListener);

        redSeekBar.setProgress(getSeekR());
        greenSeekBar.setProgress(getSeekG());
        blueSeekBar.setProgress(getSeekB());
        titleR.setProgress(getSeekTR());
        titleG.setProgress(getSeekTG());
        titleB.setProgress(getSeekTB());

        redSeekBar.setVisibility(View.GONE);
        greenSeekBar.setVisibility(View.GONE);
        blueSeekBar.setVisibility(View.GONE);
        titleR.setVisibility(View.GONE);
        titleG.setVisibility(View.GONE);
        titleB.setVisibility(View.GONE);



        final TextView bgcvisibility = (TextView) view.findViewById(R.id.bgcolor);
        bgcvisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(bgcvisible==false){
                    redSeekBar.setVisibility(View.VISIBLE);
                    greenSeekBar.setVisibility(View.VISIBLE);
                    blueSeekBar.setVisibility(View.VISIBLE);
                    bgcvisible=true;
                }else{
                    redSeekBar.setVisibility(View.GONE);
                    greenSeekBar.setVisibility(View.GONE);
                    blueSeekBar.setVisibility(View.GONE);
                    bgcvisible=false;
                }
            }
        });

        final TextView tcvisibility = (TextView) view.findViewById(R.id.textcolor);
        tcvisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(tcvisible==false){
                    titleR.setVisibility(View.VISIBLE);
                    titleG.setVisibility(View.VISIBLE);
                    titleB.setVisibility(View.VISIBLE);
                    tcvisible=true;
                }else{
                    titleR.setVisibility(View.GONE);
                    titleG.setVisibility(View.GONE);
                    titleB.setVisibility(View.GONE);
                    tcvisible=false;
                }
            }
        });

        final Button savebackground = (Button) view.findViewById(R.id.save);
        savebackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                saveData();
                Toast toast = Toast.makeText(getActivity(), "Changes have been saved.", Toast.LENGTH_SHORT);
                toast.show();
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }




    private void saveData(){
        Context context = getActivity();
        SharedPreferences prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        int bgColor = 0xff000000
                + seekR * 0x10000
                + seekG * 0x100
                + seekB;
        int tColor = 0xff000000
                + seekTR * 0x10000
                + seekTG * 0x100
                + seekTB;
        edit.putInt("BGColor", bgColor);
        edit.putInt("TColor", tColor);
        edit.putInt("seekR",seekR);
        edit.putInt("seekG",seekG);
        edit.putInt("seekB",seekB);
        edit.putInt("seekTR",seekTR);
        edit.putInt("seekTG",seekTG);
        edit.putInt("seekTB",seekTB);
        edit.commit();
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

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
// TODO Auto-generated method stub
            updateBackground();
            updateTextcolor();
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
// TODO Auto-generated method stub
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
// TODO Auto-generated method stub
        }
    };

    private void updateBackground() {
        seekR = redSeekBar.getProgress();
        seekG = greenSeekBar.getProgress();
        seekB = blueSeekBar.getProgress();
        mScreen.setBackgroundColor(
                0xff000000
                        + seekR * 0x10000
                        + seekG * 0x100
                        + seekB
        );
    }
    private void updateTextcolor() {
        textColor=(TextView) view.findViewById(R.id.textcolor);
        bgroundColor=(TextView) view.findViewById(R.id.bgcolor);
        seekTR = titleR.getProgress();
        seekTG = titleG.getProgress();
        seekTB = titleB.getProgress();
        int newTColor = 0xff000000
                + seekTR * 0x10000
                + seekTG * 0x100
                + seekTB;
        textColor.setTextColor(newTColor);
        bgroundColor.setTextColor(newTColor);
    }
}
