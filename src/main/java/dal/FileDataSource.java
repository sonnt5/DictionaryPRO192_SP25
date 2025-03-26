package dal;

import diutil.AfterCreation;
import diutil.Component;
import diutil.Injected;
import model.IndexEntry;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component(type = Component.ComponentType.REPOSITORY)
public class FileDataSource {
    private String indexFilePath;
    private String meanFilePath;

    @Injected
    private FileEntryFactory factory;

    @AfterCreation
    private void init(){
        // Lấy đường dẫn từ resources
        URL indexUrl = FileDataSource.class.getClassLoader().getResource("index.dat");
        URL meanUrl = FileDataSource.class.getClassLoader().getResource("mean.dat");

        if (indexUrl == null) {
            throw new RuntimeException("Cannot find index.dat in resources");
        }
        if (meanUrl == null) {
            throw new RuntimeException("Cannot find mean.dat in resources");
        }

        this.indexFilePath = indexUrl.getPath();
        this.meanFilePath = meanUrl.getPath();

        System.out.println("indexFilePath: " + indexFilePath);
        System.out.println("meanFilePath: " + meanFilePath);
    }

    public List<IndexEntry> readIndex() throws IOException {
        List<IndexEntry> entries = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(indexFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    entries.add(factory.createIndexEntry(line));
                }
            }
        }
        return entries;
    }

    public String readMeaning(long begin, int len) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(meanFilePath, "r")) {
            raf.seek(begin);
            byte[] bytes = new byte[len];
            raf.read(bytes, 0, len);
            return new String(bytes);
        }
    }

    public synchronized void addWord(String word, String meaning) throws IOException {
        long begin = -1;
        // Ghi nghĩa vào mean.dat
        try (RandomAccessFile meanFile = new RandomAccessFile(meanFilePath, "rw")) {
            begin = meanFile.length();
            meanFile.seek(begin);
            byte[] meaningBytes = meaning.getBytes();
            meanFile.write(meaningBytes);
        }

        // Ghi chỉ mục vào index.dat
        try (FileWriter indexWriter = new FileWriter(indexFilePath, true);
             BufferedWriter bw = new BufferedWriter(indexWriter)) {
            String indexLine = String.format("%s %d %d%n", word, begin, meaning.getBytes().length);
            bw.write(indexLine);
        }
    }

    // Getter để truy cập meanFilePath
    public String getMeanFilePath() {
        return meanFilePath;
    }
}