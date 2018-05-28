package com.example.prastabdkl.bmap;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import static com.example.prastabdkl.bmap.TokenAndId.token;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReceivableFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReceivableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReceivableFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayAdapter<String> listAdapter ;
    ListView receivable_list;

    private OnFragmentInteractionListener mListener;

    public ReceivableFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReceivableFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReceivableFragment newInstance(String param1, String param2) {
        ReceivableFragment fragment = new ReceivableFragment();
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
        View v = inflater.inflate(R.layout.fragment_receivable, container, false);

        receivable_list = (ListView) v.findViewById(R.id.receivable_list);
        listAdapter = new ArrayAdapter<String>(getContext(), R.layout.list_view);

        Ion.with(getActivity().getApplicationContext())
                .load("https://serene-badlands-22797.herokuapp.com/api/v1/capitals?capi_type=Receivable")
                .setHeader("Authorization", token)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (result != null) {
                            System.out.println("Receivable : " + result.toString());
                            for (int i = 0; i < result.size(); i++){
                                listAdapter.add(result.get(i).getAsJsonObject().get("name").getAsString());
                            }
                            receivable_list.setAdapter(listAdapter);
                        } else {
                            Toast.makeText(getContext(), "check internet connection", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        Button addPartyButton = (Button)v.findViewById(R.id.addPartyButton);
        addPartyButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                startActivity(new Intent(getActivity(), AddPartyActivity.class));
            }
        });
        return v;
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
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
