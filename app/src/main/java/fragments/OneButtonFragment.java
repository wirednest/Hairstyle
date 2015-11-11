package fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wirednest.apps.hairstyle.CameraActivity;
import com.wirednest.apps.hairstyle.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class OneButtonFragment extends Fragment {


    public OneButtonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout root = (LinearLayout) inflater.inflate(R.layout.add_photo,container,false);
        root.findViewById(R.id.addPhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CameraActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }


}
