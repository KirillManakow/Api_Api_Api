package com.example.apiwork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private AdapterMask pAdapter;
    private List<Mask> listZakazis = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView ivProducts = findViewById(R.id.ListZakazis);
        pAdapter = new AdapterMask(MainActivity.this, listZakazis);
        ivProducts.setAdapter(pAdapter);
        configurationNextButton();

        new GetZakazis().execute();
    }


    @Override
    protected void onStart() {
        super.onStart();
        Search();
        Spinner();
    }

    public void Spinner(){
        try {
            Spinner spinner = findViewById(R.id.activity_main_Sorting);
            spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

            List<String> categories = new ArrayList<>();
            categories.add("Имя а-я");
            categories.add("Конфигурация а-я");

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);

            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinner.setAdapter(dataAdapter);

        }
        catch (Exception ignored){
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try {
            ListView listViewDB = findViewById(R.id.ListZakazis);
            String item = parent.getItemAtPosition(position).toString();
            if(item.equals("Имя а-я")){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Collections.sort(listZakazis, Comparator.comparing(o -> o.getUser().toLowerCase(Locale.ROOT)));
                }
                pAdapter = new AdapterMask(MainActivity.this, listZakazis);
                listViewDB.setAdapter(pAdapter);
            }
            else if(item.equals("Конфигурация а-я")){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Collections.sort(listZakazis, Comparator.comparing(o -> o.getKonfiguracia().toLowerCase(Locale.ROOT)));
                }
                pAdapter = new AdapterMask(MainActivity.this, listZakazis);
                listViewDB.setAdapter(pAdapter);
            }
        }
        catch (Exception ex){
            Toast.makeText(parent.getContext(), ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    public void Search() {
        try {

            EditText Poisk = findViewById(R.id.search);
            Poisk.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                    pAdapter.getFilter().filter(s.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
        }
        catch (Exception ex){
            Toast.makeText(MainActivity.this, ex.toString(), Toast.LENGTH_LONG).show();
        }
    }
    private class GetZakazis extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("https://ngknn.ru:5001/NGKNN/МанаковКА/api/zakazis");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                return result.toString();

            } catch (Exception exception) {
                return null;
            }
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try
            {
                JSONArray tempArray = new JSONArray(s);
                for (int i = 0;i<tempArray.length();i++)
                {

                    JSONObject productJson = tempArray.getJSONObject(i);
                    Mask tempProduct = new Mask(
                            productJson.getInt("id_zakaza"),
                            productJson.getString("user"),
                            productJson.getString("konfiguracia"),
                            productJson.getInt("zena"),
                            productJson.getString("Image")
                    );
                    listZakazis.add(tempProduct);
                    pAdapter.notifyDataSetInvalidated();
                }
            } catch (Exception ignored) {

            }
        }


    }
    private void configurationNextButton()
    {
        Button addData = (Button) findViewById(R.id.AddSource);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddApi.class));
            }
        });
    }
}