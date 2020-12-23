package com.example.riegapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.Date;

import Clases.ServidorMQTT;

public class MainActivity extends AppCompatActivity {

    private ServidorMQTT sv = new ServidorMQTT();
    private String MQTTHOST = sv.getIp();
    private String USUARIO = sv.getUsuario();
    private String PASSWORD = sv.getPass();
    private ProgressBar progressBar;
    private TextView tvBtn, tvStatusBtn;
    private EditText tiempoRegar;
    private String message;
    private boolean estado = false;
    private String topicsTr;
    MqttAndroidClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvBtn = (TextView) findViewById(R.id.tvInfoNomBt);
        tvStatusBtn = (TextView) findViewById(R.id.tvInfoBtEstado);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        tiempoRegar = (EditText)findViewById(R.id.etIntervalo);

        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(this.getApplicationContext(), MQTTHOST, clientId);

        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(USUARIO);
        options.setPassword(PASSWORD.toCharArray());

        try {
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(getBaseContext(), "Conexión establecida con éxito", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(getBaseContext(), "No se pudo establecer la conexión", Toast.LENGTH_LONG).show();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    private void publicaMQTT(String topic, String msgeToast){
        topicsTr = topic;
        try {
            client.publish(topicsTr, message.getBytes(), 0, false);
            Toast.makeText(getBaseContext(), msgeToast, Toast.LENGTH_SHORT).show();
            if(!tiempoRegar.getText().toString().isEmpty()){
                new Task().execute();
            }
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "No se pudo establecer la conexión", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
    public void RegarUno(View v) {
        if (!estado) {
            tvBtn.setText(" Botón 1");
            tvStatusBtn.setText("Actuando");
            progressBar.setVisibility(View.VISIBLE);
            estado = true;
            message = "1";
        } else {
            tvStatusBtn.setText("Detenido");
            progressBar.setVisibility(View.INVISIBLE);
            estado = false;
            message = "0";
        }
        publicaMQTT("adrian/selenoide01","Enviado Riego 1");
    }

    public void RegarDos(View v) {
        if (!estado) {
            tvBtn.setText(" Botón 2");
            tvStatusBtn.setText("Actuando");
            progressBar.setVisibility(View.VISIBLE);
            estado = true;
            message = "1";
        } else {
            tvStatusBtn.setText("Detenido");
            progressBar.setVisibility(View.INVISIBLE);
            estado = false;
            message = "0";
        }
        publicaMQTT("adrian/selenoide02","Enviado Riego 2");
    }

    public void RegarTres(View v) {
        if (!estado) {
            tvBtn.setText(" Botón 3");
            tvStatusBtn.setText("Actuando");
            progressBar.setVisibility(View.VISIBLE);
            estado = true;
            message = "1";
        } else {
            tvStatusBtn.setText("Detenido");
            progressBar.setVisibility(View.INVISIBLE);
            estado = false;
            message = "0";
        }
        publicaMQTT("adrian/selenoide03","Enviado Riego 3");
    }

    public void RegarCuatro(View v) {
        if (!estado) {
            tvBtn.setText(" Botón 4");
            tvStatusBtn.setText("Actuando");
            progressBar.setVisibility(View.VISIBLE);
            estado = true;
            message = "1";
        } else {
            tvStatusBtn.setText("Detenido");
            progressBar.setVisibility(View.INVISIBLE);
            estado = false;
            message = "0";
        }
        publicaMQTT("adrian/selenoide04","Enviado Riego 4");
    }
    class Task extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {

            for (int i = 1; i <= Integer.parseInt(tiempoRegar.getText().toString()); i++) {
                try {
                    Thread.sleep(1000);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            tvStatusBtn.setText("Detenido");
            progressBar.setVisibility(View.INVISIBLE);
            estado = false;
            message = "0";
            try {
                client.publish(topicsTr, message.getBytes(), 0, false);
            }catch (MqttException e){
                e.printStackTrace();
            }
        }
    }
}
