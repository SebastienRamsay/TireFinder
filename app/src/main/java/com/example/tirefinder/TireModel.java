package com.example.tirefinder;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class TireModel extends ViewModel {

    MutableLiveData<List<TireInfo>> tiresToDisplay = new MutableLiveData<>(new ArrayList<TireInfo>());
    MutableLiveData<Boolean> updatingTire = new MutableLiveData<>(false);
}
