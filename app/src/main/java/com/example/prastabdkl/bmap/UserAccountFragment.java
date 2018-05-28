package com.example.prastabdkl.bmap;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import static com.example.prastabdkl.bmap.TokenAndId.getTokenAndIdInstance;
import static com.example.prastabdkl.bmap.TokenAndId.token;
import static com.example.prastabdkl.bmap.TokenAndId.userId;

/**
 * Created by prastab dhakal on 2/22/17.
 */

public class UserAccountFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView post;
    TextView salary;
    TextView joining_date;
    TextView working_plan;

    TextView addition_holiday;
    TextView additin_overtime;
    TextView addition_miscellaneous;

    TextView deduction_loan;
    TextView deduction_late;
    TextView deduction_miscellaneous;

    TextView company_deduction_absent;
    TextView company_deduction_wtax;

    TextView net_total_addition;
    TextView net_total_deduction;
    TextView net_pay;

    Button show_users;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_account, container, false);

        post = (TextView) v.findViewById(R.id.post_value);
        salary = (TextView) v.findViewById(R.id.salary_value);
        joining_date = (TextView) v.findViewById(R.id.joining_date_value);
        working_plan = (TextView) v.findViewById(R.id.working_plan_value);

        addition_holiday = (TextView) v.findViewById(R.id.holiday_value);
        additin_overtime = (TextView) v.findViewById(R.id.overtime_value);
        addition_miscellaneous = (TextView) v.findViewById(R.id.miscellaneous_value);

        deduction_loan = (TextView) v.findViewById(R.id.loan_value);
        deduction_late = (TextView) v.findViewById(R.id.late_value);
        deduction_miscellaneous = (TextView) v.findViewById(R.id.miscellaneous_deduction_value);

        company_deduction_absent = (TextView) v.findViewById(R.id.absent_value);
        company_deduction_wtax = (TextView) v.findViewById(R.id.wtax_value);

        net_total_addition = (TextView) v.findViewById(R.id.total_addition_value);
        net_total_deduction = (TextView) v.findViewById(R.id.total_deduction_value);
        net_pay = (TextView) v.findViewById(R.id.net_pay_value);

        show_users = (Button) v.findViewById(R.id.show_users);

        // json parsing
        Ion.with(getActivity().getApplicationContext())
                .load("https://serene-badlands-22797.herokuapp.com/api/v1/account/" + userId)
                .setHeader("Authorization", token)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result !=null){
                            System.out.println(result.toString());
                            post.setText(getTokenAndIdInstance().check_if_null(result, "post"));
                            salary.setText(getTokenAndIdInstance().check_if_null(result, "salary"));
                            joining_date.setText(getTokenAndIdInstance().check_if_null(result, "joining_date"));
                            working_plan.setText(getTokenAndIdInstance().check_if_null(result, "working_plan"));

                            addition_holiday.setText(getTokenAndIdInstance().check_if_null(result, "addition_holiday"));
                            additin_overtime.setText(getTokenAndIdInstance().check_if_null(result, "addition_overtime"));
                            addition_miscellaneous.setText(getTokenAndIdInstance().check_if_null(result, "addition_miscellaneous"));

                            deduction_loan.setText(getTokenAndIdInstance().check_if_null(result, "deduction_loan"));
                            deduction_late.setText(getTokenAndIdInstance().check_if_null(result, "deduction_late"));
                            deduction_miscellaneous.setText(getTokenAndIdInstance().check_if_null(result, "deduction_miscellaneous"));

                            company_deduction_absent.setText(getTokenAndIdInstance().check_if_null(result, "company_deduction_absent"));
                            company_deduction_wtax.setText(getTokenAndIdInstance().check_if_null(result, "company_deduction_wtax"));

                            net_total_addition.setText(getTokenAndIdInstance().check_if_null(result, "net_total_addition"));
                            net_total_deduction.setText(getTokenAndIdInstance().check_if_null(result, "net_total_deduction"));
                            net_pay.setText(getTokenAndIdInstance().check_if_null(result, "net_pay"));
                        }
                        else{
                            Toast.makeText(getContext(), "Check network connection", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        show_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ion.with(getActivity().getApplicationContext())
                        .load("https://serene-badlands-22797.herokuapp.com/api/v1/users")
                        .setHeader("Authorization", token)
                        .asJsonArray()
                        .setCallback(new FutureCallback<JsonArray>() {
                            @Override
                            public void onCompleted(Exception e, JsonArray result) {
                                if (result != null) {
                                    System.out.println(result.toString());
                                } else {
                                    Toast.makeText(getContext(), "check internet connection", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

        return v;
    }
}
