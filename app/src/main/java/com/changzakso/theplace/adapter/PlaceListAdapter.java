package com.changzakso.theplace.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.changzakso.theplace.R;
import com.changzakso.theplace.data.Constant;
import com.changzakso.theplace.items.ThePlace;
import com.changzakso.theplace.remote.RemoteService;
import com.changzakso.theplace.utils.ChLog;
import com.changzakso.theplace.utils.DialogManager;
import com.changzakso.theplace.utils.GoLib;
import com.changzakso.theplace.utils.StringLib;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListAdapter.ViewHolder> {
    private final String TAG = this.getClass().getSimpleName();

    private Context context;
    private int resource;
    private ArrayList<ThePlace> itemList;


    /**
     * 어댑터 생성자
     * @param context 컨텍스트 객체
     * @param resource 아이템을 보여주기 위해 사용할 리소스 아이디
     * @param itemList 아이템 리스트
     */
    public PlaceListAdapter(Context context, int resource, ArrayList<ThePlace> itemList) {
        this.context = context;
        this.resource = resource;
        this.itemList = itemList;

    }

    /**
     * 특정 아이템의 변경사항을 적용하기 위해 기본 아이템을 새로운 아이템으로 변경한다.
     * @param newItem 새로운 아이템
     */
    public void setItem(ThePlace newItem) {
        for (int i=0; i < itemList.size(); i++) {
            ThePlace item = itemList.get(i);

            if (item.id == newItem.id) {
                itemList.set(i, newItem);
                notifyItemChanged(i);
                break;
            }
        }
    }

    /**
     * 현재 아이템 리스트에 새로운 아이템 리스트를 추가한다.
     * @param itemList 새로운 아이템 리스트
     */
    public void addItemList(ArrayList<ThePlace> itemList) {
        this.itemList.addAll(itemList);
        notifyDataSetChanged();
    }

    /**
     * 즐겨찾기 상태를 변경한다.
     * @param id 장소 정보 시퀀스
     * @param keep 즐겨찾기 추가 유무
     */
    private void changeItemKeep(int id, boolean keep) {
        for (int i=0; i < itemList.size(); i++) {
            if (itemList.get(i).id == id) {
//                itemList.get(i).isKeep = keep;
                notifyItemChanged(i);
                break;
            }
        }
    }

    /**
     * 아이템 크기를 반환한다.
     * @return 아이템 크기
     */
    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    /**
     * 뷰홀더(ViewHolder)를 생성하기 위해 자동으로 호출된다.
     * @param parent 부모 뷰그룹
     * @param viewType 새로운 뷰의 뷰타입
     * @return 뷰홀더 객체
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);

        return new ViewHolder(v);
    }

    /**
     * 뷰홀더(ViewHolder)와 아이템을 리스트 위치에 따라 연동한다.
     * @param holder 뷰홀더 객체
     * @param position 리스트 위치
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ThePlace item = itemList.get(position);
        ChLog.d(TAG, "getView " + item);

//        if (item.isKeep) {
//            holder.keep.setImageResource(R.drawable.ic_keep_on);
//        } else {
        holder.keep.setImageResource(R.drawable.ic_keep_off);
//        }

        holder.name.setText(item.name);
        holder.description.setText(StringLib.getInstance().getSubString(context,
                item.description, Constant.MAX_LENGTH_DESCRIPTION));

        setImage(holder.image, item.mainImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.keep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (item.isKeep) {
//                    DialogLib.getInstance().showKeepDeleteDialog(context,
//                            keepDeleteHandler, memberInfoItem.seq, item.seq);
//                } else {
//                DialogManager.getInstance().showKeepInsertDialog(context,
//                        keepInsertHandler, memberInfoItem.seq, item.id);
//                }
            }
        });
    }

    /**
     * 이미지를 설정한다.
     * @param imageView  이미지를 설정할 뷰
     * @param fileName 이미지 파일이름
     */
    private void setImage(ImageView imageView, String fileName) {
        if (StringLib.getInstance().isBlank(fileName)) {
            Picasso.with(context).load(R.drawable.bg_place_empty).into(imageView);
        } else {
            Picasso.with(context).load(RemoteService.IMAGE_URL + fileName).into(imageView);
        }
    }

    /**
     * 즐겨찾기 추가가 성공한 경우를 처리하는 핸들러
     */
    Handler keepInsertHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            changeItemKeep(msg.what, true);
        }
    };

    /**
     * 즐겨찾기 삭제가 성공한 경우를 처리하는 핸들러
     */
    Handler keepDeleteHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            changeItemKeep(msg.what, false);
        }
    };

    /**
     * 아이템을 보여주기 위한 뷰홀더 클래스
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        ImageView keep;
        TextView name;
        TextView description;

        public ViewHolder(View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.image);
            keep = (ImageView) itemView.findViewById(R.id.keep);
            name = (TextView) itemView.findViewById(R.id.name);
            description = (TextView) itemView.findViewById(R.id.description);
        }
    }
}

