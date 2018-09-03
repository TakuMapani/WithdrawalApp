package com.tbm.withdrawal;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.tbm.withdrawal.database.WithdrawalDao;
import com.tbm.withdrawal.database.WithdrawalDatabase;
import com.tbm.withdrawal.database.WithdrawalItem;

import java.util.List;

public class WithdrawalRepository {

    private WithdrawalDao mWithdrawalDao;
    private List<WithdrawalItem> mWithdrawalList;

    WithdrawalRepository(Application application){
        WithdrawalDatabase db = WithdrawalDatabase.getDatabase(application);
        mWithdrawalDao = db.withdrawalDao();
        mWithdrawalList = mWithdrawalDao.getAll();
    }

    List<WithdrawalItem> getAllItem(){
        return mWithdrawalList;
    }

    int getCount(){
        return mWithdrawalDao.countItems();
    }

    int getLastItemID(){
       return mWithdrawalList.get(getCount() - 1).getId();
    }

    public  void nukeTable(){
        mWithdrawalDao.nukeTable();
    }

    public void insert(WithdrawalItem withdrawalItem){
        new insertAsyncTask(mWithdrawalDao).execute(withdrawalItem);
    }

    private static class insertAsyncTask  extends AsyncTask<WithdrawalItem,Void,Void>{
        private  WithdrawalDao mAsyncDao;

        insertAsyncTask(WithdrawalDao dao){
            mAsyncDao = dao;
        }
        @Override
        protected Void doInBackground(WithdrawalItem... params) {
            mAsyncDao.insert(params[0]);
            return null;
        }
    }
}
