package andl.zanon.notifcacao_wear;

import android.app.Notification;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    private int NOTIFICACAO_SIMPLES = 1;
    private int NOTIFICACAO_VOZ = 2;
    private int NOTIFICACAO_PAGINADA = 3;
    private int NOTIFICACAO_EMPILHADA = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText)findViewById(R.id.editText);
    }

    public void notificacaoSimples(View view){
        Notificacao.notificacaoSimples(this, editText.getText().toString(), NOTIFICACAO_SIMPLES);
    }

    public void notificacaoVoz(View view){
        Notificacao.notificacaoComResposta(this, editText.getText().toString(), NOTIFICACAO_VOZ);
    }

    public void notificacaoPaginada(View view){
        Notificacao.notificacaoPaginada(this, editText.getText().toString(), NOTIFICACAO_PAGINADA);
    }

    public void notificacaoEmpilhada(View view){
        Notificacao.notificacaoEmpilhada(this, editText.getText().toString(), NOTIFICACAO_EMPILHADA);
    }

}
