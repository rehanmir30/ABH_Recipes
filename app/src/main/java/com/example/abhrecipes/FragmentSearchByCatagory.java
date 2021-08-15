package com.example.abhrecipes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.abhrecipes.Model.DishByMain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSearchByCatagory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSearchByCatagory extends Fragment {

    EditText name;
    Button search;

    private ArrayList<DishByMain> Dish_list;
    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentSearchByCatagory() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSearchByCatagory.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSearchByCatagory newInstance(String param1, String param2) {
        FragmentSearchByCatagory fragment = new FragmentSearchByCatagory();
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
       View v= inflater.inflate(R.layout.fragment_search_by_catagory, container, false);

        name = v.findViewById(R.id.dish_name);
        search = v.findViewById(R.id.search);

        mRecyclerView = v.findViewById(R.id.recyclerView);
        Dish_list = new ArrayList<>();

        RequestQueue queue= Volley.newRequestQueue(getContext());

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dish_name=name.getText().toString().trim();
                if (dish_name.isEmpty()){
                    name.setError("Enter a Catagory please!");
                    return;
                }
                else {
                    String url="https://www.themealdb.com/api/json/v1/1/filter.php?c="+dish_name;
                    JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONArray array=response.getJSONArray("meals");
                                for (int i=0;i< array.length();i++){
                                    JSONObject object= array.getJSONObject(i);

                                    String dish_naam=object.getString("strMeal");
                                    String dish_tasweer=object.getString("strMealThumb");

                                    DishByMain dishByMain=new DishByMain(dish_naam,dish_tasweer);
                                    Dish_list.add(dishByMain);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            mRecyclerView.setHasFixedSize(true);
                            mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                            mAdapter = new ExampleAdapter(Dish_list);
                            mRecyclerView.setLayoutManager(mLayoutManager);
                            mRecyclerView.setAdapter(mAdapter);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    queue.add(request);
                }

            }
        });


        return v;
    }
}