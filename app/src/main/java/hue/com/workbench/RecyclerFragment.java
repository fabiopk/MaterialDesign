package hue.com.workbench;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class RecyclerFragment extends Fragment {

    private RecyclerView recyclerView;
    private RestAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        List<Restaurante> data = new ArrayList<>();
        URL[] urls = {new URL("https://d2hfuis4qoies5.cloudfront.net/80x80/1430118542693431401799_Image_Upload_Preview.png"),
                new URL("http://0.s3.envato.com/files/133140810/wpstickies-premium-image-tagging-plugin.png")};
        String[] names = {"Restaurante1","Restaurante2"};
        for(int i=0; i<names.length;i++){
            Restaurante current = new Restaurante();
            current.name = names[i];
            current.imlink = urls[i];
            data.add(current);
        }
        return data;
    }

}
