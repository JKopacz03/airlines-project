package org.airlines.airlinesproject.cruises;

import lombok.RequiredArgsConstructor;
import org.airlines.airlinesproject.cruises.dto.CruiseRequest;
import org.airlines.airlinesproject.cruises.dto.CruiseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1")
@RequiredArgsConstructor
public class CruiseController {

    private final CruiseService cruiseService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/create-cruise")
    @PreAuthorize("hasRole('ADMIN')")
    public void createCruise(@RequestBody CruiseRequest cruiseRequest){
        cruiseService.createCruise(cruiseRequest);
    }

    @GetMapping(path = "/find-all-cruises-for-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public List<CruiseResponse> findAllCruisesForAdmin(){
        return cruiseService.findAllForAdmin();
    }

    @GetMapping(path = "/find-all-cruises")
    public List<CruiseResponse> findAllCruises(){
        return cruiseService.findAll();
    }
}
