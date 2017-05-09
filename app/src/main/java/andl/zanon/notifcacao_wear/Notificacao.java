package andl.zanon.notifcacao_wear;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Andre on 04/05/2017.
 */

public class Notificacao {

    private static final String EXTRA_TEXTO = "texto";
    private static final String EXTRA_FALADO = "falado";

    private static PendingIntent criaPendingIntent(Context context, String texto, int idNotificacao){
        Intent intent = new Intent(context, Texto_Activity.class);
        intent.putExtra(EXTRA_TEXTO, texto);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(Texto_Activity.class);
        stackBuilder.addNextIntent(intent);

        return stackBuilder.getPendingIntent(idNotificacao, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static void notificacaoSimples(Context context, String texto, int idNotificacao){
        PendingIntent pendingIntent = criaPendingIntent(context, texto, idNotificacao);

        NotificationCompat.Builder notificacaoSimples = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Notificacao Simples")
                .setContentText(texto)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(idNotificacao, notificacaoSimples.build());
    }

    public static void notificacaoComResposta(Context context, String texto, int idNotificacao){
        PendingIntent pendingIntent = criaPendingIntent(context, texto, idNotificacao);

        RemoteInput remoteInput = new RemoteInput.Builder(EXTRA_FALADO)
                .setLabel("Diga a resposta")
                .build();

        NotificationCompat.Action remoteInputAction = new NotificationCompat.Action.Builder(R.mipmap.ic_launcher, "Responder", pendingIntent)
                .addRemoteInput(remoteInput)
                .build();

        NotificationCompat.WearableExtender wearableExtender = new NotificationCompat.WearableExtender()
                .addAction(remoteInputAction);

        NotificationCompat.Builder notificacaoComResposta = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Notificacao com Resposta")
                .setContentText(texto)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentIntent(pendingIntent)
                .extend(wearableExtender);

        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(idNotificacao, notificacaoComResposta.build());

    }

    public static void notificacaoPaginada(Context context, String texto, int idNotificacao){
        PendingIntent pendingIntent = criaPendingIntent(context, texto, idNotificacao);

        NotificationCompat.Builder principal = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Notificacao Paginada")
                .setContentText(texto)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentIntent(pendingIntent);

        Notification pg2 = new NotificationCompat.Builder(context)
                .setContentText("Pagina 2")
                .build();

        Notification notificacaoPaginada = new NotificationCompat.WearableExtender()
                .addPage(pg2)
                .extend(principal)
                .build();

        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(idNotificacao, notificacaoPaginada);
    }

    private static ArrayList<String> arrayList = new ArrayList<>();
    public static void notificacaoEmpilhada(Context context, String texto, int idNotificacao){
        final String GRUPO = "grupo";
        int contador = arrayList.size() + 1;
        PendingIntent pendingIntent = criaPendingIntent(context, texto, idNotificacao);

        arrayList.add(texto);

        NotificationCompat.Builder individual = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Notificacao Empilhada")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setGroup(GRUPO)
                .setContentIntent(pendingIntent);


        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle()
                .setBigContentTitle("Notificacoes: ")
                .setSummaryText("Recebidas");

        for(String mensagens : arrayList){
            inboxStyle.addLine(mensagens);
        }

        NotificationCompat.Builder agrupada = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Notificacoes: ")
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setStyle(inboxStyle)
                .setGroup(GRUPO)
                .setGroupSummary(true);

        NotificationManagerCompat.from(context).notify(idNotificacao + contador, individual.build());
        NotificationManagerCompat.from(context).notify(idNotificacao, agrupada.build());
    }
}
