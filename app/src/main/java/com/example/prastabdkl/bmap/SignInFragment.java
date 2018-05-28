package com.example.prastabdkl.bmap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by prastabdkl on 2/15/17.
 */

public class SignInFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button sign_in;
    EditText email;
    EditText password;

    public static SignInFragment newInstance(String param1, String param2) {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_sign_in, container, false);

        email = (EditText) v.findViewById(R.id.email);
        password = (EditText) v.findViewById(R.id.password);
        email.setText("admin@example.com");
        password.setText("password");

        sign_in = (Button) v.findViewById(R.id.sign_in);

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInUser();
            }
        });

        return v;
    }

    private void signInUser()
    {
        JsonObject json = new JsonObject();
        json.addProperty("email", email.getText().toString());
        json.addProperty("password", password.getText().toString());

        Ion.with(getActivity().getApplicationContext())
                .load("https://serene-badlands-22797.herokuapp.com/api/v1/authenticate")
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result != null) {
                            if (result.get("auth_token") != null){
                                String str = result.get("auth_token").getAsString();
                                int req_id = result.get("req_id").getAsInt();
                                TokenAndId tokenAndId = new TokenAndId(str, req_id);
                                Toast.makeText(getActivity().getApplicationContext(), "Logged in successfully.", Toast.LENGTH_LONG).show();
                                UserProfileFragment userProfileFragment = new UserProfileFragment();
                                getFragmentManager().beginTransaction()
                                        .replace(R.id.main_container, new HomeFragment())
                                        .addToBackStack(null)
                                        .commit();
                            }
                        }
                        else{
                            Toast.makeText(getActivity().getApplicationContext(), "Please check your internet connection", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
