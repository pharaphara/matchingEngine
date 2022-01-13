package fr.eql.matchingEngine.services.servicesimpl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import fr.eql.matchingEngine.aspect.GLogService;
import fr.eql.matchingEngine.dto.model.GeobusApiModel;
import fr.eql.matchingEngine.services.servicesinterface.EmployeeServices;

/**
 * Created by xxxxxxxx on xx/xx/xxxx.
 */

@Service
public class ExempleServicesImpl implements EmployeeServices {

    Logger log = LoggerFactory.getLogger(ExempleServicesImpl.class);

    /**
     * Description
     * @return
     */
    @Override
    @GLogService
    public String addDescription(GeobusApiModel name){
        // if we call other api
        //log.info("CALL - get external api");

        // if we have a simple implementation
        //log.info("Start Implementation - add description");
        //log.debug("Implementation receive - Name is : " + name);

        log.info("Information to log in my service implementation ");

        String welcome = "Welcome "+ name.getName() +" to our empty project structure ";

        // if we call other api
        //log.debug("CALL RESPONSE - get external api response");

        // if we have a simple implementation
        //log.debug("Implementation response - response is : " + welcome);
        //log.info("End Implementation - add description");
        return welcome;
    }

    @Override
    public String addSecondDesc(String name){

        log.info("No aspect log");

        String finalText = name + " for Geobus ";


        return finalText;
    }

}
