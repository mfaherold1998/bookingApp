package com.example.booking.common;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Transactional
public abstract class BaseService<
        E extends BaseEntity,
        D extends BaseDto,
        M extends BaseMapper<D, E>,
        R extends BaseRepository<E>> {

    protected final R repository;
    protected final M mapper;

    public E getEntityById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Exception", "Object with id " + id + " does not exists"));
    }

    public D getDtoById(Long id) {
        return mapper.toDto(repository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Exception", "Object with id " + id + " does not exists")));
    }

    public E saveEntity(E entity) {
        return repository.saveAndFlush(entity);
    }

    public D saveDto(D dto) {
        return mapper.toDto(repository.saveAndFlush(mapper.toEntity(dto)));
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public List<D> getAllDto() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    public D update(D dto) {
        if (repository.existsById(dto.getId())) {
            return mapper.toDto(repository.saveAndFlush(mapper.toEntity(dto)));
        } else {
            throw new NotFoundException("Not Found Exception", "Object with id " + dto.getId() + " does not exists, you cannot update it");
        }
    }

    public Boolean delete(Long id) {
        if (repository.existsById(id)) {
            E entity = getEntityById(id);
            entity.setDeleted(Boolean.TRUE);
            repository.saveAndFlush(entity);
            return Boolean.TRUE;
        } else {
            throw new NotFoundException("Not Found Exception", "Object with id " + id + " does not exists, you cannot delete it");
        }
    }

}
