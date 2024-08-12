package kolthy.springframework.spring6restmvc.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

import java.util.UUID;

public enum BeerStyle {
    LAGER, PILSNER, STOUT, PORTER, ALE, SAISON, PALE_ALE, WHEAT, IPA
}

