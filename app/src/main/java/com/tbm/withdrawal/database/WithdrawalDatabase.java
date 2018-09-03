package com.tbm.withdrawal.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


@Database(entities = {WithdrawalItem.class}, version = 1, exportSchema = false)
public abstract class WithdrawalDatabase extends RoomDatabase {

    public abstract WithdrawalDao withdrawalDao();

    private static WithdrawalDatabase INSTANCE;

    public static WithdrawalDatabase getDatabase(final Context context){
        if (INSTANCE == null) {
            synchronized (WithdrawalDatabase.class){
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WithdrawalDatabase.class,"Withdrawal_db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
