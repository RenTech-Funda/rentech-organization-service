package com.floweytech.agrotrack.organization.domain.services;

import com.floweytech.agrotrack.organization.domain.model.commands.CreatePlotCommand;
import com.floweytech.agrotrack.organization.domain.model.commands.ReassignPlantTypeCommand;
import com.floweytech.agrotrack.organization.domain.model.commands.ReassignSizeAreaCommand;
import jakarta.servlet.http.HttpServletRequest;

public interface PlotCommandService {
    Long handle(CreatePlotCommand command, HttpServletRequest request);
    void handle(ReassignPlantTypeCommand command, HttpServletRequest request);
    void handle(ReassignSizeAreaCommand command, HttpServletRequest request);
}



