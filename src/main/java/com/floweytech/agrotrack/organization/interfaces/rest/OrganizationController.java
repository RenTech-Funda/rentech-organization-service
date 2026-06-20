package com.floweytech.agrotrack.organization.interfaces.rest;

import com.floweytech.agrotrack.organization.domain.model.queries.GetOrganizationsByUserIdQuery;
import com.floweytech.agrotrack.organization.domain.model.valueobject.OrganizationId;
import com.floweytech.agrotrack.organization.domain.model.valueobject.SubscriptionId;
import com.floweytech.agrotrack.organization.domain.model.valueobject.UserId;
import com.floweytech.agrotrack.organization.domain.services.OrganizationCommandService;
import com.floweytech.agrotrack.organization.domain.services.OrganizationQueryService;
import com.floweytech.agrotrack.organization.interfaces.rest.resources.AddUserResource;
import com.floweytech.agrotrack.organization.interfaces.rest.resources.OrganizationResource;
import com.floweytech.agrotrack.organization.interfaces.rest.resources.RemoveUserResource;
import com.floweytech.agrotrack.organization.interfaces.rest.resources.UpdateOrganizationNameResource;
import com.floweytech.agrotrack.organization.interfaces.rest.transform.AddUserCommandFromResourceAssembler;
import com.floweytech.agrotrack.organization.interfaces.rest.transform.OrganizationResourceFromEntityAssembler;
import com.floweytech.agrotrack.organization.interfaces.rest.transform.RemoveUserCommandFromResourceAssembler;
import com.floweytech.agrotrack.organization.interfaces.rest.transform.UpdateOrganizationNameCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/organizations", produces = "application/json")
@Tag(name = "Organizations", description = "Organization Management Endpoints")
public class OrganizationController {

    private final OrganizationCommandService organizationCommandService;
    private final OrganizationQueryService organizationQueryService;

    public OrganizationController(OrganizationCommandService organizationCommandService,
                                   OrganizationQueryService organizationQueryService) {
        this.organizationCommandService = organizationCommandService;
        this.organizationQueryService = organizationQueryService;
    }

    @GetMapping("/{organizationId}")
    public ResponseEntity<OrganizationResource> getOrganizationById(@PathVariable Long organizationId) {
        var organization = organizationQueryService.getByOrganizationId(new OrganizationId(organizationId));

        return organization.map(org -> ResponseEntity.ok(OrganizationResourceFromEntityAssembler.toResourceFromEntity(org)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-name/{organizationName}")
    public ResponseEntity<OrganizationResource> getOrganizationByName(@PathVariable String organizationName) {
        var organization = organizationQueryService.getByOrganizationName(organizationName);

        return organization.map(org -> ResponseEntity.ok(OrganizationResourceFromEntityAssembler.toResourceFromEntity(org)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-subscription/{subscriptionId}")
    public ResponseEntity<OrganizationResource> getOrganizationBySubscriptionId(@PathVariable Long subscriptionId) {
        var organization = organizationQueryService.getBySubscriptionId(new SubscriptionId(subscriptionId));

        return organization.map(org -> ResponseEntity.ok(OrganizationResourceFromEntityAssembler.toResourceFromEntity(org)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-owner/{ownerUserId}")
    public ResponseEntity<List<OrganizationResource>> getOrganizationsByOwnerUserId(@PathVariable Long ownerUserId) {
        var organizations = organizationQueryService.getByOwnerUserId(new UserId(ownerUserId));

        var resources = organizations.stream()
                .map(OrganizationResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    @GetMapping("/by-user/{userId}")
    @Operation(summary = "Get organizations by user ID in members list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Organizations retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No organizations found")
    })
    public ResponseEntity<List<OrganizationResource>> getOrganizationsByUserId(@PathVariable Long userId) {
        var query = new GetOrganizationsByUserIdQuery(userId);
        var organizations = organizationQueryService.handle(query);

        if (organizations.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var resources = organizations.stream()
                .map(OrganizationResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    @PutMapping("/{organizationId}/name")
    public ResponseEntity<OrganizationResource> updateOrganizationName(
            @PathVariable Long organizationId,
            @Valid @RequestBody UpdateOrganizationNameResource resource) {

        var command = UpdateOrganizationNameCommandFromResourceAssembler.toCommandFromResource(organizationId, resource);
        organizationCommandService.handle(command);

        var organization = organizationQueryService.getByOrganizationId(new OrganizationId(organizationId));
        return organization.map(org -> ResponseEntity.ok(OrganizationResourceFromEntityAssembler.toResourceFromEntity(org)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{organizationId}/users/add")
    public ResponseEntity<OrganizationResource> addUser(
            @PathVariable Long organizationId,
            @Valid @RequestBody AddUserResource resource) {

        var command = AddUserCommandFromResourceAssembler.toCommandFromResource(organizationId, resource);
        organizationCommandService.handle(command);

        var organization = organizationQueryService.getByOrganizationId(new OrganizationId(organizationId));
        return organization.map(org -> ResponseEntity.ok(OrganizationResourceFromEntityAssembler.toResourceFromEntity(org)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{organizationId}/users/remove")
    public ResponseEntity<OrganizationResource> removeUser(
            @PathVariable Long organizationId,
            @Valid @RequestBody RemoveUserResource resource) {

        var command = RemoveUserCommandFromResourceAssembler.toCommandFromResource(organizationId, resource);
        organizationCommandService.handle(command);

        var organization = organizationQueryService.getByOrganizationId(new OrganizationId(organizationId));
        return organization.map(org -> ResponseEntity.ok(OrganizationResourceFromEntityAssembler.toResourceFromEntity(org)))
                .orElse(ResponseEntity.notFound().build());
    }
}
