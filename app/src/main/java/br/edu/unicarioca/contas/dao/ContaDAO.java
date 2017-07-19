package br.edu.unicarioca.contas.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.edu.unicarioca.contas.modelo.Conta;


public class ContaDAO extends SQLiteOpenHelper {

    public ContaDAO(Context context){
        super(context, "Despesa", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE Contas ( ");
        builder.append("id INTEGER PRIMARY KEY AUTOINCREMENT, ");
        builder.append("descricao TEXT NOT NULL, ");
        builder.append("valor REAL, ");
        builder.append("tipo INTEGER )");
        db.execSQL(builder.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public List<Conta> listarContas(){
        SQLiteDatabase readableDatabase = getReadableDatabase();
        StringBuilder sql = new StringBuilder();
        List<Conta> retorno = new ArrayList<Conta>();
        sql.append("SELECT * ");
        sql.append("FROM Contas ORDER BY ID DESC ");

        Cursor cursor = readableDatabase.rawQuery(sql.toString(), null);


        while (cursor.moveToNext()) {
            Conta cRetorno = new Conta();
            cRetorno.setId(cursor.getInt(cursor.getColumnIndex("id")));
            cRetorno.setValor(cursor.getDouble(cursor.getColumnIndex("valor")));
            cRetorno.setTipo(cursor.getInt(cursor.getColumnIndex("tipo")));
            cRetorno.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));

            retorno.add(cRetorno);
        }
        cursor.close();
        readableDatabase.close();

        return retorno;

    }

    public void insereConta(Conta conta){
        SQLiteDatabase database = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("descricao", conta.getDescricao());
        contentValues.put("valor", conta.getValor());
        contentValues.put("tipo", conta.getTipo());

        database.insert("Contas", null, contentValues);

        database.close();

    }
    public void excluirConta(Long id){
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.delete("Contas", "id = " + id, null);
        writableDatabase.close();

    }
}
