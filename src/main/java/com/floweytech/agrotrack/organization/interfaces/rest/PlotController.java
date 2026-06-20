package com.floweytech.agrotrack.organization.interfaces.rest;

import com.floweytech.agrotrack.organization.domain.model.valueobject.OrganizationId;
import com.floweytech.agrotrack.organization.domain.services.PlotCommandService;
import com.floweytech.agrotrack.organization.domain.services.PlotQueryService;
import com.floweytech.agrotrack.organization.domain.services.OrganizationQueryService;
import com.floweytech.agrotrack.organization.interfaces.rest.resources.CreatePlotResource;
import com.floweytech.agrotrack.organization.interfaces.rest.resources.PlotResource;
import com.floweytech.agrotrack.organization.interfaces.rest.resources.ReassignPlantTypeResource;
import com.floweytech.agrotrack.organization.interfaces.rest.resources.ReassignSizeAreaResource;
import com.floweytech.agrotrack.organization.interfaces.rest.transform.CreatePlotCommandFromResourceAssembler;
import com.floweytech.agrotrack.organization.interfaces.rest.transform.PlotResourceFromEntityAssembler;
import com.floweytech.agrotrack.organization.interfaces.rest.transform.ReassignPlantTypeCommandFromResourceAssembler;
import com.floweytech.agrotrack.organization.interfaces.rest.transform.ReassignSizeAreaCommandFromResourceAssembler;
import com.floweytech.agrotrack.organization.shared.infrastructure.security.AuthenticatedUserProvider;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    private final OrganizationQueryService organizationQueryService;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public PlotController(PlotCommandService plotCommandService,
                          PlotQueryService plotQueryService,
                          OrganizationQueryService organizationQueryService,
                          AuthenticatedUserProvider authenticatedUserProvider) {
        this.plotCommandService = plotCommandService;
        this.plotQueryService = plotQueryService;
        this.organizationQueryService = organizationQueryService;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    @PostMapping
    public ResponseEntity<PlotResource> createPlot(@Valid @RequestBody CreatePlotResource resource) {
        var command = CreatePlotCommandFromResourceAssembler.toCommandFromResource(resource);
        var plotId = plotCommandService.handle(command);

        var plot = plotQueryService.getById(plotId);

        return plot.map(p -> ResponseEntity.status(HttpStatus.CREATED)
                .body(PlotResourceFromEntityAssembler.toResourceFromEntity(p)))
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping
    public ResponseEntity<List<PlotResource>> getAllPlots() {
        var plots = plotQueryService.getAll();
        var resources = plots.stream()
                .filter(this::canAccessPlot)
                .map(PlotResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{plotId}")
    public ResponseEntity<PlotResource> getPlotById(@PathVariable Long plotId) {
        var plot = plotQueryService.getById(plotId);

        if (plot.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (!canAccessPlot(plot.get())) {
            return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(PlotResourceFromEntityAssembler.toResourceFromEntity(plot.get()));
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<PlotResource>> getPlotsByOrganizationId(@PathVariable Long organizationId) {
        var organization = organizationQueryService.getByOrganizationId(new OrganizationId(organizationId));
        if (organization.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (!canAccessOrganization(organization.get())) {
            return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build();
        }
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
                .filter(this::canAccessPlot)
                .map(PlotResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    @PutMapping("/{plotId}/plant-type")
    public ResponseEntity<PlotResource> reassignPlantType(
            @PathVariable Long plotId,
            @Valid @RequestBody ReassignPlantTypeResource resource) {

        var command = ReassignPlantTypeCommandFromResourceAssembler.toCommandFromResource(plotId, resource);
        plotCommandService.handle(command);

        var plot = plotQueryService.getById(plotId);
        return plot.map(p -> ResponseEntity.ok(PlotResourceFromEntityAssembler.toResourceFromEntity(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{plotId}/size-area")
    public ResponseEntity<PlotResource> reassignSizeArea(
            @PathVariable Long plotId,
            @Valid @RequestBody ReassignSizeAreaResource resource) {

        var command = ReassignSizeAreaCommandFromResourceAssembler.toCommandFromResource(plotId, resource);
        plotCommandService.handle(command);

        var plot = plotQueryService.getById(plotId);
        return plot.map(p -> ResponseEntity.ok(PlotResourceFromEntityAssembler.toResourceFromEntity(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    private boolean canAccessPlot(com.floweytech.agrotrack.organization.domain.model.aggregate.Plot plot) {
        return organizationQueryService.getByOrganizationId(plot.getOrganizationId())
                .map(this::canAccessOrganization)
                .orElse(false);
    }

    private boolean canAccessOrganization(
            com.floweytech.agrotrack.organization.domain.model.aggregate.Organization organization) {
        var userId = new com.floweytech.agrotrack.organization.domain.model.valueobject.UserId(
                authenticatedUserProvider.getUserId());
        return authenticatedUserProvider.isAdministrator()
                || organization.getOwnerUserId().equals(userId)
                || organization.getUserIds().contains(userId);
    }
}

