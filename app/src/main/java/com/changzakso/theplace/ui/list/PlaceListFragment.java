package com.changzakso.theplace.ui.list;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.changzakso.theplace.AppApplication;
import com.changzakso.theplace.MainActivity;
import com.changzakso.theplace.R;
import com.changzakso.theplace.adapter.EndlessRecyclerViewScrollListener;
import com.changzakso.theplace.adapter.PlaceListAdapter;
import com.changzakso.theplace.data.Constant;
import com.changzakso.theplace.items.ThePlace;
import com.changzakso.theplace.remote.RemoteService;
import com.changzakso.theplace.remote.ServiceGenerator;
import com.changzakso.theplace.ui.gallery.GalleryViewModel;
import com.changzakso.theplace.utils.ChLog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//public class PlaceListFragment extends Fragment {
//
//    private PlaceListViewModel placeListViewModel;
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        placeListViewModel =
//                new ViewModelProvider(this).get(PlaceListViewModel.class);
//        View root = inflater.inflate(R.layout.fragment_place_list, container, false);
//        final TextView textView = root.findViewById(R.id.text_gallery);
//        placeListViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//        return root;
//    }
//}
public class PlaceListFragment extends Fragment implements View.OnClickListener {
    private final String TAG = this.getClass().getSimpleName();

    private Context context;

    private int memberSeq = 0;
    private int listTypeValue = 2;
    private String orderType;

    private RecyclerView placeListView;
    private TextView noDataText;
    private TextView orderMeter;
    private TextView orderFavorite;
    private TextView orderRecent;
    private ImageView listType;

    private PlaceListAdapter placeListAdapter;
    private StaggeredGridLayoutManager layoutManager;
    private EndlessRecyclerViewScrollListener scrollListener;

    public static PlaceListFragment newInstance() {
        PlaceListFragment f = new PlaceListFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = this.getActivity();

//        memberSeq = ((BaseApplication)this.getActivity().getApplication()).getMemberSeq();

        View layout = inflater.inflate(R.layout.fragment_place_list, container, false);

        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.nav_list);

        orderType = Constant.ORDER_TYPE_METER;

        placeListView = (RecyclerView) view.findViewById(R.id.list);
        noDataText = (TextView) view.findViewById(R.id.no_data);
        listType = (ImageView) view.findViewById(R.id.list_type);

        orderMeter = (TextView) view.findViewById(R.id.order_meter);
        orderFavorite = (TextView) view.findViewById(R.id.order_favorite);
        orderRecent = (TextView) view.findViewById(R.id.order_recent);

        orderMeter.setOnClickListener(this);
        orderFavorite.setOnClickListener(this);
        orderRecent.setOnClickListener(this);
        listType.setOnClickListener(this);

        setRecyclerView();

        getListInfo(0);
    }


    /**
     * 장소 정보를 스태거드그리드레이아웃으로 보여주도록 설정한다.
     *
     * @param row 스태거드그리드레이아웃에 사용할 열의 개수
     */
    private void setLayoutManager(int row) {
        layoutManager = new StaggeredGridLayoutManager(row, StaggeredGridLayoutManager.VERTICAL);
        layoutManager
                .setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        placeListView.setLayoutManager(layoutManager);
    }

    /**
     * 리사이클러뷰를 설정하고 스크롤 리스너를 추가한다.
     */
    private void setRecyclerView() {
        setLayoutManager(listTypeValue);

        placeListAdapter = new PlaceListAdapter(context,
                R.layout.row_place_list, new ArrayList<ThePlace>());
        placeListView.setAdapter(placeListAdapter);

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                getListInfo(page);
            }
        };
        placeListView.addOnScrollListener(scrollListener);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.list_type) {
            changeListType();

        } else {
            if (v.getId() == R.id.order_meter) {
                orderType = Constant.ORDER_TYPE_METER;

                setOrderTextColor(R.color.text_color_green,
                        R.color.text_color_black, R.color.text_color_black);

            } else if (v.getId() == R.id.order_favorite) {
                orderType = Constant.ORDER_TYPE_FAVORITE;

                setOrderTextColor(R.color.text_color_black,
                        R.color.text_color_green, R.color.text_color_black);

            } else if (v.getId() == R.id.order_recent) {
                orderType = Constant.ORDER_TYPE_RECENT;

                setOrderTextColor(R.color.text_color_black,
                        R.color.text_color_black, R.color.text_color_green);
            }

            setRecyclerView();
            getListInfo(0);
        }
    }


    private void setOrderTextColor(int color1, int color2, int color3) {
        orderMeter.setTextColor(ContextCompat.getColor(context, color1));
        orderFavorite.setTextColor(ContextCompat.getColor(context, color2));
        orderRecent.setTextColor(ContextCompat.getColor(context, color3));
    }

    /**
     * 리사이클러뷰의 리스트 형태를 변경한다.
     */
    private void changeListType() {
        if (listTypeValue == 1) {
            listTypeValue = 2;
            listType.setImageResource(R.drawable.ic_list2);
        } else {
            listTypeValue = 1;
            listType.setImageResource(R.drawable.ic_list);

        }
        setLayoutManager(listTypeValue);
    }

//    통신 관련
    private void getListInfo(final int currentPage) {
        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

        Call<ArrayList<ThePlace>> call = remoteService.getListInfo();
        call.enqueue(new Callback<ArrayList<ThePlace>>() {
            @Override
            public void onResponse(Call<ArrayList<ThePlace>> call,
                                   Response<ArrayList<ThePlace>> response) {
                ArrayList<ThePlace> list = response.body();

                if (response.isSuccessful() && list != null) {
                    placeListAdapter.addItemList(list);

                    ChLog.d(TAG, "devjinjin UPdate");
                    if (placeListAdapter.getItemCount() == 0) {
                        noDataText.setVisibility(View.VISIBLE);
                    } else {
                        noDataText.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ThePlace>> call, Throwable t) {
                ChLog.d(TAG, "no internet connectivity");
                ChLog.d(TAG, t.toString());
            }
        });
    }
}