package fr.ulille.iut.ramponno;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ResponseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ResponseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResponseFragment extends Fragment {
    Data data;
    TextView tvStatus;
    TextView tvResponse;
    TextView tvLblCode;
    TextView tvLocation;

    public ResponseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ResponseFragment.
     */
    public static ResponseFragment newInstance() {
        return new ResponseFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public void setData(Data data) {
        this.data = data;
    }

    public void updateData() {
        tvStatus.setText(String.valueOf(data.getStatusCode()));
        int status = data.getStatusCode();
        if (status == Data.STATUS_IDLE) {
            tvStatus.setText(R.string.lbl_idle);
            tvStatus.setBackgroundColor(getActivity().getResources().getColor(R.color.blank_color));
        } else if (status == Data.STATUS_WAITING) {
            tvStatus.setText(R.string.lbl_waiting);
            tvStatus.setBackgroundColor(getActivity().getResources().getColor(R.color.blank_color));
        } else if (status == Data.STATUS_NETWORK_ERROR) {
            tvStatus.setText(R.string.lbl_network_error);
            tvStatus.setBackgroundColor(getActivity().getResources().getColor(R.color.critical_color));
        } else if (status == Data.STATUS_INTERNAL_ERROR) {
            tvStatus.setText(R.string.lbl_internal_error);
            tvStatus.setBackgroundColor(getActivity().getResources().getColor(R.color.critical_color));
        } else if ((status >= 200) && ((status < 300))) {
            tvStatus.setText(getActivity().getResources().getString(R.string.lbl_status_std, status));
            tvStatus.setBackgroundColor(getActivity().getResources().getColor(R.color.ok_color));
        } else {
            tvStatus.setText(getActivity().getResources().getString(R.string.lbl_status_std, status));
            tvStatus.setBackgroundColor(getActivity().getResources().getColor(R.color.error_color));
        }

        String location = data.getLocation();
        if (location == null) {
            tvLocation.setVisibility(View.GONE);
            tvLocation.setText(R.string.lbl_location_empty);
        } else {
            tvLocation.setVisibility(View.VISIBLE);
            tvLocation.setText(getActivity().getResources().getString(R.string.lbl_location, location));
        }
        tvResponse.setText(data.getResponseContent());
    }
}
