package persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Expense;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

// A write class that can write expense data to a json file
public class Write {

    // MODIFIES: this
    // EFFECTS: writes list of expenses to file
    public void write(List<Expense> expenses, String file) throws IOException {
        //source: https://tinyurl.com/yya94oyv
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(expenses);

        FileWriter writer = new FileWriter(file);
        writer.write(json);
        writer.close();
    }

}
