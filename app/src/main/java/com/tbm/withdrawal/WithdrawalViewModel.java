package com.tbm.withdrawal;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.tbm.withdrawal.database.WithdrawalItem;

import java.util.List;

public class WithdrawalViewModel extends AndroidViewModel {

    private WithdrawalRepository mRepository;
    private LiveData<List<WithdrawalItem>> mWithdrawalList;

    public WithdrawalViewModel (Application application){
        super(application);
        mRepository = new WithdrawalRepository(application);
        mWithdrawalList = mRepository.getAllItem();
    }

    public LiveData<List<WithdrawalItem>> getmWithdrawalList() {
        return mWithdrawalList;
    }

    public void insert(WithdrawalItem withdrawalItem){
        mRepository.insert(withdrawalItem);
    }
}
