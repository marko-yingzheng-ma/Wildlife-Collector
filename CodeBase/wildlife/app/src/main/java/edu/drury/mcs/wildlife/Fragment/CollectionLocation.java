package edu.drury.mcs.wildlife.Fragment;


import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import edu.drury.mcs.wildlife.Activity.CreateCollection;
import edu.drury.mcs.wildlife.JavaClass.CollectionObj;
import edu.drury.mcs.wildlife.JavaClass.MyLocation;
import edu.drury.mcs.wildlife.JavaClass.OnDataPassListener;
import edu.drury.mcs.wildlife.JavaClass.ViewpagerFragmentLifecycle;
import edu.drury.mcs.wildlife.R;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectionLocation extends Fragment implements View.OnClickListener, ViewpagerFragmentLifecycle {
    private View layout;
    private Button back, cancel, next, getLocation;
    private LocationManager locationManager;
    private EditText coordinates;
    private EditText latitude;
    private EditText longitude;
    private CollectionObj currentCollection;
    private OnDataPassListener dataListener;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity a;

        if(context instanceof Activity) {
            a = (Activity) context;

            try {
                dataListener = (OnDataPassListener) a;
            } catch (ClassCastException e) {
                throw new ClassCastException(a.toString() + " must implement OnDataPassListener interface");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.collection_location_fragment, container, false);

        currentCollection = ((CreateCollection) getActivity()).getCurrentCollection();

        back = (Button) layout.findViewById(R.id.back);
        cancel = (Button) layout.findViewById(R.id.cancel);
        next = (Button) layout.findViewById(R.id.next);
        getLocation = (Button) layout.findViewById(R.id.getLocation);
        latitude = (EditText) layout.findViewById(R.id.latitude);
        longitude = (EditText) layout.findViewById(R.id.longitude);

        back.setOnClickListener(this);
        cancel.setOnClickListener(this);
        next.setOnClickListener(this);
        getLocation.setOnClickListener(this);

        return layout;
    }

    @Override
    public void onClick(View view) {
        if (view == back) {
            CreateCollection.pager.setCurrentItem(0);
        } else if (view == cancel) {
            getActivity().finish();
        } else if (view == next) {
            dataListener.onDataPass(currentCollection, 2);
        } else if (view == getLocation) {


            MyLocation.LocationResult locationResult = new MyLocation.LocationResult(){
                @Override
                public void gotLocation(Location location){
                    //Got the location!
                    //coordinates.setText(Double.toString(location.getLatitude()) + Double.toString(location.getLongitude()));
                    latitude.setText(Double.toString(location.getLatitude()));
                    longitude.setText(Double.toString(location.getLongitude()));
                    currentCollection.setLocation(location);
                }
            };
            MyLocation myLocation = new MyLocation(getActivity());
            myLocation.getLocation(getActivity(), locationResult);

            /*locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    String latitude = Double.toString(location.getLatitude());
                    String longtitude = Double.toString(location.getLongitude());
                    Message.showMessage(getActivity(),"Latitude " + latitude + " | " + "Longtitude" + longtitude);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };

            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
*/
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG,"CollectionLocation is resumed");
    }

    @Override
    public void onPauseFragment() {
        Log.i(TAG,"Location -- onPauseFragment()");
    }

    @Override
    public void onResumeFragment() {
        Log.i(TAG,"Location -- onResumeFragment()");

    }

    public void setCurrentCollection(CollectionObj collection) {
        this.currentCollection = collection;
        Log.i(TAG,"CurrentCollection Date" + currentCollection.getDate());
    }
}
