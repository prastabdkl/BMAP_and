package com.example.prastabdkl.bmap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


public class PayableFragment extends Fragment {

    Button addPartyButton;
    ListView payable_list;
    private ArrayAdapter<String> listAdapter ;

    public PayableFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_payable, container, false);
        payable_list = (ListView) v.findViewById(R.id.payable_list);
        listAdapter = new ArrayAdapter<String>(getContext(), R.layout.list_view);

//        Ion.with(getActivity().getApplicationContext())
//                .load("https://serene-badlands-22797.herokuapp.com/api/v1/capitals?capi_type=Payable")
//                .setHeader("Authorization", token)
//                .asJsonArray()
//                .setCallback(new FutureCallback<JsonArray>() {
//                    @Override
//                    public void onCompleted(Exception e, JsonArray result) {
//                        if (result != null) {
//                            System.out.println("Payable : " + result.toString());
//                            for (int i = 0; i < result.size(); i++){
//                                listAdapter.add(result.get(i).getAsJsonObject().get("name").getAsString());
//                            }
//                            payable_list.setAdapter(listAdapter);
//                        } else {
//                            Toast.makeText(getContext(), "check internet connection", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });


        addPartyButton = (Button)v.findViewById(R.id.addPartyButton);
        addPartyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),AddPartyActivity.class));
            }
        });
        return v;
    }
}
