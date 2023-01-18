package com.example.bookish_bookshop_app.Module_1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.bookish_bookshop_app.utils.MyOpenHelper;
import com.example.bookish_bookshop_app.utils.Tablas;

import java.util.ArrayList;
import java.util.List;

public class Data_Module_1 {
    // Global Variables
    private Context context;
    private MyOpenHelper db;

    public Data_Module_1(Context context, MyOpenHelper db) {
        this.context = context;
        this.db = db;
    }

    // region Default data for database  -----------------------------------------------
    public void insertDataCredential() {
        /**
         * Insert default credential data into database
         */
        Model_Credential[] credentials = {
                new Model_Credential(1, "Helen", "1234"),
                new Model_Credential(2, "Nefi", "1234"),
                new Model_Credential(3, "Renan", "1234"),
                new Model_Credential(4, "Vane", "1234"),
        };

        for (Model_Credential element : credentials) {
            ContentValues values = new ContentValues();
            values.put("username", element.username);
            values.put("password", element.password);
            db.getWritableDatabase().insert(Tablas.CREDENTIAL, null, values);
        }

    }

    public void insertDataUser() {
        /**
         * Insert default user data into database
         */
        Model_User[] users = {
                new Model_User(1, "Helen", "Bernal", "0983 775 380", "helen.bernalv@ug.edu.ec", "Femenino", "Estudiante", 1),
                new Model_User(2, "Nefi", "Reyes", "0958 140 795", "nefi.reyest@ug.edu.ec", "Masculino", "Estudiante", 2),
                new Model_User(3, "Renán", "Pérez", "0967 143 109", "rehan.perezb@ug.edu.ec", "Masculino", "Estudiante", 3),
                new Model_User(4, "Vanessa", "Ronquillo", "0978 588 276", "vanessa.ronquillos@ug.edu.ec", "Femenino", "Estudiante", 4)
        };

        for (Model_User element: users) {
            ContentValues values = new ContentValues();
            values.put("name",element.name);
            values.put("lastname",element.lastname);
            values.put("phone",element.phone);
            values.put("email",element.email);
            values.put("genre",element.genre);
            values.put("occupation",element.occupation);
            values.put("id_credential",element.id_credential);
            db.getWritableDatabase().insert(Tablas.USER, null, values);
        }

    }
    // endregion

    // region User CRUD -----------------------------------------------
    public Boolean createDataUser(String name, String lastname, String phone, String email, String genre, String occupation, String username, String password) {
        // Variables
        boolean dataSavedUser = false;
        boolean dataSavedCredential = false;
        int id_credential = 0;

        dataSavedCredential = createDataCredentials (username, password);

        if (dataSavedCredential) {
            // find id_credential
            for (Model_Credential element: readDataCredentials()) {
                if (username.equals(element.username)){
                    id_credential = element.id;
                    break;
                }
            }

            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("lastname", lastname);
            values.put("phone", phone);
            values.put("email", email);
            values.put("genre", genre);
            values.put("occupation", occupation);
            values.put("id_credential", id_credential);
            db.getWritableDatabase().insert(Tablas.USER, null, values);
        }

        return dataSavedUser;
    }

    public void readDataUser() {

    }

    public void UpdateDataUser() {

    }

    public void deleteDataUser() {

    }
    // endregion


    // region Credential CRUD -----------------------------------------------
    public boolean createDataCredentials (String username, String password) {
        // Variables
        boolean dataSaved = false;

        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        db.getWritableDatabase().insert(Tablas.CREDENTIAL, null, values);

        return dataSaved;
    }

    public List<Model_Credential> readDataCredentials() {
        // Variables
        List<Model_Credential> credentials = new ArrayList<>();

        Cursor data = db.getReadableDatabase().rawQuery("SELECT c.id, c.username, c.password FROM Credential c", null);
        while (data != null && data.moveToNext()) {
            Model_Credential getCredentials = new Model_Credential(data.getInt(0), data.getString(1), data.getString(2));
            credentials.add(getCredentials);
        }

        return credentials;
    }

    public void updateDataCredentials() {

    }

    public void deleteDataCredentials() {

    }
    // endregion






}