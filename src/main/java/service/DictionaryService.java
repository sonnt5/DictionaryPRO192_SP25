package service;

import dal.FileDataSource;
import diutil.AfterCreation;
import diutil.Component;
import diutil.Injected;
import model.IndexEntry;
import dal.FileEntryFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(type = Component.ComponentType.SERVICE)
public class DictionaryService {
    private static DictionaryService instance;
    private Map<String, IndexEntry> dictionary;

    @Injected
    private FileDataSource dataSource;

    @Injected
    private FileEntryFactory factory;

    @AfterCreation
    private void init()
    {
        this.dictionary = new HashMap<>();
    }

    public void loadDictionary() {
        try {
            List<IndexEntry> entries = dataSource.readIndex();
            for (IndexEntry entry : entries) {
                dictionary.put(entry.getWord(), entry);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String lookup(String word) {
        IndexEntry entry = dictionary.get(word);
        if (entry == null) {
            return "Word not found!";
        }
        try {
            return dataSource.readMeaning(entry.getBegin(), entry.getLen());
        } catch (IOException e) {
            e.printStackTrace();
            return "Error reading meaning!";
        }
    }

    public void addWord(String word, String meaning) {
        try {
            if (dictionary.containsKey(word)) {
                System.out.println("Word '" + word + "' already exists!");
                return;
            }
            long begin = new File(dataSource.getMeanFilePath()).length(); // Tính trước khi ghi
            dataSource.addWord(word, meaning);
            int len = meaning.getBytes().length;
            IndexEntry newEntry = new IndexEntry(word, begin, len);
            dictionary.put(word, newEntry);
            System.out.println("Added word '" + word + "' successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error adding word '" + word + "'!");
        }
    }


}