package com.example.booking.common;


import com.example.booking.entity.UserEntity;
import com.example.booking.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
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
        return repository.findById(id).orElseThrow(NotFoundException::new);
    }

    public D getDtoById(Long id) {
        E entity = getEntityById(id);
        if(entity.deleted){
            throw new NotFoundException();
        }
        return mapper.toDto(entity);
    }

    public E save(E entity) {
        return repository.saveAndFlush(entity);
    }

    public D save(D dto) {
        return mapper.toDto(repository.saveAndFlush(mapper.toEntity(dto)));
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public List<D> getAllDto() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    public List<E> getAllEntity() { return repository.findAll(); }

    public E update(E entity) {
        entity.setUpdatedDate(LocalDateTime.now());
        return repository.save(entity);
    }

    public D update(D dto) {
        if (repository.existsById(dto.getId())) {
            return mapper.toDto(repository.saveAndFlush(mapper.toEntity(dto)));
        } else {
            throw new NotFoundException();
        }
    }

    public Boolean delete(Long id) {
        if (repository.existsById(id)) {
            E entity = getEntityById(id);
            entity.setDeleted(Boolean.TRUE);
            repository.saveAndFlush(entity);
            return Boolean.TRUE;
        } else {
            throw new NotFoundException();
        }
    }

    public UserEntity getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserEntity) authentication.getPrincipal();
    }

}
