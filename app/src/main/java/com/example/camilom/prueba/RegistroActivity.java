package com.example.camilom.prueba;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RegistroActivity extends AppCompatActivity {

    //Declaramos las variables
    private TextInputLayout tilNombre;
    private TextInputLayout tilApellido;
    private TextInputLayout tilEmail;
    private TextInputLayout tilPassword;
    private Button botonRegistro;
    private EditText nombre, apellido, email, password;
    private TextView texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Referencias TILs
        tilNombre = (TextInputLayout) findViewById(R.id.til_nombre);
        tilApellido = (TextInputLayout) findViewById(R.id.til_apellido);
        tilEmail = (TextInputLayout) findViewById(R.id.til_email);
        tilPassword = (TextInputLayout) findViewById(R.id.til_password);

        // Referencias ETs
        nombre = (EditText) findViewById(R.id.etNombre);
        apellido = (EditText) findViewById(R.id.etApellido);
        email = (EditText) findViewById(R.id.etEmail);
        password = (EditText) findViewById(R.id.etPassword);

        nombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilNombre.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        apellido.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilApellido.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                esEmailValido(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilPassword.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        texto = (TextView) findViewById(R.id.tvLogin);
        texto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent registroIntent = new Intent(RegistroActivity.this, LoginActivity.class);
                RegistroActivity.this.startActivity(registroIntent);
            }
        });

        botonRegistro = (Button) findViewById(R.id.botonRegistro);
        botonRegistro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int validar;
                validar = validarDatos();
                if(validar==1) {
                    Thread nt = new Thread() {
                        @Override
                        public void run() {
                            try {
                                enviarPost(nombre.getText().toString(), apellido.getText().toString(), email.getText().toString(), password.getText().toString());
                                //creamos el Intent
                                Intent intent = new Intent(RegistroActivity.this, ContenidoActivity.class);
                                //Iniciamos el activity.
                                startActivity(intent);
                            } catch (Exception e) {
                                // TODO: handle exception
                            }
                        }
                    };
                    nt.start();
                }else{System.out.println("vacio");}
            }
        });
    }

    private boolean esNombreValido(String nombre) {
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        if (!patron.matcher(nombre).matches() || nombre.length() > 20) {
            tilNombre.setError("Nombre inválido");
            return false;
        } else {
            tilNombre.setError(null);
        }

        return true;
    }

    private boolean esApellidoValido(String apellido) {
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        if (!patron.matcher(apellido).matches() || apellido.length() > 20) {
            tilApellido.setError("Nombre inválido");
            return false;
        } else {
            tilApellido.setError(null);
        }

        return true;
    }

    private boolean esEmailValido(String email) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.setError("Correo electrónico inválido");
            return false;
        } else {
            tilEmail.setError(null);
        }

        return true;
    }

    private int validarDatos() {

        int retorno=0;

        String nombre = tilNombre.getEditText().getText().toString();
        String apellido = tilApellido.getEditText().getText().toString();
        String email = tilEmail.getEditText().getText().toString();

        boolean a = esNombreValido(nombre);
        boolean b = esApellidoValido(apellido);
        boolean c = esEmailValido(email);

        if (a && b && c) {
            // OK, se pasa a la siguiente acción
            Toast.makeText(this, "Se guarda el registro", Toast.LENGTH_LONG).show();
            retorno = 1;
        }else{
            Toast.makeText(this, "No se guardo el registro", Toast.LENGTH_LONG).show();
            retorno = 0;
        }
        return retorno;
    }

    public String enviarPost(String nombre, String apellido, String email, String password) {

        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpPost httpPost = new HttpPost("http://10.0.2.2/appfarma/registrar.php");
        HttpResponse response = null;

        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>(4);
            params.add(new BasicNameValuePair("nombre", nombre));
            params.add(new BasicNameValuePair("apellido", apellido));
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("password", password));

            httpPost.setEntity(new UrlEncodedFormEntity(params));
            response = httpClient.execute(httpPost, localContext);
            //}
        } catch (Exception e) {
            // TODO: handle exception
        }
        return response.toString();
    }
}
