package com.tbm.withdrawal.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface WithdrawalDao {
    @Insert
    void insert(WithdrawalItem... item);

    @Query("SELECT COUNT(*) FROM  withdrawal_list")
    int countItems();

    @Query("SELECT * FROM withdrawal_list")
    List<WithdrawalItem> getAll();

    @Query("DELETE FROM withdrawal_list")
    void nukeTable();

}
