package ic.ufmt.br.petcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ConsultaActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView listViewAnimais;
    private Button buttonFechar;
    private AnimalAdapter animalAdapter;
    private ArrayList<Animal> animais;
    private Animal animalEdicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        buttonFechar = (Button)findViewById(R.id.buttonFechar);
        buttonFechar.setOnClickListener(this);

        listViewAnimais = (ListView)findViewById(R.id.listViewAnimais);
        listViewAnimais.setOnItemClickListener(this);

        animais = new Animal(this).getAnimais();
        animalAdapter = new AnimalAdapter(this,animais);
        listViewAnimais.setAdapter(animalAdapter);


    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Animal usuario = animais.get(position);
        Intent intent = new Intent(this,CadPetActivity.class);
        intent.putExtra("consulta",usuario.getId());
        animalEdicao = usuario;
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (animalEdicao != null){
            animalEdicao.carregaAnimalbyid(animalEdicao.getId());
            if (animalEdicao.isExcluir())
                animais.remove(animalEdicao);
            animalAdapter.notifyDataSetChanged();
        }
    }
}