package org.example.otomotoclon.controller;

import lombok.RequiredArgsConstructor;
import org.example.otomotoclon.dto.SubscribedCarDTO;
import org.example.otomotoclon.dto.SubscribedCarDTOExtended;
import org.example.otomotoclon.entity.Response;
import org.example.otomotoclon.exception.ObjectDontExistInDBException;
import org.example.otomotoclon.exception.ObjectExistInDBException;
import org.example.otomotoclon.serivce.SubscribedCarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/subscribe-car")
@RequiredArgsConstructor
public class SubscribedCarController {

    private final SubscribedCarService subscribedCarService;

    @PostMapping
    public ResponseEntity<Response> subscribeCar(@RequestBody SubscribedCarDTO subscribedCarDTO)  {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            subscribedCarService.subscribeCar(username, subscribedCarDTO);
        } catch (ObjectDontExistInDBException e) {
            return ResponseEntity.status(400).body(new Response(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok(new Response("Subscribed!", HttpStatus.OK.value()));
    }

    @DeleteMapping("/delete/{subscriptionId}")
    public ResponseEntity<Response> unsubscribeCar(@PathVariable long subscriptionId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            subscribedCarService.unsubscribeCar(subscriptionId, username);
        } catch (ObjectDontExistInDBException | AuthenticationException e) {
            return ResponseEntity.status(400).body(new Response(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok(new Response("Unsubscribed", HttpStatus.OK.value()));
    }

    @GetMapping
    public ResponseEntity<List<SubscribedCarDTOExtended>> getSubscriptionsByLoggedInUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<SubscribedCarDTOExtended> subscriptions = subscribedCarService.getSubscriptionsByUsername(
                username
        );
        return ResponseEntity.ok(subscriptions);
    }
}
