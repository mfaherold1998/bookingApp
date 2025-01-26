package com.example.booking.common;

import com.example.booking.entity.UserEntity;
import com.example.booking.exception.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
public abstract class BaseController<E extends BaseEntity, D extends BaseDto, S extends BaseService<E, D, ?, ?>> {

    protected final S service;

    @PostMapping(value = "/save")
    public ResponseEntity<D> save(@NotNull @Valid @RequestBody D dto) {

        if(checkCanSave(service.getAuthenticatedUser(), dto)){
            return ResponseEntity.ok(service.save(dto));
        }
        throw new CantSaveException();
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<D> getById(@NotNull @PathVariable Long id) {

        if(checkCanGet(service.getAuthenticatedUser(), id)){
            return ResponseEntity.ok(service.getDtoById(id));
        }
        throw new CantGetException();
    }

    @GetMapping(value = "/getall")
    public ResponseEntity<List<D>> getAll(){

        if(checkCanGetAll(service.getAuthenticatedUser())){
            return ResponseEntity.ok(service.getAllDto());
        }
        throw new CantGetAllException();

    }

    @PutMapping(value = "/update")
    public ResponseEntity<D> update(@Valid @NotNull @RequestBody D dto) {
        Long id = dto.getId();
        if(checkCanUpdate(service.getAuthenticatedUser(), dto, id)){
            return ResponseEntity.ok(service.update(dto));
        }
        throw new CantUpdateException();
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Boolean> delete(@NotNull @PathVariable Long id) {

        if(checkCanDelete(service.getAuthenticatedUser(), id)){
            return ResponseEntity.ok(service.delete(id));
        }
        throw new CantDeleteException();
    }
    protected Boolean checkCanGetAll(UserEntity currentUser){return Boolean.TRUE;}
    protected Boolean checkCanGet(UserEntity currentUser, Long id){return Boolean.TRUE;}
    protected Boolean checkCanSave(UserEntity currentUser, D toSave){return Boolean.TRUE;}
    protected Boolean checkCanUpdate(UserEntity currentUser, D toUpdate, Long id){return Boolean.TRUE;}
    protected Boolean checkCanDelete(UserEntity currentUser, Long id){return Boolean.TRUE;}

    public UserEntity getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserEntity) authentication.getPrincipal();
    }
}

