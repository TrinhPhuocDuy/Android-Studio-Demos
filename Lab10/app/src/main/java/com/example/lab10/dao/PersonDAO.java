package com.example.lab10.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.lab10.model.Person;

import java.util.List;

@Dao
public interface PersonDAO {

    @Query("SELECT * FROM person")
    List<Person> getAll();

    @Query("SELECT * FROM person WHERE uid IN (:personId)")
    Person loadPersonById(int personId);

    @Query("SELECT * FROM person WHERE first_name LIKE :first AND " + "last_name LIKE :last LIMIT 1")
    Person findByName(String first, String last);

    @Insert
    void insert(Person person);

    @Update
    void update(Person person);

    @Delete
    void delete(Person person);

}
