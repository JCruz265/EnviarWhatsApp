package br.com.ubsf.whatsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Spinner spTipo, spLocal;
    EditText etNome, etTelefone, etOutroLocal;
    LinearLayout layoutLocal;
    Map<String, String[]> locais = new HashMap<>();

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);

        spTipo = findViewById(R.id.spTipo);
        spLocal = findViewById(R.id.spLocal);
        etNome = findViewById(R.id.etNome);
        etTelefone = findViewById(R.id.etTelefone);
        etOutroLocal = findViewById(R.id.etOutroLocal);
        layoutLocal = findViewById(R.id.layoutLocal);

        Button btn = findViewById(R.id.btnEnviar);

        String[] tipos = {
                "Selecione",
                "Consulta",
                "Exames Laboratoriais",
                "Radiografia",
                "Fisioterapia"
        };

        spTipo.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, tipos));

        locais.put("Exames Laboratoriais", new String[]{
                "Posto UBSF Jardim Paraíso",
                "Laboratório Municipal",
                "Outro"
        });

        locais.put("Radiografia", new String[]{
                "INRAD",
                "Outro"
        });

        locais.put("Fisioterapia", new String[]{
                "Fisio Aventureiro",
                "CEFI",
                "Outro"
        });

        spTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> p, View v, int i, long l) {
                String tipo = spTipo.getSelectedItem().toString();
                if (locais.containsKey(tipo)) {
                    layoutLocal.setVisibility(View.VISIBLE);
                    spLocal.setAdapter(new ArrayAdapter<>(
                            MainActivity.this,
                            android.R.layout.simple_spinner_dropdown_item,
                            locais.get(tipo)));
                } else {
                    layoutLocal.setVisibility(View.GONE);
                }
            }
            public void onNothingSelected(AdapterView<?> p) {}
        });

        btn.setOnClickListener(v -> enviar());
    }

    void enviar() {
        String nome = etNome.getText().toString();
        String tel = etTelefone.getText().toString();

        if (nome.isEmpty() || tel.isEmpty()) {
            Toast.makeText(this, "Preencha os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        String msg = "Olá, aqui é da UBSF Jardim Paraíso.\n\n" +
                "Consigo falar com *" + nome + "*?\n\n" +
                "Favor confirmar o recebimento.";

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("whatsapp://send?phone=55" + tel +
                "&text=" + Uri.encode(msg)));
        i.setPackage("com.whatsapp");
        startActivity(i);
    }
}
