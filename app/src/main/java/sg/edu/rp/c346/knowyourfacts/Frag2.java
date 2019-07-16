package sg.edu.rp.c346.knowyourfacts;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class Frag2 extends Fragment {
    ImageView iv;
    Button colorChange;
    LinearLayout linearLayout1;

    public Frag2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag2, container, false);

        iv = view.findViewById(R.id.iv);
        colorChange = view.findViewById(R.id.changeColor);
        linearLayout1 = view.findViewById(R.id.linearLayout1);

        String imageUrl = "https://wtffunfact.com/wp-content/uploads/2019/07/Fun-Animal-Fact-Singing-Seals.png";
        Picasso.with(getActivity()).load(imageUrl).into(iv);

        colorChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Random rand = new Random();
                int r = rand.nextInt(255);
                int g = rand.nextInt(255);
                int b = rand.nextInt(255);

                linearLayout1.setBackgroundColor(Color.rgb(r,g,b));
            }
        });

        return view;
    }

}
