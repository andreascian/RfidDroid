package dave.com.rfiddroid20;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by max on 12/01/2017.
 */

public class WorkerDBHelper extends SQLiteOpenHelper {

    public WorkerDBHelper(Context context){
        super(context,WorkerEntry.TABLE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
