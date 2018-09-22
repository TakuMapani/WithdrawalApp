package com.tbm.withdrawal;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.tbm.withdrawal.database.WithdrawalDao;
import com.tbm.withdrawal.database.WithdrawalDatabase;
import com.tbm.withdrawal.database.WithdrawalItem;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class WithdrawalRepository {

    private WithdrawalDao mWithdrawalDao;
    private LiveData<List<WithdrawalItem>> mWithdrawalList;

    WithdrawalRepository(Application application){
        WithdrawalDatabase db = WithdrawalDatabase.getDatabase(application);
        mWithdrawalDao = db.withdrawalDao();
        mWithdrawalList = mWithdrawalDao.getAll();
    }

    LiveData<List<WithdrawalItem>> getAllItem(){
        return mWithdrawalList;
    }



    WithdrawalItem getLastItem(){
        int count = 0;
        if (count() == 0) {
            return  null;
        }else{
            count = count()-1;
        }
        WithdrawalItem last = null;
        try {
            last = new itemAsyncTask(mWithdrawalDao).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,count).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return last;
    }

    public  void nukeTable(){
        new nukeTableAsyncTask(mWithdrawalDao).execute();
    }

    public void insert(WithdrawalItem withdrawalItem){
        new insertAsyncTask(mWithdrawalDao).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,withdrawalItem);
    }

    public int  count (){

        Integer a = 0;
        try {
            a = new countAsyncTask(mWithdrawalDao).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
       /* int count = a;*/
        return a;
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

    private class nukeTableAsyncTask extends  AsyncTask<Void,Void,Void>{
        private WithdrawalDao mAsyncDao;

        nukeTableAsyncTask(WithdrawalDao dao){
            mAsyncDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncDao.nukeTable();
            return null;
        }
    }

    private class countAsyncTask extends AsyncTask<Void, Void, Integer> {
        private WithdrawalDao mAsyncDao;

        countAsyncTask(WithdrawalDao dao){
            mAsyncDao = dao;
        }

        @Override
        protected Integer doInBackground(Void... voids) {

            return mAsyncDao.countItems();
        }
    }

    private class itemAsyncTask extends AsyncTask<Integer, Void, WithdrawalItem> {
        private WithdrawalDao mAsyncDao;

        itemAsyncTask(WithdrawalDao dao){
            mAsyncDao = dao;
        }


        @Override
        protected WithdrawalItem doInBackground(Integer... integers) {
            return mAsyncDao.getItem(integers[0]);
        }
    }
}
