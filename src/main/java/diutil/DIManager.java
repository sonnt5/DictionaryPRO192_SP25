package diutil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DIManager {
    private final Map<Class<?>, Object> instances = new HashMap<>();

    public DIManager(String... packageNames) {
        try {
            scanAndInstantiate(packageNames); // Bước 1: Tạo instance
            injectDependencies();             // Bước 2: Tiêm phụ thuộc
            invokeAfterCreation();            // Bước 3: Gọi @AfterCreation
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize DI Manager", e);
        }
    }

    private void scanAndInstantiate(String[] packageNames) throws Exception {
        for (String packageName : packageNames) {
            Set<Class<?>> classes = ClassScanner.getClasses(packageName);
            for (Class<?> clazz : classes) {
                if (clazz.isAnnotationPresent(Component.class)) {
                    Object instance = clazz.getDeclaredConstructor().newInstance();
                    instances.put(clazz, instance);
                    System.out.println("Created instance: " + clazz.getName());
                }
            }
        }
    }

    private void injectDependencies() throws Exception {
        for (Class<?> clazz : instances.keySet()) {
            Object instance = instances.get(clazz);
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Injected.class)) {
                    field.setAccessible(true);
                    Class<?> fieldType = field.getType();
                    boolean injected = false;
                    // Duyệt qua tất cả instances để tìm type khớp
                    for (Class<?> injection : instances.keySet()) {
                        if (fieldType.isAssignableFrom(injection)) {
                            Object dependency = instances.get(injection);
                            field.set(instance, dependency);
                            System.out.println("Injected " + injection.getName() + " into " + clazz.getName());
                            injected = true;
                            break; // Thoát vòng lặp khi tìm thấy và tiêm
                        }
                    }
                    if (!injected) {
                        throw new RuntimeException("No matching dependency found for " + fieldType.getName() + " in " + clazz.getName());
                    }
                }
            }
        }
    }

    private void invokeAfterCreation() throws Exception {
        for (Object instance : instances.values()) {
            Class<?> clazz = instance.getClass();
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(AfterCreation.class)) {
                    method.setAccessible(true);
                    method.invoke(instance);
                    System.out.println("Invoked @AfterCreation on " + clazz.getName() + "." + method.getName());
                }
            }
        }
    }

    public <T> T getInstance(Class<T> clazz) {
        return clazz.cast(instances.get(clazz));
    }
}