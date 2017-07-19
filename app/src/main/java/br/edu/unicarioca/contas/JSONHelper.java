package br.edu.unicarioca.contas;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import br.edu.unicarioca.contas.controller.MainActivity;
import br.edu.unicarioca.contas.dao.ContaDAO;
import br.edu.unicarioca.contas.modelo.Conta;

public class JSONHelper {

    public static void fazBackup(Context context, MainActivity activity){
        String privateFileStoragePath = getPrivateFileStoragePath(context.getApplicationContext());
        File outputFile = new File(privateFileStoragePath + "/saveJSON.json");
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            fileOutputStream.write(getJSON(activity).getBytes());
            fileOutputStream.close();
            enviarEmail(activity);
        }catch(IOException io){
            Toast.makeText(activity, "Ocorreu um erro ao realizar o backup de suas contas", Toast.LENGTH_SHORT);
        }
    }

    private static String getJSON(MainActivity activity){
        JSONObject retorno = null;
        JSONArray array = new JSONArray();
        ContaDAO contaDAO = new ContaDAO(activity);
        try {
            for (Conta conta : contaDAO.listarContas()) {
                retorno =  new JSONObject();
                retorno.put("id", conta.getId());
                retorno.put("descricao", conta.getDescricao());
                retorno.put("valor", conta.getValor());
                retorno.put("tipo", conta.getTipo());
                array.put(retorno);

            }
        } catch (Exception e) {
            Toast.makeText(activity, "Ocorreu um erro ao realizar o backup de suas contas", Toast.LENGTH_SHORT);
        }finally {
            contaDAO.close();
        }

        return array.toString();
    }

    private static void enviarEmail(MainActivity activity){
        Uri path = Uri.parse("content://br.edu.unicarioca.contas.fileprovider" + getPrivateFileStoragePath(activity.getApplicationContext()) + "/saveJSON.json");
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Backup das suas contas");
        intent.putExtra(Intent.EXTRA_STREAM, path);
        activity.startActivityForResult(Intent.createChooser(intent, "Disparando Email"), activity.RESULT_OK);
    }

    public static String getPrivateFileStoragePath(Context mContext) {
        return mContext.getFilesDir().getAbsolutePath();
    }
}
