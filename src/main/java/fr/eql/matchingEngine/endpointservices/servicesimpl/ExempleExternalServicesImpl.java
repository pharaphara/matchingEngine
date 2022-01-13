package fr.eql.matchingEngine.endpointservices.servicesimpl;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import fr.eql.matchingEngine.dto.model.GeobusApiModel;
import fr.eql.matchingEngine.endpointservices.servicesinterface.EmployeeExternalServices;

/**
 * Created by xxxxxxxx on xx/xx/xxxx.
 */

@Service
public class ExempleExternalServicesImpl implements EmployeeExternalServices {

    Logger log = LoggerFactory.getLogger(ExempleExternalServicesImpl.class);

    /**
     * Description
     * @return
     */
    @Override
    public String postDescription(GeobusApiModel name){

        log.info("Information to log in my end point service implementation ");

        String send = name.getName() +" has been sent the endpoint ";

        return send;
    }

}
