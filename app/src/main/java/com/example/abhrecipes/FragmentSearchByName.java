package com.example.abhrecipes;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSearchByName#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSearchByName extends Fragment {

    EditText name;
    Button search;
    TextView nothing_found,random_name,random_ingredients,random_youtube;
    ImageView random_image;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentSearchByName() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSearchByName.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSearchByName newInstance(String param1, String param2) {
        FragmentSearchByName fragment = new FragmentSearchByName();
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
        View v = inflater.inflate(R.layout.fragment_search_by_name, container, false);

        name = v.findViewById(R.id.dish_name);
        search = v.findViewById(R.id.search);
        nothing_found = v.findViewById(R.id.nothing_found);

        random_name=v.findViewById(R.id.random_name);
        random_image=v.findViewById(R.id.random_img);
        random_ingredients=v.findViewById(R.id.random_ingredients);
        random_youtube=v.findViewById(R.id.random_youtube);

        RequestQueue queue = Volley.newRequestQueue(getContext());

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dish_name = name.getText().toString().trim();
                if (dish_name.isEmpty()) {
                    name.setError("Enter a dish name please");
                    return;
                } else {
                    String name_url = "https://www.themealdb.com/api/json/v1/1/search.php?s=" + dish_name;
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, name_url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            if (response.isNull("meals")) {
                                nothing_found.setVisibility(View.VISIBLE);
                            } else {
                                nothing_found.setVisibility(View.GONE);
                                try {
                                    JSONArray array = response.getJSONArray("meals");
                                    JSONObject object = array.getJSONObject(0);

                                    String name = object.getString("strMeal");
                                    String[] ingredients = new String[21];
                                    for (int i = 1; i <= 20; i++) {
                                        ingredients[i] = object.getString("strIngredient" + i);
                                    }
                                    String image = object.getString("strMealThumb");
                                    String yt_url = object.getString("strYoutube");

                                    random_name.setText(name);
                                    Picasso.get().load(image).into(random_image);
                                    for (int i = 1; i <= 20; i++) {
                                        if (!ingredients[i].isEmpty()) {
                                            random_ingredients.append(i + "  " + ingredients[i] + "\n");
                                        }
                                    }
                                    random_youtube.setText(yt_url);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                random_youtube.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent i = new Intent(getActivity(), Open_web_Links.class);
                                        i.putExtra("url", random_youtube.getText().toString().trim());
                                        startActivity(i);
                                    }
                                });
                            }
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