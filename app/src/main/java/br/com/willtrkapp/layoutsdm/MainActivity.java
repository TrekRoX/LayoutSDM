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
    private final String KEY_LST_VIEW_TELEFONES = "KEY_LST_VIEW_TELEFONES";
    private final String KEY_LST_VIEW_EMAIL = "KEY_LST_VIEW_EMAIL";
    private EditText nomeEditTex;
    private EditText emailEditTex;
    //private EditText telefoneEditTex;
    private CheckBox notificacoesCheckbox;
    private RadioGroup notificacoesRadioGroup;

    private LinearLayout telefoneLinearLayout;
    private LinearLayout emailLinearLayout;
    private ArrayList<View> telefoneArraylist;
    private ArrayList<View> emailArrayList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroll_view_activity_main);

        notificacoesCheckbox = findViewById(R.id.notificacoesCheckBox);
        notificacoesRadioGroup = findViewById(R.id.notificacoesRadioGroup);
        nomeEditTex = findViewById(R.id.nomeEditText);
        //emailEditTex = findViewById(R.id.emailEditText);
        //telefoneEditTex = findViewById(R.id.telefoneEditText);

        telefoneLinearLayout = findViewById(R.id.telefoneLinearLayout);
        emailLinearLayout = findViewById(R.id.emailLinearLayout);
        telefoneArraylist = new ArrayList<>();
        emailArrayList = new ArrayList<>();

        //Tratando evento de check no checkbox

        /*
        notificacoesCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CheckBox) view).isChecked())
                {
                    notificacoesRadioGroup.setVisibility(View.VISIBLE);
                }
                else
                {
                    notificacoesRadioGroup.setVisibility(View.GONE);
                }
            }
        });*/
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
        if(view.getId() == R.id.adicionarTelefoneButton){
            LayoutInflater layoutInflater = getLayoutInflater();
            View novoTelefoneLayout = layoutInflater.inflate(R.layout.novotelefonelayout, null, false);
            telefoneArraylist.add(novoTelefoneLayout);
            telefoneLinearLayout.addView(novoTelefoneLayout);
        }
    }

    public void adicionarEmail(View view){
        if(view.getId() == R.id.adicionarEmailButton){
            LayoutInflater layoutInflater = getLayoutInflater();
            View novoEmailLayout = layoutInflater.inflate(R.layout.novoemaillayout, null, false);
            emailArrayList.add(novoEmailLayout);
            emailLinearLayout.addView(novoEmailLayout);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //SALVAR OS DADOS DE ESTADO DINÂMICO
        outState.putBoolean(ESTADO_NOTIFICAO_CHECKBOX, notificacoesCheckbox.isChecked());
        outState.putInt(NOTIFICACAO_RADIO_BUTTON_SELECIONADO, notificacoesRadioGroup.getCheckedRadioButtonId());
        outState.putSerializable(KEY_LST_VIEW_TELEFONES, telefoneArraylist);
        outState.putSerializable(KEY_LST_VIEW_EMAIL, emailArrayList);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState != null){

            notificacoesCheckbox.setChecked(savedInstanceState.getBoolean(ESTADO_NOTIFICAO_CHECKBOX, false));
            int idRdb = savedInstanceState.getInt(NOTIFICACAO_RADIO_BUTTON_SELECIONADO, -1);
            if(idRdb != -1)
                notificacoesRadioGroup.check(idRdb);

            if(savedInstanceState.getSerializable(KEY_LST_VIEW_TELEFONES) != null)
            {
                telefoneArraylist = (ArrayList<View>) savedInstanceState.getSerializable(KEY_LST_VIEW_TELEFONES);
                for(int i = 0; i < telefoneArraylist.size(); i++)
                {
                    if(telefoneArraylist.get(i) != null)
                    {
                        try
                        {
                            View holderLayout = telefoneArraylist.get(i);
                            if(holderLayout.getParent()!=null)
                                ((LinearLayout)holderLayout.getParent()).removeView(holderLayout); // <- fix

                            telefoneLinearLayout.addView(telefoneArraylist.get(i));
                        }
                        catch (Exception ex)
                        {
                            Log.v("MYLOG", "Erro: " + ex.getMessage());
                            break;
                        }
                    }
                }
            }

            if(savedInstanceState.getSerializable(KEY_LST_VIEW_EMAIL) != null)
            {
                emailArrayList = (ArrayList<View>) savedInstanceState.getSerializable(KEY_LST_VIEW_EMAIL);
                for(int i = 0; i < emailArrayList.size(); i++)
                {
                    if(emailArrayList.get(i) != null)
                    {
                        try
                        {
                            View holderLayout = emailArrayList.get(i);
                            if(holderLayout.getParent()!=null)
                                ((LinearLayout)holderLayout.getParent()).removeView(holderLayout); // <- fix

                            emailLinearLayout.addView(emailArrayList.get(i));
                        }
                        catch (Exception ex)
                        {
                            Log.v("MYLOG", "Erro: " + ex.getMessage());
                            break;
                        }
                    }
                }
            }


        }
    }

    public void limparFormulario(View botao){
        notificacoesCheckbox.setChecked(false);
        notificacoesRadioGroup.clearCheck();
        nomeEditTex.setText("");
        //emailEditTex.setText("");
        nomeEditTex.requestFocus();
        for(int i = 0; i < telefoneArraylist.size(); i++)  {
            ((EditText)telefoneArraylist.get(i).findViewById(R.id.telefoneEditText)).setText("");
            ((Spinner)telefoneArraylist.get(i).findViewById(R.id.tipoTelefoneSpinner)).setSelection(0);
        }

        for(int i = 0; i < emailArrayList.size(); i++)  {
            ((EditText)emailArrayList.get(i).findViewById(R.id.emailEditText)).setText("");
        }

        telefoneArraylist.clear();
        telefoneLinearLayout.removeAllViews();

        emailArrayList.clear();
        emailLinearLayout.removeAllViews();
    }
}
