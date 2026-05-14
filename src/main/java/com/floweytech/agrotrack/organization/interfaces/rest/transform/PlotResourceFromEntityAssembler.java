package com.floweytech.agrotrack.organization.interfaces.rest.transform;

import com.floweytech.agrotrack.organization.domain.model.aggregate.Plot;
import com.floweytech.agrotrack.organization.interfaces.rest.resources.PlotResource;

public class PlotResourceFromEntityAssembler {

    public static PlotResource toResourceFromEntity(Plot entity) {
        return new PlotResource(
            entity.getId(),
            entity.getPlotId().value(),
            entity.getPlotName(),
            entity.getSizeArea().getValue(),
            entity.getSizeArea().getUnit(),
            entity.getPlantTypeId().value(),
            entity.getLocation(),
            entity.getOrganizationId().value()
        );
    }
}
