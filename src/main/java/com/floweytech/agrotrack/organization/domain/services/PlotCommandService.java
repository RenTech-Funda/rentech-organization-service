package com.floweytech.agrotrack.organization.domain.services;

import com.floweytech.agrotrack.organization.domain.model.commands.CreatePlotCommand;
import com.floweytech.agrotrack.organization.domain.model.commands.ReassignPlantTypeCommand;
import com.floweytech.agrotrack.organization.domain.model.commands.ReassignSizeAreaCommand;
public interface PlotCommandService {
    Long handle(CreatePlotCommand command);
    void handle(ReassignPlantTypeCommand command);
    void handle(ReassignSizeAreaCommand command);
}



