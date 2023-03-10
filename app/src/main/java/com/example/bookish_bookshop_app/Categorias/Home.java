package com.example.bookish_bookshop_app.Categorias;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bookish_bookshop_app.MainActivity;
import com.example.bookish_bookshop_app.R;
import com.example.bookish_bookshop_app.utils.Imagen;
import com.example.bookish_bookshop_app.utils.MyOpenHelper;
import com.example.bookish_bookshop_app.utils.Tablas;


public class Home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View view;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Home() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MyOpenHelper dbHelper = new MyOpenHelper(getContext(),1);
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        final SQLiteDatabase dbw = dbHelper.getWritableDatabase();
        if (db != null) {
            Cursor c = db.rawQuery("SELECT COUNT(*) AS CONTAR FROM Libro", null);
            if (c != null && c.moveToNext()) {
                @SuppressLint("Range") int contador = c.getInt(0);
                if(contador == 0){
                    InsertCategories(db);
                    InsertBooks(db);
                    insertCategoriasxLibro(dbw);
                }
            }
        }
        loadCategoria();
        loadLibros();
    }

    private void loadLibros() {
        LinearLayout layoutCategorias = view.findViewById(R.id.LayoutSugerencias);
        MyOpenHelper dbHelper = new MyOpenHelper(getContext(),1);
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        layoutCategorias.removeAllViews();
        if (db != null) {
            Cursor c = db.rawQuery("SELECT _id, titulo, imagen FROM Libro WHERE estado = 1", null);
            if (c != null && c.moveToNext()) {
                c.moveToFirst();
                int i = 0;
                do {
                    @SuppressLint("Range") int id = c.getInt(0);
                    @SuppressLint("Range") byte[] imagen = c.getBlob(2);
                    @SuppressLint("Range") String nombre = c.getString(1);
                    LinearLayout ly = new LinearLayout(view.getContext());
                    ly.setOrientation(LinearLayout.VERTICAL);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(280, 460);
                    lp.setMargins(20, 20, 20, 20);
                    ImageView img = new ImageView(getContext());
                    TextView txt = new TextView(getContext());
                    txt.setBackgroundColor(Color.parseColor("#20B2AA"));
                    txt.setTextColor(Color.parseColor("white"));
                    img.setId(i);
                    //img.setLayoutParams(lp);
                    Bitmap imgb = Imagen.deserializar(imagen);
                    img.setImageBitmap(imgb);
                    img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    img.setAdjustViewBounds(true);
                    txt.setText(nombre);
                    txt.setGravity(Gravity.CENTER_HORIZONTAL);
                    ly.setTag(id);
                    ly.addView(img, 280, 380);
                    ly.addView(txt, 280, 60);
                    ly.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            MainActivity.replaceFragment(new DetailsFragment((Integer) view.getTag()));
                        }
                    });
                    layoutCategorias.addView(ly, lp);
                } while (c.moveToNext());
            }
            //fragmentTransaction.commit();
        }
    }

    private void loadCategoria() {
        LinearLayout layoutCategorias = view.findViewById(R.id.LayoutCategorias);
        MyOpenHelper dbHelper = new MyOpenHelper(getContext(),1);
        final SQLiteDatabase dbw = dbHelper.getWritableDatabase();
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
/*        if ("hola".equalsIgnoreCase("hoa")) {
            InsertCategories(dbw);
            InsertBooks(dbw);
        }*/
        //insertCategoriasxLibro(dbw);
        layoutCategorias.removeAllViews();
        if (db != null) {
            Cursor c = db.rawQuery("SELECT * FROM categoria WHERE estado = 1", null);
            if (c != null && c.moveToNext()) {
                c.moveToFirst();
                int i = 0;
                do {
                    System.out.println("-============================================================-==============================================================");
                    @SuppressLint("Range") int id = c.getInt(c.getColumnIndex("_id"));
                    @SuppressLint("Range") byte[] imagen = c.getBlob(c.getColumnIndex("imagen"));
                    @SuppressLint("Range") String nombre = c.getString(c.getColumnIndex("nombre")).toString();
                    LinearLayout ly = new LinearLayout(view.getContext());
                    ly.setOrientation(LinearLayout.VERTICAL);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(280, 460);
                    lp.setMargins(20, 20, 20, 20);
                    ImageView img = new ImageView(getContext());
                    TextView txt = new TextView(getContext());
                    txt.setBackgroundColor(Color.parseColor("#20B2AA"));
                    txt.setTextColor(Color.parseColor("white"));
                    img.setId(i);
                    //img.setLayoutParams(lp);
                    Bitmap imgb = Imagen.deserializar(imagen);
                    img.setImageBitmap(imgb);
                    img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    img.setAdjustViewBounds(true);
                    txt.setText(nombre);
                    txt.setGravity(Gravity.CENTER_HORIZONTAL);
                    ly.setTag(id);
                    ly.addView(img, 280, 380);
                    ly.addView(txt, 280, 60);
                    ly.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (view.getTag() != null) {
                                MainActivity.replaceFragment(new SeccionCategoriaFragment((Integer) view.getTag()));
                            }
                        }
                    });
                    layoutCategorias.addView(ly, lp);
                } while (c.moveToNext());
            }
            //fragmentTransaction.commit();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    public void insertCategoriasxLibro(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put("categoria_id", 1);
        cv.put("libro_id", 1);
        db.insert(Tablas.CATEOGRIA_LIBRO, null, cv);
        ContentValues cv1 = new ContentValues();
        cv1.put("categoria_id", 2);
        cv1.put("libro_id", 2);
        db.insert(Tablas.CATEOGRIA_LIBRO, null, cv1);
        ContentValues cv2 = new ContentValues();
        cv2.put("categoria_id", 3);
        cv2.put("libro_id", 3);
        db.insert(Tablas.CATEOGRIA_LIBRO, null, cv2);
        ContentValues cv3 = new ContentValues();
        cv3.put("categoria_id", 4);
        cv3.put("libro_id", 4);
        db.insert(Tablas.CATEOGRIA_LIBRO, null, cv3);
        ContentValues cv4 = new ContentValues();
        cv4.put("categoria_id", 4);
        cv4.put("libro_id", 4);
        db.insert(Tablas.CATEOGRIA_LIBRO, null, cv4);

    }

    public void InsertCategories(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put("_id", 1);
        cv.put("nombre", "Ciencia Ficci??n");
        cv.put("imagen", Imagen.serializar(this.getResources().getDrawable(R.drawable.categoria_cf)));
        cv.put("estado", 1);
        db.insert("categoria", null, cv);

        ContentValues cv1 = new ContentValues();
        cv1.put("_id", 2);
        cv1.put("nombre", "Cl??sicos");
        cv1.put("imagen", Imagen.serializar(this.getResources().getDrawable(R.drawable.categoria_clasic)));
        cv1.put("estado", 1);
        db.insert("categoria", null, cv1);

        ContentValues cv2 = new ContentValues();
        cv2.put("_id", 3);
        cv2.put("nombre", "Hist??rica");
        cv2.put("imagen", Imagen.serializar(this.getResources().getDrawable(R.drawable.categoria_historia)));
        cv2.put("estado", 1);
        db.insert("categoria", null, cv2);

        ContentValues cv3 = new ContentValues();
        cv3.put("_id", 4);
        cv3.put("nombre", "Juvenil");
        cv3.put("imagen", Imagen.serializar(this.getResources().getDrawable(R.drawable.categoria_juvenil)));
        cv3.put("estado", 1);
        db.insert("categoria", null, cv3);

        ContentValues cv5 = new ContentValues();
        cv5.put("_id", 5);
        cv5.put("nombre", "Romance");
        cv5.put("imagen", Imagen.serializar(this.getResources().getDrawable(R.drawable.categoria_romance)));
        cv5.put("estado", 1);
        db.insert("categoria", null, cv5);

        ContentValues cv6 = new ContentValues();
        cv6.put("_id", 6);
        cv6.put("nombre", "Terror");
        cv6.put("imagen", Imagen.serializar(this.getResources().getDrawable(R.drawable.categoria_terror)));
        cv6.put("estado", 1);
        db.insert("categoria", null, cv6);
    }

    public void InsertBooks(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put("_id", 1);
        cv.put("titulo", "Ciencia Ficci??n");
        cv.put("imagen", Imagen.serializar(this.getResources().getDrawable(R.drawable.milnochessinti)));
        cv.put("descripcion", "Tras un par??ntesis en Rusia, para Sofia ha llegado el momento de poner orden a su vida sentimental. Ya no puede seguir huyendo de su pasado, de la soledad de su matrimonio, ni de la historia pasional y rota con Tancredi, y decide regresar a Roma. En un viaje a Sicilia para visitar a sus padres, descubrir?? un secreto familiar que le afectar?? profundamente. Mientras tanto, Tancredi sigue todos sus pasos; es un hombre enamorado que nunca se ha rendido a la primera. Pero Sof??a no conf??a en ??l??? ??Acabar??n reencontr??ndose?");
        cv.put("precio", 9.59);
        cv.put("autor", "Federico Moccia");
        cv.put("num_paginas", 464);
        cv.put("fecha_publicacion", "2022-02-09");
        cv.put("cantidad", 5);
        cv.put("estado", 1);
        db.insert("libro", null, cv);

        ContentValues cv1 = new ContentValues();
        cv1.put("_id", 2);
        cv1.put("titulo", "Los que sobran");
        cv1.put("imagen", Imagen.serializar(this.getResources().getDrawable(R.drawable.losquesobran)));
        cv1.put("descripcion", "Un an??lisis profundo, sobre el estado actual de la sociedad humana. Un libro que nos incita a cambiar lo que ahora tenemos, y, desde nosotros mismos, empezar a realizar la verdadera revoluci??n pac??fica. Recomendado del librero. Qu?? ins??lita paradoja define nuestra ??poca, las primeras generaciones globales, las m??s educadas de la historia, no logran hallar el camino para realizarse. ??Qu?? se los impide? ??Qu?? poderosas fuerzas tienen el poder de cerrarle el camino a las mayor??as y de decidir por ellas su destino? ??Por qu?? parecemos avanzar imparables hacia cada vez m??s grandes cat??strofes sin que podamos detener a quienes controlan el mundo? ??Qu?? se halla en la ra??z del profundo desasosiego y angustia que hoy nos invade en todos los rincones de nuestro fr??gil planeta?");
        cv1.put("precio", 10.59);
        cv1.put("autor", "Juan Carlos Fl??rez");
        cv1.put("num_paginas", 356);
        cv1.put("fecha_publicacion", "2021-05-24");
        cv1.put("cantidad", 15);
        cv1.put("estado", 1);
        db.insert("libro", null, cv1);

        ContentValues cv2 = new ContentValues();
        cv2.put("_id", 3);
        cv2.put("titulo", "La Invitada");
        cv2.put("imagen", Imagen.serializar(this.getResources().getDrawable(R.drawable.lainvitada)));
        cv2.put("descripcion", "Helen y Nate dejan atr??s la ciudad para mudarse al campo. Quieren construir la casa de sus sue??os en un terreno rural a las afueras de un bosque. Cuando descubren que su magn??fica propiedad tiene un pasado violento y oscuro, Helen, que era profesora de historia, quedar?? fascinada por la leyenda local de Hattie Breckenridge, que fue acusada de brujer??a hace m??s de cien a??os. Cuando se sumerge en la historia de Hattie y sus descendientes, descubrir?? que ese linaje llega hasta la actualidad. Conforme avance la construcci??n de la casa, un peligro inesperado acechar?? a sus due??os y al resto de habitantes del lugar.");
        cv2.put("precio", 19.8);
        cv2.put("autor", "Jennifer McMahon");
        cv2.put("num_paginas", 496);
        cv2.put("fecha_publicacion", "2021-08-16");
        cv2.put("cantidad", 2);
        cv2.put("estado", 1);
        db.insert("libro", null, cv2);

        ContentValues cv3 = new ContentValues();
        cv3.put("_id", 4);
        cv3.put("titulo", "Una Herencia En Juego");
        cv3.put("imagen", Imagen.serializar(this.getResources().getDrawable(R.drawable.unaherenciaenjuego)));
        cv3.put("descripcion", "Ad??ntrate en la historia de una cenicienta moderna, repleta de giros inesperados y misterios sin resolver. CUARENTA Y SEIS MIL DOSCIENTOS MILLONES DE D??LARES. ???Pens??. El coraz??n me retumbaba contra las costillas y ten??a la boca tan seca como el papel de lija. Tobias Hawthorne ten??a cuarenta y seis mil doscientos millones de d??lares. Tobias Hawthorne no se lo hab??a dejado todo a sus nietos. No se lo hab??a dejado todo a sus hijas. Los n??meros de esa ecuaci??n no sal??an. Pero ni de lejos. Y mi cerebro se par?? en seco. Me pitaban los o??dos. ??Por qu?? a m??? ??Por qu?? era yo la principal heredera de su fortuna? Uno por uno, todos los presentes se volvieron para mirarme. Atrapada en un mundo de riqueza y privilegios, con el peligro acechando a cada paso, Avery tendr?? que ir a por todas y jugar a ese juego... si quiere sobrevivir.");
        cv3.put("precio", 17.35);
        cv3.put("autor", "Jennifer Lynn Barnes");
        cv3.put("num_paginas", 448);
        cv3.put("fecha_publicacion", "2022-03-17");
        cv3.put("cantidad", 5);
        cv3.put("estado", 1);
        db.insert("libro", null, cv3);

        ContentValues cv4 = new ContentValues();
        cv4.put("_id", 5);
        cv4.put("titulo", "??A qu?? est??s esperando?");
        cv4.put("imagen", Imagen.serializar(this.getResources().getDrawable(R.drawable.aqueestasesperando)));
        cv4.put("descripcion", "Can Drogo, piloto e hijo del due??o de la empresa aeron??utica High Drogo, es un hombre alto, guapo, adinerado, simp??tico??? Puede elegir a la mujer que desee, y aunque disfruta de esa ??magia especial?? con la que le ha dotado la vida, en su interior siente que todas lo aburren. Por su parte, Sonia Becher es la mayor de cuatro hermanas y la propietaria de una empresa de eventos y de una agencia de modelos. Can ve en ella a una chica divertida, atrevida, sin tab??es, con la que se puede hablar de todo, incluido de sexo, pero poco m??s, pues considera que no es su tipo. Hasta que un d??a las sonrisas y las miradas de la joven no van dirigidas a ??l, y eso, sin saber por qu??, comienza a molestarlo. ??En serio Sonia va a sonre??r a otros hombres estando ??l delante? Sexo. Familia. Diversi??n. Locura. Todo esto es lo que vas a encontrar en ??A qu?? est??s esperando?, una novela que te har?? ver que, en ocasiones, tu coraz??n se desboca por quien menos esperas sin que puedas frenarlo.");
        cv4.put("precio", 28.88);
        cv4.put("autor", "Megan Maxwell");
        cv4.put("num_paginas", 672);
        cv4.put("fecha_publicacion", "2020-10-29");
        cv4.put("cantidad", 4);
        cv4.put("estado", 1);
        db.insert("libro", null, cv4);

        ContentValues cv5 = new ContentValues();
        cv5.put("_id", 6);
        cv5.put("titulo", "El Conde de Montecristo");
        cv5.put("imagen", Imagen.serializar(this.getResources().getDrawable(R.drawable.elcondedemontecristo)));
        cv5.put("descripcion", "El Conde de Montecristo es una de las novelas de aventuras m??s famosas de todos los tiempos. Escrita por el autor franc??s Alexandre Dumas (1802-1870) y publicada en 1844. El Conde de Montecristo fue un ??xito comercial al momento de su publicaci??n, gracias en parte a la acogida de otra novela reciente de Dumas, Los Tres Mosqueteros (1844). La novela narra la vida de Edmundo Dant??s desde que fue apresado injustamente en el castillo de If por un falso cargo de traici??n, hasta que regresa a??os despu??s, convertido en el Conde de Montecristo, para ejercer su venganza sobre aquellos que destruyeron su vida. La historia trata temas como la b??squeda de la justicia, la ceguera aristocr??tica, la ambici??n, el honor, los cambios de una ??poca tumultuosa en la historia de Francia, la naturaleza del odio y el peso de la maldad sobre el alma humana. Es una ventana a un momento turbio de manipulaciones sociales y pol??ticas, de intriga cortesana y de fascinaci??n por lo ex??tico y lo desconocido, y un inmortal ejemplo de lo que estar??a dispuesto a hacer un hombre por rehacer su vida.");
        cv5.put("precio", 19.23);
        cv5.put("autor", "Alejandro Dumas");
        cv5.put("num_paginas", 1188);
        cv5.put("fecha_publicacion", "2020-08-06");
        cv5.put("cantidad", 12);
        cv5.put("estado", 1);
        db.insert("libro", null, cv5);
    }
}