package com.floweytech.agrotrack.organization.domain.model.aggregate;

import com.floweytech.agrotrack.organization.domain.model.commands.CreatePlotCommand;
import com.floweytech.agrotrack.organization.domain.model.valueobject.OrganizationId;
import com.floweytech.agrotrack.organization.domain.model.valueobject.PlantTypeId;
import com.floweytech.agrotrack.organization.domain.model.valueobject.PlotId;
import com.floweytech.agrotrack.organization.domain.model.valueobject.SizeArea;
import com.floweytech.agrotrack.organization.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class Plot extends AuditableAbstractAggregateRoot<Plot> {
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "plot_id", unique = true))
    private PlotId plotId;
    @Setter
    private String plotName;
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "size", column = @Column(name = "size")),
        @AttributeOverride(name = "unit", column = @Column(name = "unit"))
    })
    private SizeArea sizeArea;
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "plant_type_id"))
    private PlantTypeId plantTypeId;
    private String location;
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "organization_id"))
    private OrganizationId organizationId;

    protected Plot() {
    }

    public Plot(CreatePlotCommand command) {
        this.plotName = command.plotName();
        this.sizeArea = command.sizeArea();
        this.plantTypeId = command.plantTypeId();
        this.location = command.location();
        this.organizationId = command.organizationId();
    }

    @PostPersist
    protected void onPostPersist() {
        this.plotId = new PlotId(this.getId());
    }

    public void reassignPlantType(PlantTypeId newPlantTypeId) {
        this.plantTypeId = newPlantTypeId;
    }

    public void reassignSizeArea(SizeArea newSizeArea) {
        this.sizeArea = newSizeArea;
    }
}
