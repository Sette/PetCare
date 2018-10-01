package ic.ufmt.br.petcare;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static String NAME = "sqlitepet.db";
    private static int VERSAO = 1;

    public DBHelper(Context context){
        super(context, NAME, null,VERSAO);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE [animais] (\n" +
                        "[id] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                        "[nome] VARCHAR(60)  NOT NULL,\n" +
                        "[email] VARCHAR(60)  NOT NULL,\n" +
                        "[imagem] BLOB  NULL\n" +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
