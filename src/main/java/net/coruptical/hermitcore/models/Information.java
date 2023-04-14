package net.coruptical.hermitcore.models;

import org.bukkit.entity.Player;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;


// Class that allows us to store player id's using PDCs.
public class Information implements Serializable {

    private UUID uuid;
    private String name;

    private Date issuedDate;

    public Information(Player p){
        this.uuid = p.getUniqueId();
        this.name = p.getName();

    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }
}
