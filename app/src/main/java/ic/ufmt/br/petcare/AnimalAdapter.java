package ic.ufmt.br.petcare;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class AnimalAdapter extends ArrayAdapter<Animal> {

    private ArrayList<Animal> animais;


    public AnimalAdapter(@NonNull Context context, @NonNull ArrayList<Animal> animais) {
        super(context,0,animais);
        this.animais = animais;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Animal animal = animais.get(position);

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_animal, null);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageViewAvatar);
        TextView textViewnome = (TextView) convertView.findViewById(R.id.textViewNome);
        TextView textViewemail = (TextView) convertView.findViewById(R.id.textViewEmail);

        try {
            textViewnome.setText(animal.getNome().toString());
            textViewemail.setText(animal.getEmail().toString());
        }catch (Exception e){
            
        }

        if (animal.getAvatar() != null){
            imageView.setImageBitmap(animal.getAvatar());
        }

        return convertView;



    }


}
