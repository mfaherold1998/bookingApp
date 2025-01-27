package com.example.booking.service;

import com.example.booking.dto.InvitationRequest;
import com.example.booking.entity.Employee;
import com.example.booking.entity.Invitation;
import com.example.booking.entity.Subdivision;
import com.example.booking.entity.UserEntity;
import com.example.booking.exception.CustomException;
import com.example.booking.exception.NotFoundException;
import com.example.booking.repository.EmployeeRepository;
import com.example.booking.repository.InvitationRepository;
import com.example.booking.repository.SubdivisionRepository;
import com.example.booking.repository.UserRepository;
import com.example.booking.utils.Enums;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@Getter
@RequiredArgsConstructor
public class InvitationService {

    private final UserRepository userRepository;
    private final SubdivisionRepository subdivisionRepository;
    private final InvitationRepository invitationRepository;
    private final EmployeeRepository employeeRepository;
    private final EmailService emailService;
    private final RoleService roleService;

    public Boolean sendInvitation (InvitationRequest request){

        ///Verificar si los datos son correctos
        UserEntity invitedUser = userRepository.findByEmail(request.getInvitedUserEmail()).orElseThrow(() -> new CustomException("There is no user registered with this email", HttpStatus.BAD_REQUEST));
        Subdivision subdivision = subdivisionRepository.findById(request.getSubdivision()).orElseThrow(() -> new CustomException("There is no subdivision with this ID", HttpStatus.BAD_REQUEST));

        ///Generar token unico para la invitacion
        String invitationToken = UUID.randomUUID().toString();

        ///Crear la invitacion
        Invitation invitation = Invitation.builder()
                .subdivision(subdivision)
                .user(invitedUser)
                .token(invitationToken)
                .status(Enums.InvitationStatus.PENDING.getValue())
                .expirationDate(LocalDateTime.now().plusDays(7))
                .build();

        ///Salvar invitacion en la BD
        invitation = invitationRepository.saveAndFlush(invitation);

        ///Enviar email de invitacion
        String subject = String.format("%s are inviting you to work with them.", invitation.getSubdivision().getCorporation().getName());

        String text = String.format(
                "Dear user,\n\n" +
                        "We are thrilled to invite you to join our team at %s in %s. Your skills and experience align perfectly with our vision, and we believe you would make a valuable addition to our team.\n\n" +
                        "To begin the onboarding process, please click on the invitation link below:\n" +
                        "https://localhost:8080/api/invitations/accept/%s\n\n" +
                        "If you have any questions or need further assistance, don’t hesitate to reach out. We look forward to welcoming you!\n\n" +
                        "Best regards,\n" +
                        "Nanda\n" +
                        "Developer\n" +
                        "Assistance",
                invitation.getSubdivision().getCorporation().getName(),
                invitation.getSubdivision().getName(),
                invitation.getToken()
        );

        EmailService.EmailContainer email = EmailService.EmailContainer.builder()
                .to(invitation.getUser().getEmail())
                .subject(subject)
                .text(text)
                .build();

        emailService.sendNewEmail(email);

        return Boolean.TRUE;
    }

    public Boolean acceptInvitation(String token) {

        ///Comprobar que es una invitacion valida por estado y por tiempo
        Invitation invitation = invitationRepository.findByToken(token).orElseThrow(NotFoundException::new);
        if(!Objects.equals(invitation.getStatus(), Enums.InvitationStatus.PENDING.getValue()) ||
                invitation.getExpirationDate().isBefore(LocalDateTime.now()) ) {
            throw new CustomException("This invitation is not valid anymore", HttpStatus.BAD_REQUEST);
        }

        ///Change Status to Accepted and save
        invitation.setStatus(Enums.InvitationStatus.ACCEPTED.getValue());
        invitationRepository.save(invitation);

        ///Comprobar si ya esta registrado
        Employee employee = invitation.getUser().getEmployee();
        if (employee != null){return Boolean.TRUE;}

        ///Save a new employee associated with the subdivision
        Employee newEmployee = Employee.builder()
                .firstName(invitation.getUser().getClient().getFirstName())
                .lastName(invitation.getUser().getClient().getLastName())
                .email(invitation.getUser().getEmail())
                .subdivision(invitation.getSubdivision())
                .build();
        employeeRepository.save(newEmployee);

        ///Conectar employee con la tabla UserEntity y guardar en la BD
        UserEntity newUser = invitation.getUser();
        newUser.setEmployee(newEmployee);
        newUser.getRoles().add((roleService.findByName(Enums.RoleNames.EMPLOYEE.getValue())));
        ///Ahora este user tiene instancias en cliente y empleado
        userRepository.save(newUser);

        //TODO enviar notificacion al que envio la invitacion de que fue aceptada



        return Boolean.TRUE;
    }

    public Boolean rejectInvitation(String token){

        ///Comprobar que es una invitacion valida por estado y fecha
        Invitation invitation = invitationRepository.findByToken(token).orElseThrow(NotFoundException::new);
        if(!Objects.equals(invitation.getStatus(), Enums.InvitationStatus.PENDING.getValue()) ||
                invitation.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new CustomException("This invitation is not valid anymore", HttpStatus.BAD_REQUEST);
        }

        ///Cambiar estado de la invitacion
        invitation.setStatus(Enums.InvitationStatus.REJECTED.getValue());
        invitationRepository.saveAndFlush(invitation);

        //TODO notificar que la notificacion fue rechazada

        return Boolean.TRUE;
    }

}