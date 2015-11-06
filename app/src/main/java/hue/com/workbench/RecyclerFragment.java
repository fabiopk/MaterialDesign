package hue.com.workbench;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //TODO
                Toast.makeText(getActivity(),"The" + position + " was clciked!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
            //TODO
                Toast.makeText(getActivity(),"The" + position + " was LOONG clciked!", Toast.LENGTH_SHORT).show();
            }
        }));
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

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {

            this.clickListener = clickListener;

            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {

                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null){
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                    super.onLongPress(e);
                }
            });

        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = recyclerView.findChildViewUnder(e.getX(), e.getY());

            if (child != null && gestureDetector.onTouchEvent(e) && clickListener != null){
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            gestureDetector.onTouchEvent(e);
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            gestureDetector.onTouchEvent(e);
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public static interface ClickListener{
        void onClick(View view, int position);
        void onLongClick(View view, int position);

    }
}
