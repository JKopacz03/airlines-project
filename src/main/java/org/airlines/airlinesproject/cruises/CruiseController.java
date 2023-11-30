package org.airlines.airlinesproject.cruises;

import lombok.RequiredArgsConstructor;
import org.airlines.airlinesproject.cruises.dto.CruiseRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1")
@RequiredArgsConstructor
public class CruiseController {

    private final CruiseService cruiseService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/create-cruise")
    public void createCruise(@RequestBody CruiseRequest cruiseRequest){
        cruiseService.createCruise(cruiseRequest);
    }
}
