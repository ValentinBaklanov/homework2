package dao;


import java.util.Optional;
import java.util.Set;

public interface Dao<K, E> {

    E create(E object);

    Optional<E> update(E object);

    boolean delete(K id);

    Optional<E> findById(K id);

    Set<E> findAll();

}
