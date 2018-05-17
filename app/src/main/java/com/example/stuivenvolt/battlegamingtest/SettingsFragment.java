package com.example.stuivenvolt.battlegamingtest;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends android.app.Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private boolean bgcvisible=false, tcvisible=false;
    private View view;
    private ImageView arrow;


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
    }
    public int getSeekR(){
        Context context = getActivity();
        SharedPreferences prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return prefs.getInt("seekR", 0xff000000);
    }
    public int getSeekG(){
        Context context = getActivity();
        SharedPreferences prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return prefs.getInt("seekG", 0xff000000);
    }
    public int getSeekB(){
        Context context = getActivity();
        SharedPreferences prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return prefs.getInt("seekB", 0xff000000);
    }
    public int getSeekTR(){
        Context context = getActivity();
        SharedPreferences prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return prefs.getInt("seekTR", 0xff000000);
    }
    public int getSeekTG(){
        Context context = getActivity();
        SharedPreferences prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return prefs.getInt("seekTG", 0xff000000);
    }
    public int getSeekTB(){
        Context context = getActivity();
        SharedPreferences prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return prefs.getInt("seekTB", 0xff000000);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_settings, container, false);

        mScreen = view.findViewById(R.id.myScreen);
        redSeekBar = view.findViewById(R.id.mySeekingBar_R);
        greenSeekBar = view.findViewById(R.id.mySeekingBar_G);
        blueSeekBar = view.findViewById(R.id.mySeekingBar_B);
        titleR = view.findViewById(R.id.titleSeekingBar_R);
        titleG = view.findViewById(R.id.titleSeekingBar_G);
        titleB = view.findViewById(R.id.titleSeekingBar_B);

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
                arrow = (ImageView) view.findViewById(R.id.settings_arrow_1);
                if(bgcvisible==false){
                    redSeekBar.setVisibility(View.VISIBLE);
                    greenSeekBar.setVisibility(View.VISIBLE);
                    blueSeekBar.setVisibility(View.VISIBLE);
                    bgcvisible=true;
                    arrow.setRotation(90);
                }else{
                    redSeekBar.setVisibility(View.GONE);
                    greenSeekBar.setVisibility(View.GONE);
                    blueSeekBar.setVisibility(View.GONE);
                    bgcvisible=false;
                    arrow.setRotation(0);
                }
            }
        });

        final TextView tcvisibility = (TextView) view.findViewById(R.id.textcolor);
        tcvisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(tcvisible==false){
                    arrow = (ImageView) view.findViewById(R.id.settings_arrow_2);
                    titleR.setVisibility(View.VISIBLE);
                    titleG.setVisibility(View.VISIBLE);
                    titleB.setVisibility(View.VISIBLE);
                    tcvisible=true;
                    arrow.setRotation(90);
                }else{
                    titleR.setVisibility(View.GONE);
                    titleG.setVisibility(View.GONE);
                    titleB.setVisibility(View.GONE);
                    tcvisible=false;
                    arrow.setRotation(0);
                }
            }
        });

        final Button savebackground = view.findViewById(R.id.save);
        savebackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                saveData();
                Toast toast = Toast.makeText(getActivity(), "Changes have been saved.", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        final Button resetbackground = view.findViewById(R.id.reset);
        resetbackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                resetData();
                Toast toast = Toast.makeText(getActivity(), "Changes have been reset.", Toast.LENGTH_SHORT);
                toast.show();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("test/0/Entrene/17:00");
                //DatabaseReference testref;
                //Map<String, String> entrenes = new HashMap<>();
                //entrenes.put("Torrent", "Ancient Legends");
                myRef.child("Descampado").setValue("Arcadia");

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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void resetData(){
        Context context = getActivity();
        SharedPreferences prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        int bgColor = 0xff000000
                + seekR * 0x10000
                + seekG * 0x100
                + seekB;
        seekR=255;
        seekG=255;
        seekB=255;
        int tColor = 0xff000000;
        seekTR=0;
        seekTG=0;
        seekTB=0;
        edit.putInt("BGColor", bgColor);
        edit.putInt("TColor", tColor);
        edit.putInt("seekR",seekR);
        edit.putInt("seekG",seekG);
        edit.putInt("seekB",seekB);
        edit.putInt("seekTR",seekTR);
        edit.putInt("seekTG",seekTG);
        edit.putInt("seekTB",seekTB);
        edit.apply();
        final android.app.FragmentManager fm = ((Activity) context).getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame,new SettingsFragment()).commit();
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
        edit.apply();
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

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            updateBackground();
            updateTextcolor();
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
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
        TextView textColor = view.findViewById(R.id.textcolor);
        TextView bgroundColor = view.findViewById(R.id.bgcolor);
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
