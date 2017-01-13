package dave.com.rfiddroid20;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

public class InputRFIDActivity extends AppCompatActivity {
    public static Worker worker;
    private Worker[] office;     //FAKE SQLITE DATABASE
    private AutoCompleteTextView focus;
    private View snackView;
    private String code = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_rfid);

        init();
        initListeners();
    }

    private void initListeners(){
        focus.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (((EditText)view).getText().toString().length() == 11){
                    code = ((EditText)view).getText().toString();
                    code = code.substring(0,10);
                    worker = checkOffice(code);
                    Log.d("test",code);
                    ((EditText)view).setText("");

                    if (worker != null){
                        //Lavoratore presente
                        //InputRFIDActivity.this.finish();
                        startActivity(new Intent(InputRFIDActivity.this,DataHandlerActivity.class));
                    }else{
                        //Lavoratore non presente nel database
                        Snackbar.make(snackView,getString(R.string.noID),Snackbar.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {}

    private Worker checkOffice(String code){
        boolean found = false;
        Worker w = new Worker();

        for (Worker wk : office){
            if (wk.getCode().equals(code)){
                w = wk;
                found = true;
                break;
            }
        }

        if (!found){
            return null;
        }

        return w;
    }

    private void init(){
        //TEST
        office = new Worker[2];
        for (int i=0; i<office.length; i++){
            office[i] = new Worker();
        }
        office[0].setCode("490032fb25");   //1304
        office[0].setName("Mario Rossi");

        office[1].setCode("490032c498");   //0209
        office[1].setName("Mario Bianchi");

        //office[2].setCode("490032fbe5");   //1307
        //office[2].setName("Giovanni Verdi");




        worker = new Worker();
        snackView = findViewById(R.id.main);
        focus = (AutoCompleteTextView) findViewById(R.id.focus);
    }

    /**
    private Worker checkOffice(String code){
        Worker w = new Worker();

        WorkerDBHelper dbHelper = new WorkerDBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] entries = {
                WorkerEntry.COL_NAME,
                WorkerEntry.COL_ID,
        };

        Cursor cur = db.query(
                WorkerEntry.TABLE_NAME,
                entries,
                null,
                null,
                null,
                null,
                null
        );

        if (cur.getCount() == 0){
            cur.close();
            return null;
        }else{
            while(cur.moveToNext()){
                worker.setName(cur.getString(cur.getColumnIndexOrThrow("NAME")));
            }
            cur.close();
        }

        return w;
    }**/
}