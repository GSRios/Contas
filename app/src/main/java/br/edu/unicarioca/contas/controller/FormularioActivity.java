package br.edu.unicarioca.contas.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import br.edu.unicarioca.contas.ContaHelper;
import br.edu.unicarioca.contas.R;
import br.edu.unicarioca.contas.dao.ContaDAO;


public class FormularioActivity extends AppCompatActivity {
    ContaHelper contaHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        Spinner spinner = (Spinner) findViewById(R.id.formulario_tipo_conta);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tipo_contas, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        contaHelper = new ContaHelper(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_form, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_form_ok:
                if (contaHelper.validaConta(FormularioActivity.this)) {
                    ContaDAO app = new ContaDAO(FormularioActivity.this);
                    app.insereConta(contaHelper.getConta());
                    Toast.makeText(FormularioActivity.this, "Conta Salva", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
