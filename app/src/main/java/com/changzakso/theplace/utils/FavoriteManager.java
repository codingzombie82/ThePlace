package com.changzakso.theplace.utils;

import android.os.Handler;

import com.changzakso.theplace.remote.RemoteService;
import com.changzakso.theplace.remote.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;

public class FavoriteManager {
    public final String TAG = FavoriteManager.class.getSimpleName();
    private volatile static FavoriteManager instance;

    public static FavoriteManager getInstance() {
        if (instance == null) {
            synchronized (FavoriteManager.class) {
                if (instance == null) {
                    instance = new FavoriteManager();
                }
            }
        }
        return instance;
    }

//    /**
//     * 즐겨찾기 추가를 서버에 요청한다.
//     *
//     * @param handler   결과를 응답할 핸들러
//     * @param memberSeq 사용자 일련번호
//     * @param infoSeq   장소 정보 일련번호
//     */
//    public void insertKeep(final Handler handler, int memberSeq, final int infoSeq) {
//        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);
//
//        Call<String> call = remoteService.insertKeep(memberSeq, infoSeq);
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                if (response.isSuccessful()) {
//                    MyLog.d(TAG, "insertKeep " + response);
//                    handler.sendEmptyMessage(infoSeq);
//                } else { // 등록 실패
//                    MyLog.d(TAG, "response error " + response.errorBody());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                MyLog.d(TAG, "no internet connectivity");
//            }
//        });
//    }
//
//    /**
//     * 즐겨찾기 삭제를 서버에 요청한다.
//     *
//     * @param handler   결과를 응답할 핸들러
//     * @param memberSeq 사용자 일련번호
//     * @param infoSeq   장소 정보 일련번호
//     */
//    public void deleteKeep(final Handler handler, int memberSeq, final int infoSeq) {
//        ConnectService remoteService = ServiceGenerator.createService(ConnectService.class);
//
//        Call<String> call = remoteService.deleteKeep(memberSeq, infoSeq);
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                if (response.isSuccessful()) {
//                    MyLog.d(TAG, "deleteKeep " + response);
//                    handler.sendEmptyMessage(infoSeq);
//                } else { // 등록 실패
//                    MyLog.d(TAG, "response error " + response.errorBody());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                MyLog.d(TAG, "no internet connectivity");
//            }
//        });
//    }
}
