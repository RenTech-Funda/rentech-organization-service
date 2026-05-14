package com.floweytech.agrotrack.organization.domain.model.entities;

import com.floweytech.agrotrack.organization.domain.model.commands.CreatePlantTypeCommand;
import com.floweytech.agrotrack.organization.domain.model.valueobject.PlantTypeId;
import com.floweytech.agrotrack.organization.domain.model.valueobject.PlantTypes;
import com.floweytech.agrotrack.organization.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class PlantType extends AuditableModel {

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "plant_type_id", unique = true))
    private PlantTypeId plantTypeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "plant_type")
    private PlantTypes plantTypes;

    private String name;

    private String description;

    private Boolean predefined;

    protected PlantType() {
    }

    public PlantType(CreatePlantTypeCommand command) {
        this.plantTypeId = command.plantTypeId();
        this.plantTypes = command.plantTypes();
        this.name = command.name();
        this.description = command.description();
        this.predefined = false;
    }

    // Constructor para seeding sin PlantTypeId (se generará en @PostPersist)
    public PlantType(PlantTypes plantTypes, String name, String description, Boolean predefined) {
        this.plantTypes = plantTypes;
        this.name = name;
        this.description = description;
        this.predefined = predefined;
    }

    @PostPersist
    protected void onPostPersist() {
        this.plantTypeId = new PlantTypeId(this.getId());
    }

    public void markAsPredefined() {
        this.predefined = true;
    }
}
