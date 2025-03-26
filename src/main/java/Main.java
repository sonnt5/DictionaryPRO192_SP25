import controller.DictionaryController;
import dal.FileEntryFactory;
import service.DictionaryService;
import dal.FileDataSource;
import view.DictionaryView;
import diutil.*;

public class Main {
    public static void main(String[] args) {
        // Khởi tạo DIManager với các package chứa @Component
        DIManager diManager = new DIManager("controller", "view", "service", "dal", "model");

        // Lấy instance của DictionaryController và chạy
        DictionaryController controller = diManager.getInstance(DictionaryController.class);
        controller.run();
    }
}