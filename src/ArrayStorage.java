/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] allResume = new Resume[10000];
    private int size = 0;

    void clear() {
        for (int i = 0; i < size; i++) {
            allResume[i] = null;
        }
        size = 0;
    }

    void save(Resume resume) {
        allResume[size] = resume;
        size++;
    }

    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (allResume[i].uuid.equals(uuid)) {
                return allResume[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        boolean find = false;
        for (int i = 0; i < size; i++) {
            if (allResume[i].uuid.equals(uuid) || find) {
                find = true;
                allResume[i] = allResume[i + 1];
            }
        }
        if (find) {
            size--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] copy = new Resume[size];
        System.arraycopy(allResume, 0, copy, 0, size);
        return copy;
    }

    int size() {
        return size;
    }
}
