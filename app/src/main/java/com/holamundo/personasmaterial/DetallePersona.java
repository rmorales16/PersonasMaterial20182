package com.holamundo.personasmaterial;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class DetallePersona extends AppCompatActivity {
    private TextView lblCedula;
    private TextView lblNombre;
    private TextView lblApellido;
    private Bundle bundle;
    private Intent i;
    private ImageView foto;
    private String ced,nomb,apell,id,fot;
    private StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_persona);
        lblCedula = findViewById(R.id.txtCedulaE);
        lblNombre = findViewById(R.id.txtNombreE);
        lblApellido = findViewById(R.id.txtApellidoE);
        foto = findViewById(R.id.fotoE);
        storageReference = FirebaseStorage.getInstance().getReference();
        i = getIntent();
        bundle = i.getBundleExtra("datos");

        ced = bundle.getString("cedula");
        nomb = bundle.getString("nombre");
        apell = bundle.getString("apellido");
        fot = bundle.getString("foto");
        id = bundle.getString("id");

        lblCedula.setText(ced);
        lblNombre.setText(nomb);
        lblApellido.setText(apell);

        storageReference.child(fot).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(foto);
            }
        });

    }

    public void eliminar(View v){
        String positivo, negativo;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.titulo_eliminar));
        builder.setMessage(getResources().getString(R.string.mensaje_eliminar));
        positivo = getResources().getString(R.string.si);
        negativo = getResources().getString(R.string.no);

        builder.setPositiveButton(positivo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Persona p = new Persona();
                p.setId(id);
                p.eliminar();
                onBackPressed();
            }
        });

       builder.setNegativeButton(negativo, new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialogInterface, int i) {

           }
       }) ;

        AlertDialog dialog = builder.create();
        dialog.show();

    }
    public void onBackPressed(){
        finish();
        Intent i = new Intent(DetallePersona.this,Principal.class);
        startActivity(i);
    }
}
