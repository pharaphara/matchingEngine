package fr.eql.matchingEngine.services.servicesinterface;

import fr.eql.matchingEngine.aspect.GLogService;
import fr.eql.matchingEngine.dto.model.GeobusApiModel;

/**
 * Created by xxxxxx on xx/xx/xxxx.
 */

public interface EmployeeServices {

    /**
     * Description
     * @return
     */

    String addDescription(GeobusApiModel name);

    String addSecondDesc(String name);
}
