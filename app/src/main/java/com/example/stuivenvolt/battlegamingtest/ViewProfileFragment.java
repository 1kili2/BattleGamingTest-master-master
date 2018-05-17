package com.example.stuivenvolt.battlegamingtest;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewProfileFragment extends android.app.Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    DatabaseReference myRef;
    FirebaseUser user;
    FirebaseAuth mAuth;
    private View view;
    boolean dataSet = false;

    private OnFragmentInteractionListener mListener;

    public ViewProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonalProfileFragment.
     */
    public static ViewProfileFragment newInstance(String param1, String param2) {
        ViewProfileFragment fragment = new ViewProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myRef = FirebaseDatabase.getInstance().getReference("usuarios");
        view = inflater.inflate(R.layout.fragment_view_profile, container, false);

        TextView profilePhone = view.findViewById(R.id.profile_set_Phone_Number);
        setData("Phone Number", profilePhone);

        TextView profileName = view.findViewById(R.id.profile_set_Name);
        setData("Name", profileName);

        TextView profileEmail = view.findViewById(R.id.profile_set_Email);
        profileEmail.setText(user.getEmail());

        TextView profileNickName = view.findViewById(R.id.profile_set_Nickname);
        setData("NickName", profileNickName);

        TextView profileMotto = view.findViewById(R.id.profile_Motto);
        setData("Motto", profileMotto);






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

    private void setData(final String child, final TextView field){
        final String mail = user.getEmail().replace("."," ");
        try {
            DatabaseReference profileRef = myRef.child(mail).child("Public").child(child);
            dataSet = false;
            profileRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String data = dataSnapshot.getValue(String.class);
                    if(data != null) {
                        field.setText(data);
                    } else {
                        if(child.equals("Name")) {
                            view.findViewById(R.id.profile_Name_field).setVisibility(View.GONE);
                        }else if (child.equals("Name")){
                            view.findViewById(R.id.profile_Phone_field).setVisibility(View.GONE);
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) { }
            });
        }catch (Throwable e) { }
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
