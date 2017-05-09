package andl.zanon.notifcacao_wear;

import android.content.Intent;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Texto_Activity extends AppCompatActivity {

    private static final String EXTRA_TEXTO = "texto";
    private static final String EXTRA_FALADO = "falado";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texto_);

        TextView textView = (TextView)findViewById(R.id.textView);

        String voz = getExtraTextoFalado(getIntent());
        String texto = getIntent().getStringExtra(EXTRA_TEXTO);
        if(voz != null){
            texto = " " + voz;
        }

        textView.setText(texto);
    }

    private String getExtraTextoFalado(Intent intent){
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if(remoteInput != null){
            return remoteInput.getCharSequence(EXTRA_FALADO).toString();
        }

        return null;
    }
}
