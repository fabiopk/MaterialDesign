package hue.com.workbench;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;


public class RecyclerFragment extends Fragment {

    private static HueDatabaseAdapter hueHelper;
    private RecyclerView recyclerView;
    private RestAdapter adapter;
    private static Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        hueHelper = new HueDatabaseAdapter(getActivity());
        View layout = inflater.inflate(R.layout.recycler_fragment, container, false);

        recyclerView = (RecyclerView) layout.findViewById(R.id.recyclerList);
        try {
            adapter = new RestAdapter(getActivity(), getData());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }

    public static List<Restaurante> getData() throws MalformedURLException {
//        List<Restaurante> data = new ArrayList<>();
        hueHelper = new HueDatabaseAdapter(context);
        List<Restaurante> data = hueHelper.getRestaurante();
//        for(int i=0; i<lista.size();i++){
//            Restaurante current = new Restaurante();
//            current.name = names[i];
//            data.add(current);
//        }
        return data;
    }

}
