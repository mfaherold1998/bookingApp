package com.example.booking.controller;

import com.example.booking.common.BaseController;
import com.example.booking.dto.CorporationDto;
import com.example.booking.entity.Corporation;
import com.example.booking.entity.Proprietor;
import com.example.booking.entity.UserEntity;
import com.example.booking.exception.NotFoundException;
import com.example.booking.service.CorporationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/corporations")

@Tag(name = "Corporations", description = "API for managing corporations information.")
public class CorporationController extends BaseController<Corporation, CorporationDto, CorporationService> {

    public CorporationController(CorporationService service) {
        super(service);
    }

    @Operation(
            summary = "Fetch all Corporations",
            description = "Retrieves a list of all corporations registered in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation")
    })
    @Override
    @PreAuthorize("hasAuthority('SUPERADMIN')")
    public ResponseEntity<List<CorporationDto>> getAll() {
        return super.getAll();
    }

    @Operation(
            summary = "Fetch a Corporation by Id",
            description = "Retrieves the details of a specific corporation using its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation")
    })
    @Override
    @PreAuthorize("hasAnyAuthority('CLIENT', 'EMPLOYEE', 'PROPRIETOR','SUPERADMIN')")
    public ResponseEntity<CorporationDto> getById(@PathVariable Long id) {
        return super.getById(id);
    }

    @Operation(
            summary = "Create a new Corporation",
            description = "Creates a new corporation in the system with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Operation"),
            @ApiResponse(responseCode = "500", description = "Bad Credentials")
    })
    @Override
    @PreAuthorize("hasAnyAuthority('PROPRIETOR','SUPERADMIN')")
    public ResponseEntity<CorporationDto> save(@NotNull @Valid @RequestBody CorporationDto dto) {
        return super.save(dto);
    }

    @Operation(
            summary = "Delete a Corporation",
            description = "Marks a specific appointment as deleted without permanently removing it from the system. The booking is flagged as inactive but remains in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successful Operation"),
            @ApiResponse(responseCode = "404", description = "Non-existent Corporation")
    })
    @Override
    @PreAuthorize("hasAnyAuthority('PROPRIETOR','SUPERADMIN')")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @Operation(
            summary = "Update a Corporation",
            description = "Updates the details of an existing corporation. Requires the unique ID of the corporation and the new data to be saved.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation"),
            @ApiResponse(responseCode = "404", description = "Non-existent Corporation")
    })
    @Override
    @PreAuthorize("hasAnyAuthority('PROPRIETOR','SUPERADMIN')")
    public ResponseEntity<CorporationDto> update(@NotNull @Valid @RequestBody CorporationDto dto) {
        return super.update(dto);
    }

    @Override
    protected Boolean checkCanDelete(UserEntity currentUser, Long id) {

        ///Obtiene la corporation a eliminar con el ID dado
        Corporation corporation = service.getRepository().findById(id).orElseThrow(NotFoundException::new);

        ///Comprueba que el usuario autenticado tiene un id de Proprietario
        if (currentUser.getProprietor() == null) {
            return false;
        }

        ///Se obtiene el id de propietario del usuario
        Long currentProprietorId = currentUser.getProprietor().getId();

        ///Se retorna verdadero o falso en caso el usuario sea proprietario de la corporacion
        return corporation.getProprietors().stream()
                .map(Proprietor::getId)
                .anyMatch(proprietorId -> proprietorId.equals(currentProprietorId));

    }


}
