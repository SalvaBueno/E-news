package salva.e_news;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoticiasFragment extends Fragment {
    ImageButton lol,overwatch,clash_royale,csgo,hs,fifa,wow,fornite;

    public NoticiasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_noticias, container, false);

        lol = view.findViewById(R.id.league_of_legends);
        lol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        overwatch = view.findViewById(R.id.overwatch);
        overwatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        clash_royale = view.findViewById(R.id.clash_royale);
        clash_royale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        fifa = view.findViewById(R.id.fifa);
        fifa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        csgo = view.findViewById(R.id.csgo);
        csgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        hs = view.findViewById(R.id.hearhstone);
        hs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        fornite = view.findViewById(R.id.fornite);
        fornite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        wow = view.findViewById(R.id.world_of_warcraft);
        wow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }

}
