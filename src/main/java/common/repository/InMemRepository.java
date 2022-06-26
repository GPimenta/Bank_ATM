package common.repository;

import common.model.IdentificationItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public abstract class InMemRepository <T extends IdentificationItem> implements IRepository<T>{
    private final ArrayList<T> repository = new ArrayList<T>();

    @Override
    public Optional<T> create(T newItem){
        if(newItem == null){
            throw new IllegalArgumentException("New Item can not be null");
        }
        repository.add(newItem);
        return Optional.of(newItem);
    }

    @Override
    public boolean deleteById(Integer id){
        return repository.removeIf(item -> item.getId().equals(id));
    }

    @Override
    public Optional<T> update(T newItem){
        if (deleteById(newItem.getId())){
            repository.add(newItem);
            return Optional.of(newItem);
        }
        return Optional.empty();
    }
    @Override
    public Optional<T> getById(Integer id){
        for (T t: repository){
            if (t.getId().equals(id)){
                return Optional.of(t);
            }
        }
        return Optional.empty();
    }

    @Override
    public Collection<T> getAll(){
        return new ArrayList<T>(repository);
    }


}
