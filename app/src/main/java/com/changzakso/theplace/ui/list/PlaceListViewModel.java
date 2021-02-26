package com.changzakso.theplace.ui.list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PlaceListViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PlaceListViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is place List");
    }

    public LiveData<String> getText() {
        return mText;
    }
}