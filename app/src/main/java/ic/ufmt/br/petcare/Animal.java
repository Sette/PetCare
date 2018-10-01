package ic.ufmt.br.petcare;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Date;

public class Animal {

    private int id;
    private String nome;
    private Date nascimento;
    private String email;
    private String raca;
    private byte[] imagem;
    private Bitmap avatar;
    private String urlGravatar;
    private boolean excluir;
    private Context context;

    public Animal(Context context){
        this.context = context;
        id = -1;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        this.urlGravatar = String.format("https://s.gravatar.com/avatar/%s?s=200", Auxilio.md5Hex(this.email));
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
        if (this.imagem != null)
            this.avatar = Auxilio.getImagemBytes(this.imagem);
    }

    public boolean isExcluir() {
        return excluir;
    }

    public void setExcluir(boolean excluir) {
        this.excluir = excluir;
    }

    public int getId() {
        return id;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }

    public String getUrlGravatar() {
        return urlGravatar;
    }

    public void setUrlGravatar(String urlGravatar) {
        this.urlGravatar = urlGravatar;
    }

    public boolean excluir(){
        DBHelper dbHelper = null;
        SQLiteDatabase sqLiteDatabase = null;
        try{
            dbHelper = new DBHelper(context);
            sqLiteDatabase = dbHelper.getWritableDatabase();
            sqLiteDatabase.beginTransaction();


            sqLiteDatabase.delete("animais", "id = ?", new String[]{String.valueOf(this.id)});

            excluir = true;

            sqLiteDatabase.setTransactionSuccessful();
            sqLiteDatabase.endTransaction();

            return true;

        }catch (Exception e){
            e.printStackTrace();
            sqLiteDatabase.endTransaction();
            return false;
        }finally {
            if (sqLiteDatabase != null)
                sqLiteDatabase.close();
            if (dbHelper != null)
                dbHelper.close();

        }
    }

    public boolean salvar(){
        DBHelper dbHelper = null;
        SQLiteDatabase sqLiteDatabase = null;
        try{
            dbHelper = new DBHelper(context);
            sqLiteDatabase = dbHelper.getWritableDatabase();
            String sql = "";
            if (id == -1){
                sql = "INSERT INTO animais (nome,email,imagem) values (?,?,?)";
            }else{
                sql = "UPDATE animais set nome=?,email=?,imagem=? where id=?";
            }
            sqLiteDatabase.beginTransaction();
            SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(sql);
            sqLiteStatement.clearBindings();
            sqLiteStatement.bindString(1,nome);
            sqLiteStatement.bindString(2, email);

            if (imagem != null )
                sqLiteStatement.bindBlob(3, imagem);

            if (id != -1)
                sqLiteStatement.bindString(4,String.valueOf(id));

            sqLiteStatement.executeInsert();
            sqLiteDatabase.setTransactionSuccessful();
            sqLiteDatabase.endTransaction();

            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            if (sqLiteDatabase != null)
                sqLiteDatabase.close();
            if (dbHelper != null)
                dbHelper.close();

        }
    }

    public void carregaAnimalbyid(int id){
        DBHelper dbHelper = null;
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        try{
            dbHelper = new DBHelper(context);
            sqLiteDatabase = dbHelper.getReadableDatabase();
            cursor = sqLiteDatabase.query("animais",null,"id = ?",new String[]{String.valueOf(id)},null,null,null );

            excluir = true;

            while (cursor.moveToNext()){
                this.id = cursor.getInt(cursor.getColumnIndex("id"));
                setNome(cursor.getString(cursor.getColumnIndex("nome")));
                setEmail(cursor.getString(cursor.getColumnIndex("email")));
                setImagem(cursor.getBlob(cursor.getColumnIndex("imagem")));
                excluir = false;

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor != null && (!cursor.isClosed()))
                cursor.close();
            if (sqLiteDatabase != null)
                sqLiteDatabase.close();
            if (dbHelper != null)
                dbHelper.close();
        }
    }

    public ArrayList<Animal> getAnimais(){
        DBHelper dbHelper = null;
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        ArrayList<Animal> animais = new ArrayList<>();
        try{
            dbHelper = new DBHelper(context);
            sqLiteDatabase = dbHelper.getReadableDatabase();
            cursor = sqLiteDatabase.query("animais",null,null,null,null,null,null );
            while (cursor.moveToNext()){
                Animal animal = new Animal(context);
                animal.id = cursor.getInt(cursor.getColumnIndex("id"));
                animal.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                animal.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                animal.setImagem(cursor.getBlob(cursor.getColumnIndex("imagem")));
                animais.add(animal);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor != null && (!cursor.isClosed()))
                cursor.close();
            if (sqLiteDatabase != null)
                sqLiteDatabase.close();
            if (dbHelper != null)
                dbHelper.close();
        }
        return animais;
    }
}
