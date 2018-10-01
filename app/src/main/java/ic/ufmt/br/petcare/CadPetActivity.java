package ic.ufmt.br.petcare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class CadPetActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextNome;
    private EditText editTextEmail;
    private EditText editTextSenha;
    private Button buttonExcluir;
    private Button buttonSalvar;
    private Button buttonCancelar;
    private ImageView imageView;

    private final Animal animal = new Animal(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadanimal);


        editTextNome = (EditText)findViewById(R.id.editTextNome);
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        buttonExcluir = (Button)findViewById(R.id.buttonExcluir);
        buttonCancelar = (Button)findViewById(R.id.buttonCancelar);
        buttonSalvar = (Button)findViewById(R.id.buttonSalvar);
        imageView = (ImageView)findViewById(R.id.imageViewAvatar);

        buttonExcluir.setOnClickListener(this);
        buttonSalvar.setOnClickListener(this);
        buttonCancelar.setOnClickListener(this);

        buttonExcluir.setEnabled(false);

        if (getIntent().getExtras() != null){
            setTitle(getString(R.string.titulo_editando));
            int codigo = getIntent().getExtras().getInt("consulta");
            animal.carregaAnimalbyid(codigo);

            if (animal.getAvatar() != null)
                imageView.setImageBitmap(animal.getAvatar());
            try {
                editTextNome.setText(animal.getNome().toString());
                editTextEmail.setText(animal.getEmail().toString());
            }catch (Exception e ){
                e.printStackTrace();
            }

            buttonExcluir.setEnabled(true);
        }else{
            setTitle(getString(R.string.titulo_incluindo));
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonCancelar : {
                finish();
                break;
            }
            case R.id.buttonExcluir : {
                animal.excluir();
                finish();
                break;
            }
            case R.id.buttonSalvar :{
                boolean valido = true;
                animal.setNome(editTextNome.getText().toString());
                animal.setEmail(editTextEmail.getText().toString());

                carregaImagem();

                if (animal.getNome().equals("")){
                    editTextNome.setError(getString(R.string.obrigatorio));
                    valido = false;
                }

                if (animal.getEmail().equals("")){
                    editTextEmail.setError(getString(R.string.obrigatorio));
                    valido = false;
                }


                if (valido){
                    if (!animal.salvar())
                        System.out.println("erro");

                    finish();
                }
                break;
            }
        }
    }

    private void carregaImagem(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                animal.setImagem(Auxilio.getImagemBytesFromUrl(animal.getUrlGravatar()));
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(animal.getAvatar());
                    }
                });
            }
        });
        thread.start();
        try{
            thread.join();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
