package com.floweytech.agrotrack.organization.interfaces.rest;

import com.floweytech.agrotrack.organization.domain.model.valueobject.PlantTypeId;
import com.floweytech.agrotrack.organization.domain.services.PlantTypeCommandService;
import com.floweytech.agrotrack.organization.domain.services.PlantTypeQueryService;
import com.floweytech.agrotrack.organization.interfaces.rest.resources.CreatePlantTypeResource;
import com.floweytech.agrotrack.organization.interfaces.rest.resources.PlantTypeResource;
import com.floweytech.agrotrack.organization.interfaces.rest.transform.CreatePlantTypeCommandFromResourceAssembler;
import com.floweytech.agrotrack.organization.interfaces.rest.transform.PlantTypeResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/plant-types", produces = "application/json")
@Tag(name = "Plant Types", description = "Plant Type Management Endpoints")
public class PlantTypeController {

    private final PlantTypeCommandService plantTypeCommandService;
    private final PlantTypeQueryService plantTypeQueryService;

    public PlantTypeController(PlantTypeCommandService plantTypeCommandService,
                               PlantTypeQueryService plantTypeQueryService) {
        this.plantTypeCommandService = plantTypeCommandService;
        this.plantTypeQueryService = plantTypeQueryService;
    }

    @PostMapping
    public ResponseEntity<PlantTypeResource> createPlantType(@Valid @RequestBody CreatePlantTypeResource resource,
                                                              HttpServletRequest request) {
        var command = CreatePlantTypeCommandFromResourceAssembler.toCommandFromResource(resource);
        var plantTypeId = plantTypeCommandService.handle(command, request);

        var plantType = plantTypeQueryService.getByPlantTypeId(new PlantTypeId(plantTypeId));

        return plantType.map(pt -> ResponseEntity.status(HttpStatus.CREATED)
                .body(PlantTypeResourceFromEntityAssembler.toResourceFromEntity(pt)))
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/{plantTypeId}")
    public ResponseEntity<PlantTypeResource> getPlantTypeById(@PathVariable Long plantTypeId) {
        var plantType = plantTypeQueryService.getByPlantTypeId(new PlantTypeId(plantTypeId));

        return plantType.map(pt -> ResponseEntity.ok(PlantTypeResourceFromEntityAssembler.toResourceFromEntity(pt)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-name/{name}")
    public ResponseEntity<List<PlantTypeResource>> searchPlantTypesByName(@PathVariable String name) {
        var plantTypes = plantTypeQueryService.searchByName(name);
        var resources = plantTypes.stream()
                .map(PlantTypeResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    @GetMapping
    public ResponseEntity<List<PlantTypeResource>> getAllPlantTypes() {
        var plantTypes = plantTypeQueryService.getAll();
        var resources = plantTypes.stream()
                .map(PlantTypeResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    @GetMapping("/predefined")
    public ResponseEntity<List<PlantTypeResource>> getAllPredefinedPlantTypes() {
        var plantTypes = plantTypeQueryService.getAllPredefined();
        var resources = plantTypes.stream()
                .map(PlantTypeResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }
}
