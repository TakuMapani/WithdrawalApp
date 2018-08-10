package com.tbm.withdrawal.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;

@Entity(tableName = "withdrawal_list")
public class WithdrawalItem {

    private static int ID_COUNT = 0;

    @PrimaryKey
    @NonNull
    private int id;

    @ColumnInfo
    private String month;

    @ColumnInfo
    private int amount;

    @ColumnInfo
    private int countWtihdrawal;

    public WithdrawalItem(String month, int amount, int countWtihdrawal) {
        this.id = ID_COUNT;
        ID_COUNT++;
        this.month = month;
        this.amount = amount;
        this.countWtihdrawal = countWtihdrawal;
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

    public int getCountWtihdrawal() {
        return countWtihdrawal;
    }

    public void setCountWtihdrawal(int countWtihdrawal) {
        this.countWtihdrawal = countWtihdrawal;
    }
}
