package com.tbm.withdrawal;

import android.app.Application;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Ignore;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.tbm.withdrawal.database.WithdrawalItem;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WithdrawalRepository withdrawalRepository;
    private Calendar calendar;

    private TextView monthTV;
    private TextView countTV;
    private TextView amountTV;
    private static String month;
    private List<WithdrawalItem> withdrawalItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        withdrawalRepository = new WithdrawalRepository(getApplication());
        withdrawalItemList = withdrawalRepository.getAllItem();
        calendar = Calendar.getInstance();

        month = getMonthForInt(calendar.get(Calendar.MONTH));
        monthTV = findViewById(R.id.month_tv);
        countTV = findViewById(R.id.count_tv);
        amountTV = findViewById(R.id.amount_tv);


        monthTV.setText(month);

        updateViews();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_withdrawal);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (withdrawalItemList.size() != 0) {
                    countTV.setText("Withdrawal times: " + (withdrawalRepository.getLastItemID() + 1));
                    amountTV.setText("Amount: $" + (withdrawalRepository.getLastItemID() * 50 + 50));

                    withdrawalRepository.insert(new WithdrawalItem(withdrawalRepository.getCount() * 50, month));
                    withdrawalItemList.clear();
                    withdrawalItemList = withdrawalRepository.getAllItem();
                    withdrawalItemList.get(0).setCount(withdrawalRepository.getCount());

                } else {
                    countTV.setText("Withdrawl times: 1");
                    amountTV.setText("Amount: $50");

                   withdrawalRepository.insert(new WithdrawalItem(1, 50, month));
                    withdrawalItemList.clear();
                    withdrawalItemList = withdrawalRepository.getAllItem();
                    withdrawalItemList.get(0).setCount(withdrawalRepository.getCount());
                }

               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });


    }

    /**
     * @updateViews populates the views when the oncreate is called
     */
    private void updateViews() {
        if (withdrawalItemList.size() != 0) {
            if (withdrawalItemList.get(0).getMonth().equals(month)) {
                countTV.setText("Withdrawal times: " + (withdrawalRepository.getLastItemID()));
                amountTV.setText("Amount: $" + (withdrawalRepository.getLastItemID() * 50));
                withdrawalItemList.get(0).setCount(withdrawalRepository.getCount());
            } else {
                resetDatabase();
            }

        } else {
            amountTV.setText("Withdrawl amount: $0");
            countTV.setText("Withdrawl times: 0");
        }
    }

    @Ignore
    private String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11) {
            month = months[num];
        }
        return month;
    }

    /**
     * @resetDatabase used to clear database on a new month
     */
    private void resetDatabase() {
        withdrawalRepository.nukeTable();
        withdrawalItemList.get(0).setCount(0);
        withdrawalItemList.clear();
        withdrawalItemList = withdrawalRepository.getAllItem();

        amountTV.setText("Withdrawl amount: $0");
        countTV.setText("Withdrawl times: 0");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
