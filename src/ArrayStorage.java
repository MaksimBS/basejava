/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int size = 0;

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    void save(Resume resume) {
        storage[size] = resume;
        size++;
    }

    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        boolean find = false;
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid) || find) {
                find = true;
                storage[i] = storage[i + 1];
            }
        }
        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] copy = new Resume[size];
        System.arraycopy(storage, 0, copy, 0, size);
        return copy;
    }

    int size() {
        return size;
    }
}
