package com.example.prastabdkl.bmap;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.prastabdkl.bmap.Database.DBHelper;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.Iterator;

import static com.example.prastabdkl.bmap.R.styleable.FloatingActionButton;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Wages.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Wages#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Wages extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    DBHelper mydb;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    FloatingActionButton floatingActionButton;
    ArrayList<String> worker_list;
    ArrayAdapter<String> listAdapter;
    ListView worker_lists;

    public Wages() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Wages.
     */
    // TODO: Rename and change types and number of parameters
    public static Wages newInstance(String param1, String param2) {
        Wages fragment = new Wages();
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
        View v = inflater.inflate(R.layout.fragment_wages, container,false);

        floatingActionButton = (FloatingActionButton)v.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                startActivity(new Intent(getActivity(), AddWorkerActivity.class));
            }
        });

        worker_list = new ArrayList<String>();
        worker_lists = (ListView) v.findViewById(R.id.worker_list);
        listAdapter = new ArrayAdapter<String>(getContext(), R.layout.list_view);
//        worker_list = mydb.getAllWorker(); //Get list of all the workers

//        Ion.with(getContext())
//                .load("https://serene-badlands-22797.herokuapp.com/api/v1/users")
//                .addHeader("Authorization", token)
//                .asJsonArray()
//                .setCallback(new FutureCallback<JsonArray>() {
//                    @Override
//                    public void onCompleted(Exception e, JsonArray result) {
//                        if(result != null){
//                            if(is_admin){
//                                for (int i=0; i < result.size(); i++){
//                                    listAdapter.add(result.get(i).getAsJsonObject().get("name").getAsString());
//                                }
//                                worker_lists.setAdapter(listAdapter);
//                            }
//                        }
//                        else{
//                            Toast.makeText(getContext(), "Check Internet Connection", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });

//        mydb = new DBHelper(getContext());
//
//        worker_list = new ArrayList<String>();
//        worker_list = mydb.getAllWorker(); //Get list of all the workers
//
//        //Log all the worker's names
//        Iterator iter = worker_list.iterator();
//        ListView listView = (ListView) v.findViewById(R.id.worker_list);
//        while (iter.hasNext()) {
//            // if here
//            Log.d("Names", iter.next().toString());
//            ArrayAdapter adapter = new ArrayAdapter<String>(getContext(),
//                    R.layout.list_view, worker_list);
//            listView.setAdapter(adapter);
//        }
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
//        {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                // TODO Auto-generated method stub
//                Toast.makeText(getActivity(), worker_list.get(position), Toast.LENGTH_SHORT).show();
//            }
//        });
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
/*        if (context instanceof OnFragmentInteractionListener) {
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
}
