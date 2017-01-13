package dave.com.rfiddroid20;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import static dave.com.rfiddroid20.InputRFIDActivity.worker;

public class DataHandlerActivity extends AppCompatActivity {
    private Button back;
    private Button entry;
    private Button exit;
    private Button save;
    private TextView entry_date;
    private TextView exit_date;
    private TextView welcome;
    private View snackView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_handler);

        initViews();
        checkDates();

        welcome.setText(getString(R.string.welcome) + " " + worker.getName());

        entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /// TODO: 12/01/2017 entry cases
                //manage these cases:
                //the worker has never entered -> print a '-' in the last entry TextView
                //                             -> when the worker clicks entry send the date of entry to the database

                if (worker.getEntryDate() != null){
                    //the worker has never entered before
                    Snackbar.make(snackView,getString(R.string.alreadyEntered),Snackbar.LENGTH_SHORT).show();
                }else{
                    //the worker has entered at least one time
                    worker.setEntryDate(new Date(System.currentTimeMillis()));
                    setEntryDate(worker.getEntryDate());
                }
            }
        });

        exit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(worker.getEntryDate() == null){
                    //if the worker has never entered before
                    Snackbar.make(snackView,getString(R.string.neverEntered),Snackbar.LENGTH_SHORT).show();
                }else {
                    //if the worker has entered at least once
                    if (worker.getExitDate() != null) {
                        //the worker has never exited before
                        Snackbar.make(snackView, getString(R.string.alreadyExited), Snackbar.LENGTH_SHORT).show();
                    } else {
                        //the worker has exited at lest one time

                        ///TODO: has the worker done all its daily contract hours?
                        ///YES - print the new last exit date in its TextView and send it to the database
                        ///NO - show him a Dialog where the worker has to write a causal

                        worker.setExitDate(new Date(System.currentTimeMillis()));
                        setExitDate(worker.getExitDate());
                    }
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataHandlerActivity.this.finish();
                //startActivity(new Intent(DataHandlerActivity.this,InputRFIDActivity.class));
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //saveToDB(worker);
                back.setEnabled(false);
                entry.setEnabled(false);
                exit.setEnabled(false);

                Snackbar bar = Snackbar.make(findViewById(R.id.data_handler),getString(R.string.saved),Snackbar.LENGTH_SHORT);
                bar.show();

                new CountDownTimer(2000,1000){

                    @Override
                    public void onTick(long millisUntilFinished) {}

                    @Override
                    public void onFinish() {
                        back.callOnClick();
                    }
                }.start();
            }
        });
    }

    @Override
    public void onBackPressed() {}

    private void checkDates(){
        if (worker.getEntryDate() != null){
            //Già entrato oggi
            setEntryDate(worker.getEntryDate());
        }else{
            //Mai entrato oggi
            entry_date.setText(getString(R.string.lastEntry) + "-");
        }

        if (worker.getExitDate() != null){
            //Gia uscito oggi
            setExitDate(worker.getExitDate());
        }else{
            //Mai uscito oggi
            exit_date.setText(getString(R.string.lastEntry) + "-");
        }
    }

    private void setEntryDate(Date date){
        SimpleDateFormat tf = new SimpleDateFormat("hh:mm:ss - dd/MM/yyyy");
        entry_date.setText( getString(R.string.lastEntry) + tf.format(date));
    }

    private void setExitDate(Date date){
        SimpleDateFormat tf = new SimpleDateFormat("hh:mm:ss - dd/MM/yyyy");
        exit_date.setText( getString(R.string.lastExit) + tf.format(date));
    }

    private void initViews(){
        snackView = findViewById(R.id.data_handler);
        back = (Button) findViewById(R.id.button_back);
        entry = (Button) findViewById(R.id.button_entry);
        exit = (Button) findViewById(R.id.button_exit);
        entry_date = (TextView) findViewById(R.id.last_entry) ;
        exit_date = (TextView) findViewById(R.id.last_exit);
        welcome = (TextView) findViewById(R.id.welcome);
        save = (Button) findViewById(R.id.button_save);
    }

    private void saveToDB(Worker worker){
        WorkerDBHelper dbHelper = new WorkerDBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues newValues = new ContentValues();
        newValues.put(WorkerEntry.COL_NAME,worker.getName());
    }
}
