package com.edem.medlink.controller;

import com.edem.medlink.dto.AppointmentResponse;
import com.edem.medlink.dto.GenericResponseMessage;
import com.edem.medlink.service.appointment.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping ("/api/v1/appointment")
@RestController
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    @Operation(summary = "Book appointment", method = "POST")
    @PostMapping("/book/{availabilityId}")
    public AppointmentResponse createAppointment(Authentication authentication, @PathVariable UUID availabilityId){
        return appointmentService.createAppointment(authentication,availabilityId);
    }

    @Operation(summary = "Get appointments", method = "GET")
    @GetMapping("/my-appointments")
    public Page<AppointmentResponse> myAppointments(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size,
                                                    Authentication authentication){
        return appointmentService.getUserAppointments(authentication, page, size);

    }

    @PatchMapping("/{appointmentId}")
    public ResponseEntity<GenericResponseMessage> confirmAppointment(Authentication authentication, @PathVariable UUID appointmentId){
        return ResponseEntity.ok(appointmentService.confirmAppointment(authentication,appointmentId));

    }


}
