package com.floweytech.agrotrack.organization.interfaces.rest;

import com.floweytech.agrotrack.organization.domain.model.valueobject.OrganizationId;
import com.floweytech.agrotrack.organization.domain.services.PlotCommandService;
import com.floweytech.agrotrack.organization.domain.services.PlotQueryService;
import com.floweytech.agrotrack.organization.interfaces.rest.resources.CreatePlotResource;
import com.floweytech.agrotrack.organization.interfaces.rest.resources.PlotResource;
import com.floweytech.agrotrack.organization.interfaces.rest.resources.ReassignPlantTypeResource;
import com.floweytech.agrotrack.organization.interfaces.rest.resources.ReassignSizeAreaResource;
import com.floweytech.agrotrack.organization.interfaces.rest.transform.CreatePlotCommandFromResourceAssembler;
import com.floweytech.agrotrack.organization.interfaces.rest.transform.PlotResourceFromEntityAssembler;
import com.floweytech.agrotrack.organization.interfaces.rest.transform.ReassignPlantTypeCommandFromResourceAssembler;
import com.floweytech.agrotrack.organization.interfaces.rest.transform.ReassignSizeAreaCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/plots", produces = "application/json")
@Tag(name = "Plots", description = "Plot Management Endpoints")
public class PlotController {

    private final PlotCommandService plotCommandService;
    private final PlotQueryService plotQueryService;

    public PlotController(PlotCommandService plotCommandService,
                          PlotQueryService plotQueryService) {
        this.plotCommandService = plotCommandService;
        this.plotQueryService = plotQueryService;
    }

    @PostMapping
    public ResponseEntity<PlotResource> createPlot(@Valid @RequestBody CreatePlotResource resource,
                                                    HttpServletRequest request) {
        var command = CreatePlotCommandFromResourceAssembler.toCommandFromResource(resource);
        var plotId = plotCommandService.handle(command, request);

        var plot = plotQueryService.getById(plotId);

        return plot.map(p -> ResponseEntity.status(HttpStatus.CREATED)
                .body(PlotResourceFromEntityAssembler.toResourceFromEntity(p)))
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping
    public ResponseEntity<List<PlotResource>> getAllPlots() {
        var plots = plotQueryService.getAll();
        var resources = plots.stream()
                .map(PlotResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{plotId}")
    public ResponseEntity<PlotResource> getPlotById(@PathVariable Long plotId) {
        var plot = plotQueryService.getById(plotId);

        return plot.map(p -> ResponseEntity.ok(PlotResourceFromEntityAssembler.toResourceFromEntity(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<PlotResource>> getPlotsByOrganizationId(@PathVariable Long organizationId) {
        var plots = plotQueryService.getByOrganizationId(new OrganizationId(organizationId));
        var resources = plots.stream()
                .map(PlotResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    @GetMapping("/by-name/{plotName}")
    public ResponseEntity<List<PlotResource>> getPlotsByName(@PathVariable String plotName) {
        var plots = plotQueryService.getByPlotName(plotName);
        var resources = plots.stream()
                .map(PlotResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    @PutMapping("/{plotId}/plant-type")
    public ResponseEntity<PlotResource> reassignPlantType(
            @PathVariable Long plotId,
            @Valid @RequestBody ReassignPlantTypeResource resource,
            HttpServletRequest request) {

        var command = ReassignPlantTypeCommandFromResourceAssembler.toCommandFromResource(plotId, resource);
        plotCommandService.handle(command, request);

        var plot = plotQueryService.getById(plotId);
        return plot.map(p -> ResponseEntity.ok(PlotResourceFromEntityAssembler.toResourceFromEntity(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{plotId}/size-area")
    public ResponseEntity<PlotResource> reassignSizeArea(
            @PathVariable Long plotId,
            @Valid @RequestBody ReassignSizeAreaResource resource,
            HttpServletRequest request) {

        var command = ReassignSizeAreaCommandFromResourceAssembler.toCommandFromResource(plotId, resource);
        plotCommandService.handle(command, request);

        var plot = plotQueryService.getById(plotId);
        return plot.map(p -> ResponseEntity.ok(PlotResourceFromEntityAssembler.toResourceFromEntity(p)))
                .orElse(ResponseEntity.notFound().build());
    }
}

