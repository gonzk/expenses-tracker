package persistence;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Expense;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// A read class that can read a list of expense data from a json file
public class Read {

    // EFFECTS: returns a list of expenses parsed from json file;
    public static List<Expense> readExpenses(String file) throws IOException {
        //source: https://java2blog.com/gson-example-read-and-write-json/
        Gson gson = new Gson();

        BufferedReader reader = new BufferedReader(new FileReader(file));

        return new ArrayList<>(new Gson().fromJson(reader, new TypeToken<List<Expense>>() {}.getType()));

    }
}
