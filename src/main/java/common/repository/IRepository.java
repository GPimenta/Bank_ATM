package common.repository;

import common.model.IdentificationItem;

import java.util.Collection;
import java.util.Optional;

public interface IRepository<T extends IdentificationItem> {
    Optional<T> create(T newItem);

    boolean deleteById(Integer id);

    Optional<T> update(T newItem);

    Optional<T> getById(Integer id);

    Collection<T> getAll();
}
