package com.floweytech.agrotrack.organization.interfaces.rest.transform;

import com.floweytech.agrotrack.organization.domain.model.commands.ReassignSizeAreaCommand;
import com.floweytech.agrotrack.organization.domain.model.valueobject.SizeArea;
import com.floweytech.agrotrack.organization.interfaces.rest.resources.ReassignSizeAreaResource;

public class ReassignSizeAreaCommandFromResourceAssembler {

    public static ReassignSizeAreaCommand toCommandFromResource(Long plotId, ReassignSizeAreaResource resource) {
        return new ReassignSizeAreaCommand(
            plotId,
            new SizeArea(resource.size(), resource.unit())
        );
    }
}

