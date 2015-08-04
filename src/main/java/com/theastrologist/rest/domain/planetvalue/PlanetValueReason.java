package com.theastrologist.rest.domain.planetvalue;

import com.theastrologist.rest.domain.SkyArtefact;

/**
 * Created by Samy on 26/07/2015.
 */
public class PlanetValueReason {
    private PlanetValueReasonType reasonType;
    private SkyArtefact artefact;

    public PlanetValueReason(PlanetValueReasonType reason, SkyArtefact skyArtefact) {
        reasonType = reason;
        artefact = skyArtefact;
    }
}
