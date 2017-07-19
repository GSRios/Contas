package br.edu.unicarioca.contas.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import br.edu.unicarioca.contas.ContaHelper;
import br.edu.unicarioca.contas.JSONHelper;
import br.edu.unicarioca.contas.R;
import br.edu.unicarioca.contas.dao.ContaDAO;
import br.edu.unicarioca.contas.modelo.Conta;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.main_nova_conta);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forward = new Intent(MainActivity.this, FormularioActivity.class);
                startActivity(forward);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_main_bkp:
                JSONHelper.fazBackup(getApplicationContext(), this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        listarContas();

    }

    public void reiniciaExterno(){
        onResume();
    }


    private void listarContas(){
        ContaDAO contaDAO = new ContaDAO(this);
        List<Conta> aux =  contaDAO.listarContas();
        contaDAO.close();
        TextView textView = (TextView) findViewById(R.id.main_saldo);
        textView.setText("SALDO DISPONÍVEL " + ContaHelper.geraSaldoDisponivel(aux));

        final ListView lista = (ListView) findViewById(R.id.lista_despesas);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Conta conta = (Conta) lista.getItemAtPosition(position);
                ContaHelper.showConta(conta, MainActivity.this);
            }
        });

        ArrayAdapter<Conta> arrAdapter = new ArrayAdapter<Conta>(this, android.R.layout.simple_list_item_1, aux);
        lista.setAdapter(arrAdapter);

    }


}
