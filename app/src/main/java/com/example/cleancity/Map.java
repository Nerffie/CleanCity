package com.example.cleancity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import androidx.fragment.app.Fragment;

import com.example.cleancity.models.ClusterMarker;
import com.example.cleancity.models.Poubelle;
import com.example.cleancity.models.PoubelleLocation;

import com.example.cleancity.util.MyClusterRendererManager;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;


import com.google.maps.GeoApiContext;


import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;

import lombok.NonNull;


import static com.example.cleancity.Constants.MAPVIEW_BUNDLE_KEY;

public class Map extends Fragment implements OnMapReadyCallback,GoogleMap.OnInfoWindowClickListener {

    private static final String TAG = "PoubelleListFragment";

    //widgets

    private MapView mMapView;



    //vars
    private ArrayList<Poubelle> mPoubelleListe = new ArrayList<>();
    private ArrayList<PoubelleLocation> mPoubelleLocations = new ArrayList<>();




    private GoogleMap mGoogleMap;
    private LatLngBounds mMapBoundary;

    private GeoApiContext mGeoApiContext;

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
        mMapView = view.findViewById(R.id.poubelle_list_map);
        initGoogleMap(savedInstanceState);
        return view;
    }

    private void setCameraView(){

        double bottomBoundary = mPoubelleLocations.get(4).getLat()-0.01;
        double leftBoundary = mPoubelleLocations.get(4).getLon()-0.01;
        double topBoundary = mPoubelleLocations.get(4).getLat()+0.01;
        double rightBoundary = mPoubelleLocations.get(4).getLon()+0.01;

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




        if(mGeoApiContext == null){
            mGeoApiContext = new GeoApiContext.Builder()
                    .apiKey(getString(R.string.google_maps_key))
                    .build();
        }
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

                    snippet = poubelleLocation.getPoubelle().getEtat();


                    int avatar = R.drawable.poubelle; // set the default avatar

                    ClusterMarker newClusterMarker = new ClusterMarker(
                            new LatLng(poubelleLocation.getLat(), poubelleLocation.getLon()),
                             String.valueOf(poubelleLocation.getPoubelle().getId()),
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
        map.getCameraPosition();
        mGoogleMap = map;
        mGoogleMap.setOnInfoWindowClickListener(this);
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




    @Override
    public void onInfoWindowClick(Marker marker) {


            ClusterMarker current = mClusterMarkers.get(Integer.parseInt(marker.getTitle())-1);
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(
                    "Poubelle # "+current.getTitle()+
                    "\n\nEtat : "+current.getPoubelle().getEtat()+
                    "\n\nTempérature : "+current.getPoubelle().getTemp()+" °C"+
                    "\n\nRemplissage : "+current.getPoubelle().getRempli()+ " %"+
                    "\n\nTrouver le chemin pour aller ?")
                    .setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            dialog.cancel();
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
    }








}