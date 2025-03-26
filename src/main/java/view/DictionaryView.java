package view;

import diutil.Component;

@Component(type = Component.ComponentType.VIEW)
public class DictionaryView {
    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void displayMenu() {
        System.out.println("\n=== Dictionary Menu ===");
        System.out.println("1. Lookup a word");
        System.out.println("2. Add a new word");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
    }
}
