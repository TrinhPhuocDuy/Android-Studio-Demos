package com.example.lab10.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.lab10.dao.PersonDAO;
import com.example.lab10.model.Person;

@Database(entities = {Person.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PersonDAO personDAO();

}
