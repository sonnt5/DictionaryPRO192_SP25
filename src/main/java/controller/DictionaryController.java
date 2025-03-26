package controller;

import diutil.Component;
import diutil.Injected;
import service.DictionaryService;
import view.DictionaryView;

import java.util.Scanner;

@Component(type = Component.ComponentType.CONTROLLER)
public class DictionaryController {

    @Injected
    private DictionaryService service;

    @Injected
    private DictionaryView view;

    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        service.loadDictionary();
        while (running) {
            view.displayMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    handleLookup(scanner);
                    break;
                case "2":
                    handleAddWord(scanner);
                    break;
                case "3":
                    view.displayMessage("Goodbye!");
                    running = false;
                    break;
                default:
                    view.displayMessage("Invalid option! Please try again.");
            }
        }
        scanner.close();
    }

    private void handleLookup(Scanner scanner) {
        view.displayMessage("Enter word to lookup:");
        String word = scanner.nextLine().trim();
        String meaning = service.lookup(word);
        view.displayMessage("Meaning of '" + word + "': " + meaning);
    }

    private void handleAddWord(Scanner scanner) {
        view.displayMessage("Enter new word:");
        String word = scanner.nextLine().trim();
        view.displayMessage("Enter meaning:");
        String meaning = scanner.nextLine().trim();
        service.addWord(word, meaning);
    }
}
