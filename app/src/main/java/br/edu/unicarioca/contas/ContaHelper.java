package br.edu.unicarioca.contas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import br.edu.unicarioca.contas.controller.FormularioActivity;
import br.edu.unicarioca.contas.controller.MainActivity;
import br.edu.unicarioca.contas.dao.ContaDAO;
import br.edu.unicarioca.contas.modelo.Conta;

public class ContaHelper {

    private EditText valor;
    private EditText descricao;
    private Spinner tipoConta;

    public ContaHelper(FormularioActivity activity){
        valor = (EditText) activity.findViewById(R.id.formulario_valor);
        descricao = (EditText) activity.findViewById(R.id.formulario_descricao);
        tipoConta = (Spinner) activity.findViewById(R.id.formulario_tipo_conta);
    }

    public Conta getConta(){
        Conta conta = new Conta();
        conta.setDescricao(descricao.getText().toString());
        conta.setValor(Double.parseDouble(valor.getText().toString()));
        conta.setTipo(tipoConta.getSelectedItemPosition());
        return conta;
    }

    public boolean validaConta(FormularioActivity ac){
        boolean retorno = true;

        if("".equals(descricao.getText().toString())){
            Toast.makeText(ac.getApplicationContext(), "Favor preencher a descricação da conta", Toast.LENGTH_SHORT).show();
            return false;
        }

        if("".equals(valor.getText().toString())){
            Toast.makeText(ac.getApplicationContext(), "Favor inserir o valor da conta", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(tipoConta.getSelectedItemPosition() == 0){
            Toast.makeText(ac.getApplicationContext(), "Favor selecionar o tipo da conta", Toast.LENGTH_SHORT).show();
            return false;
        }
        return retorno;
    }

    public static Double geraSaldoDisponivel(List<Conta> lista){
        Double saldoAux = new Double(0.0);
        for(Conta conta : lista){
            if(conta.getTipo() != 1){
                saldoAux = saldoAux - conta.getValor();
            }else{
                saldoAux = saldoAux + conta.getValor();
            }
        }
        return saldoAux;
    }

    public static void showConta(final Conta c, final AppCompatActivity activity){
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        StringBuilder st = new StringBuilder();
        alert.setTitle("Deseja excluir a conta?");
        st.append("Tipo da Conta: ").append(c.getTipo() != 2? "Receita" : "Despesa").append("\n");
        st.append("Descrição: ").append(c.getDescricao()).append("\n");
        st.append("Valor R$: ").append(c.getValor()).append("\n");
        alert.setMessage(st.toString());

        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                ContaDAO dao = new ContaDAO(activity.getApplicationContext());
                dao.excluirConta((long) c.getId());
                ((MainActivity) activity).reiniciaExterno();
            }
        });

        alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        alert.show();
    }
}
