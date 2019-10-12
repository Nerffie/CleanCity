package com.example.cleancity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cleancity.R;
import com.example.cleancity.models.ClusterMarker;
import com.example.cleancity.models.Poubelle;
import com.example.cleancity.models.PoubelleLocation;
import com.example.cleancity.models.User;
import com.example.cleancity.util.MyClusterRendererManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;

import lombok.NonNull;

import static androidx.core.content.ContextCompat.getSystemService;
import static com.example.cleancity.Constants.MAPVIEW_BUNDLE_KEY;

public class Map extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "PoubelleListFragment";

    //widgets
    //private RecyclerView mUserListRecyclerView;
    private MapView mMapView;


    //vars
    private ArrayList<Poubelle> mPoubelleListe = new ArrayList<>();
    private ArrayList<PoubelleLocation> mPoubelleLocations = new ArrayList<>();
    //private UserRecyclerAdapter mUserRecyclerAdapter;

    private GoogleMap mGoogleMap;
    private LatLngBounds mMapBoundary;
    private PoubelleLocation mPoubelleLocation;

    private ClusterManager mClusterManager;
    private MyClusterRendererManager mClusterManagerRenderer;
    private ArrayList<ClusterMarker> mClusterMarkers = new ArrayList<>();

    public static Map newInstance() {
        return new Map();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPoubelleListe = getArguments().getParcelableArrayList(getString(R.string.intent_poubelle_list));
            mPoubelleLocations = getArguments().getParcelableArrayList(getString(R.string.intent_poubelle_locations));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        //mUserListRecyclerView = view.findViewById(R.id.user_list_recycler_view);
        mMapView = view.findViewById(R.id.poubelle_list_map);

        initUserListRecyclerView();

        initGoogleMap(savedInstanceState);

        for (PoubelleLocation poubelleLocation : mPoubelleLocations){
            Log.d(TAG,poubelleLocation.getPoubelle().getId()+ "OnCreateView : Poubelle Location :"+poubelleLocation.getLat()+" "+poubelleLocation.getLon());
        }

        return view;
    }

    private void setCameraView(){

        double bottomBoundary = mPoubelleLocations.get(0).getLat()-.1;
        double leftBoundary = mPoubelleLocations.get(0).getLon()-.1;
        double topBoundary = mPoubelleLocations.get(0).getLat()+.1;
        double rightBoundary = mPoubelleLocations.get(0).getLon()+.1;

        mMapBoundary = new LatLngBounds(
                new LatLng(bottomBoundary, leftBoundary),
                new LatLng(topBoundary, rightBoundary)
        );


        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(mMapBoundary,0));
    }
    private void initGoogleMap(Bundle savedInstanceState){
        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync(this);
    }

    private void initUserListRecyclerView() {
        //mUserRecyclerAdapter = new UserRecyclerAdapter(mUserList);
        //mUserListRecyclerView.setAdapter(mUserRecyclerAdapter);
        //mUserListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void addMapMarkers(){
        if(mGoogleMap != null){

            if(mClusterManager == null){
                mClusterManager = new ClusterManager<ClusterMarker>(getActivity().getApplicationContext(), mGoogleMap);
            }
            if(mClusterManagerRenderer == null){
                mClusterManagerRenderer = new MyClusterRendererManager(
                        getActivity(),
                        mGoogleMap,
                        mClusterManager
                );
                mClusterManager.setRenderer(mClusterManagerRenderer);
            }

            for(PoubelleLocation poubelleLocation: mPoubelleLocations){

                Log.d(TAG, "addMapMarkers: location: " + poubelleLocation.getLon()+" "+poubelleLocation.getLat());
                try{
                    String snippet = "";

                    snippet = "Poubelle numéro #" + poubelleLocation.getPoubelle().getId();


                    int avatar = R.drawable.poubelle; // set the default avatar
                    /*try{
                        avatar = Integer.parseInt(poubelleLocation.getUser().getAvatar());
                    }catch (NumberFormatException e){
                        Log.d(TAG, "addMapMarkers: no avatar for " + userLocation.getUser().getUsername() + ", setting default.");
                    }*/
                    ClusterMarker newClusterMarker = new ClusterMarker(
                            new LatLng(poubelleLocation.getLat(), poubelleLocation.getLon()),
                            poubelleLocation.getPoubelle().getEtat(),
                            snippet,
                            avatar,
                            poubelleLocation.getPoubelle()
                    );
                    mClusterManager.addItem(newClusterMarker);
                    mClusterMarkers.add(newClusterMarker);

                }catch (NullPointerException e){
                    Log.e(TAG, "addMapMarkers: NullPointerException: " + e.getMessage() );
                }

            }
            mClusterManager.cluster();

            setCameraView();
        }
    }




    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);
        //map.addMarker(new MarkerOptions().position((new LatLng(0,0))).title("Marker"));
        mGoogleMap = map;
        //setCameraView();
        addMapMarkers();
    }




    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}