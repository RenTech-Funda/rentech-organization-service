package com.floweytech.agrotrack.organization.domain.model.commands;

import com.floweytech.agrotrack.organization.domain.model.valueobject.SizeArea;

public record ReassignSizeAreaCommand(
    Long plotId,
    SizeArea sizeArea
) {
}

