package br.com.willtrkapp.layoutsdm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final String ESTADO_NOTIFICAO_CHECKBOX = "ESTADO_NOTIFICAO_CHECKBOX";
    private final String NOTIFICACAO_RADIO_BUTTON_SELECIONADO = "NOTIFICACAO_RADIO_BUTTON_SELECIONADO";
    private final String KEY_TEL_ARRAY = "KEY_TEL_ARRAY";
    private final String KEY_TEL_SPINNER_VALUE_ARRAY = "KEY_TEL_SPINNER_VALUE_ARRAY";
    private final String KEY_EMAIL_ARRAY = "KEY_EMAIL_ARRAY";
    private EditText nomeEditTex;
    private CheckBox notificacoesCheckbox;
    private RadioGroup notificacoesRadioGroup;
    private LinearLayout telefoneLinearLayout;
    private LinearLayout emailLinearLayout;
    private ArrayList<String> emailArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroll_view_activity_main);

        notificacoesCheckbox = findViewById(R.id.notificacoesCheckBox);
        notificacoesRadioGroup = findViewById(R.id.notificacoesRadioGroup);
        nomeEditTex = findViewById(R.id.nomeEditText);

        telefoneLinearLayout = findViewById(R.id.telefoneLinearLayout);
        emailLinearLayout = findViewById(R.id.emailLinearLayout);
        emailArrayList = new ArrayList<>();


        notificacoesCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){
                notificacoesRadioGroup.setVisibility(View.VISIBLE);
            }
            else{
                notificacoesRadioGroup.setVisibility(View.GONE);
            }
            }
        });

    }

    public void adicionarTelefone(View view){
        if(view.getId() == R.id.adicionarTelefoneButton)
            inflaTelefone(null, null);
    }

    public void adicionarEmail(View view){
        if(view.getId() == R.id.adicionarEmailButton)
            inflaEmail(null);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.v("MYLOG", "Salvando Instância");

        //SALVAR OS DADOS DE ESTADO DINÂMICO
        outState.putBoolean(ESTADO_NOTIFICAO_CHECKBOX, notificacoesCheckbox.isChecked());
        outState.putInt(NOTIFICACAO_RADIO_BUTTON_SELECIONADO, notificacoesRadioGroup.getCheckedRadioButtonId());

        ArrayList<String> telefoneArraylist = new ArrayList<>();
        ArrayList<Integer> telefoneSpinnerValueArrayList = new ArrayList<>();

        for (int i = 0; i < telefoneLinearLayout.getChildCount(); i++) {
            View vwTelefone = telefoneLinearLayout.getChildAt(i);
            telefoneArraylist.add(((EditText)vwTelefone.findViewById(R.id.telefoneEditText)).getText().toString());
            telefoneSpinnerValueArrayList.add(((Spinner)vwTelefone.findViewById(R.id.tipoTelefoneSpinner)).getSelectedItemPosition());
        }
        outState.putStringArrayList(KEY_TEL_ARRAY, telefoneArraylist);
        outState.putIntegerArrayList(KEY_TEL_SPINNER_VALUE_ARRAY, telefoneSpinnerValueArrayList);

        for (int i = 0; i < emailLinearLayout.getChildCount(); i++) {
            View vwEmail = emailLinearLayout.getChildAt(i);
            emailArrayList.add(((EditText)vwEmail.findViewById(R.id.emailEditText)).getText().toString());
        }
        outState.putStringArrayList(KEY_EMAIL_ARRAY, emailArrayList);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Log.v("MYLOG", "Restaurando Instância");

        if(savedInstanceState != null){
            notificacoesCheckbox.setChecked(savedInstanceState.getBoolean(ESTADO_NOTIFICAO_CHECKBOX, false));
            int idRdb = savedInstanceState.getInt(NOTIFICACAO_RADIO_BUTTON_SELECIONADO, -1);
            if(idRdb != -1)
                notificacoesRadioGroup.check(idRdb);

            if(savedInstanceState.getStringArrayList(KEY_TEL_ARRAY) != null)
            {
                ArrayList<String> telefoneArraylist = savedInstanceState.getStringArrayList(KEY_TEL_ARRAY);
                ArrayList<Integer> telefoneSpinnerValueArrayList = savedInstanceState.getIntegerArrayList(KEY_TEL_SPINNER_VALUE_ARRAY);
                for(int i = 0; i < telefoneArraylist.size(); i ++)
                    inflaTelefone(telefoneArraylist.get(i), telefoneSpinnerValueArrayList.get(i));

            }

            if(savedInstanceState.getStringArrayList(KEY_EMAIL_ARRAY) != null)
            {
                ArrayList<String> emailArrayList = savedInstanceState.getStringArrayList(KEY_EMAIL_ARRAY);
                for(int i = 0; i < emailArrayList.size(); i ++)
                    inflaEmail(emailArrayList.get(i));
            }
        }
    }

    private void inflaTelefone(String pTelefone, Integer pSpinnerPosition)
    {
        LayoutInflater layoutInflater = getLayoutInflater();
        View novoTelefoneLayout = layoutInflater.inflate(R.layout.novotelefonelayout, null);
        if(pTelefone != null)
        {
            ((EditText)novoTelefoneLayout.findViewById(R.id.telefoneEditText)).setText(pTelefone);
            ((Spinner)novoTelefoneLayout.findViewById(R.id.tipoTelefoneSpinner)).setSelection(pSpinnerPosition);
        }
        telefoneLinearLayout.addView(novoTelefoneLayout);
    }

    private void inflaEmail(String pEmail)
    {
        LayoutInflater layoutInflater = getLayoutInflater();
        View novoEmailLayout = layoutInflater.inflate(R.layout.novoemaillayout, null, false);
        if(pEmail != null)
            ((EditText)novoEmailLayout.findViewById(R.id.emailEditText)).setText(pEmail);

        emailLinearLayout.addView(novoEmailLayout);
    }

    public void limparFormulario(View botao){
        notificacoesCheckbox.setChecked(false);
        notificacoesRadioGroup.clearCheck();
        nomeEditTex.setText("");
        telefoneLinearLayout.removeAllViews();
        emailLinearLayout.removeAllViews();
        nomeEditTex.requestFocus();
    }
}
