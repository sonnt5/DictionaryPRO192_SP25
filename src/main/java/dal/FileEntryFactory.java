package dal;

import diutil.Component;
import model.IndexEntry;
import model.Meaning;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component(type = Component.ComponentType.SUB)
public class FileEntryFactory {
    private static final Pattern INDEX_PATTERN = Pattern.compile("^(\\S+)\\s+(\\d+)\\s+(\\d+)$");
    public IndexEntry createIndexEntry(String line) {
        Matcher matcher = INDEX_PATTERN.matcher(line.trim());
        if (matcher.matches()) {
            String word = matcher.group(1);        // Nhóm 1: word
            long begin = Long.parseLong(matcher.group(2)); // Nhóm 2: begin
            int len = Integer.parseInt(matcher.group(3));  // Nhóm 3: len
            return new IndexEntry(word, begin, len);
        } else {
            throw new IllegalArgumentException("Invalid index line format: " + line);
        }
    }

    public Meaning createMeaning(String content) {
        return new Meaning(content);
    }


}
