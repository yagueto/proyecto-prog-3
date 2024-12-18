package db;

import java.util.List;

public interface Dao<T> {
    T get(Object param);

    List<T> getAll();

    void save(T t);

    void update(T t);

    void delete(T t);

}
