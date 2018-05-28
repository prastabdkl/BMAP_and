package com.example.prastabdkl.bmap;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import static com.example.prastabdkl.bmap.TokenAndId.getTokenAndIdInstance;
import static com.example.prastabdkl.bmap.TokenAndId.is_admin;
import static com.example.prastabdkl.bmap.TokenAndId.token;
import static com.example.prastabdkl.bmap.TokenAndId.userId;

/**
 * Created by prastab dhakal on 2/22/17.
 */

public class UserProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button sign_in;

    TextView name;
    TextView email;
    TextView address;
    TextView bank_name;
    TextView account_number;
    TextView nationality;
    TextView home;
    TextView mobile;
    TextView work;
    Button account_info;

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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_profile, container, false);

        name = (TextView) v.findViewById(R.id.name_value);
        email = (TextView) v.findViewById(R.id.email_value);
        address = (TextView) v.findViewById(R.id.address_value);
        bank_name = (TextView) v.findViewById(R.id.bank_value);
        account_number = (TextView) v.findViewById(R.id.account_no_value);
        nationality = (TextView) v.findViewById(R.id.nationality_value);
        home = (TextView) v.findViewById(R.id.home_contact_value);
        mobile = (TextView) v.findViewById(R.id.mobile_contact_value);
        work = (TextView) v.findViewById(R.id.work_contact_value);
        account_info = (Button) v.findViewById(R.id.account_info);

        int user_id = userId;
        Ion.with(getActivity().getApplicationContext())
                .load("https://serene-badlands-22797.herokuapp.com/api/v1/users/" + user_id)
                .setHeader("Authorization", token)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result !=null){
                            System.out.println(result.toString());
                            is_admin = result.get("is_admin").getAsBoolean();
                            name.setText(getTokenAndIdInstance().check_if_null(result, "name"));
                            email.setText(getTokenAndIdInstance().check_if_null(result, "email"));
                            address.setText(getTokenAndIdInstance().check_if_null(result, "address"));
                            bank_name.setText(getTokenAndIdInstance().check_if_null(result, "bank_name"));
                            account_number.setText(getTokenAndIdInstance().check_if_null(result, "account_number"));
                            nationality.setText(getTokenAndIdInstance().check_if_null(result, "nationality"));
                            home.setText(getTokenAndIdInstance().check_if_null(result, "home"));
                            mobile.setText(getTokenAndIdInstance().check_if_null(result, "mobile"));
                            work.setText(getTokenAndIdInstance().check_if_null(result, "work"));
                        }
                        else{
                            Toast.makeText(getContext(), "Check network connection", Toast.LENGTH_LONG).show();
                        }
                    }
                });
        final UserAccountFragment ufm = new UserAccountFragment();

        account_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.main_container, ufm)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return v;
    }
}
