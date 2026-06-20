package com.floweytech.agrotrack.organization.domain.model.aggregate;

import com.floweytech.agrotrack.organization.domain.model.commands.CreateOrganizationCommand;
import com.floweytech.agrotrack.organization.domain.model.valueobject.OrganizationId;
import com.floweytech.agrotrack.organization.domain.model.valueobject.SubscriptionId;
import com.floweytech.agrotrack.organization.domain.model.valueobject.UserId;
import com.floweytech.agrotrack.organization.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Organization extends AuditableAbstractAggregateRoot<Organization> {
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "organization_id", unique = true))
    private OrganizationId organizationId;
    @Setter
    private String organizationName;
    private Boolean isActive;
    private Integer maxPlots;
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "owner_user_id", nullable = false))
    private UserId ownerUserId;
    @ElementCollection
    @CollectionTable(name = "organization_user_ids", joinColumns = @JoinColumn(name = "organization_db_id"))
    @AttributeOverride(name = "value", column = @Column(name = "user_id"))
    private List<UserId> userIds = new ArrayList<>();
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "subscription_id", nullable = false, unique = true))
    private SubscriptionId subscriptionId;

    protected Organization() {}

    public Organization(CreateOrganizationCommand command) {
        this.organizationName = command.organizationName();
        this.isActive = false;
        this.maxPlots = command.maxPlots();
        this.ownerUserId = new UserId(command.ownerUserId());
        this.subscriptionId = new SubscriptionId(command.subscriptionId());
    }

    @PostPersist
    public void generateOrganizationId() {
        this.organizationId = new OrganizationId(this.getId());
    }

    public void addUser(UserId userId) {
        this.userIds.add(userId);
    }

    public void removeUser(UserId userId) {
        this.userIds.remove(userId);
    }

    public void activate() {
        this.isActive = true;
    }

    public void deactivate() {
        this.isActive = false;
    }

}
