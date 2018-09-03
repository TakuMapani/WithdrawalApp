package com.tbm.withdrawal.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;

@Entity(tableName = "withdrawal_list")
public class WithdrawalItem {

    //private static int ID_COUNT = 0;

    @PrimaryKey
    @NonNull
    private int id;

    @ColumnInfo
    private String month;

    @ColumnInfo
    private int amount;

    //Replaced with static variable count might change
   /* @ColumnInfo
    private int countWtihdrawal;*/

    /**
     * The static declaration for @count is used for the id
     * The set count is used whenever the database is update as it is static
     */
    private static int count;

    public static int getCount() {
        return count;
    }

    // TODO: 3/09/2018 find a better way of updating count 
    public static void setCount(int count) {
        WithdrawalItem.count = count;
    }

    public WithdrawalItem( int amount, String month) {
        //this is used to set the ID and also the number of times withdrawn
        count = this.count + 1;
        this.id = count;
        this.month = month;
        this.amount = amount;
        //this.countWtihdrawal = countWtihdrawal;
    }

    @Ignore
    public WithdrawalItem(@NonNull int withdrawalD, int withdrawalAmount, String withdrawalMonth) {
        this.count = withdrawalD;
        this.id = this.count;
        this.amount = withdrawalAmount;
        this.month = withdrawalMonth;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


}
