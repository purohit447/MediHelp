package com.first.MediHelp.ui.Appointments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AppointmentViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AppointmentViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}