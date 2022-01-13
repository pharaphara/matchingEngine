package fr.eql.matchingEngine.aspect;




import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import fr.eql.matchingEngine.utils.Util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.Objects;

import static java.lang.System.currentTimeMillis;
import static org.apache.logging.log4j.util.Strings.isNotBlank;

/**
 * Aspect for logging execution.
 *
 * @author SBM
 *
 */
@Aspect
@Component
@ConfigurationProperties("interceptor")
public class LoggingAspect {

    @Value("${project.name}")
    String projectName;

    @Value("${logging.prefix}")
    String prefixLog;

    @Value("${logging.numberInfoChar}")
    int numberInfoChar;

    @Value("${logging.numberDebugChar}")
    int numberDebugChar;

    //TODO ADD PACKAGE BEFORE TARGET METHODE

    public static final String API_Repository = "execution(* fr.eql.matchingEngine.controller.*.*(..))";
    public static final String Service_Repository = "execution(* fr.eql.matchingEngine.services.servicesinterface.*.*(..))";
    public static final String End_Point_Repository = "execution(* fr.eql.matchingEngine.endpointservices.servicesinterface.*.*(..))";
    public static final String Exception_Repository = "execution(* fr.eql.matchingEngine.exception.*.*(..))";


    @Pointcut(API_Repository)
    public void anyServiceController() {}

    @Pointcut(Service_Repository)
    public void anyServiceMethod() {}

    @Pointcut(End_Point_Repository)
    public void anyEndPointCall() {}

    @Pointcut(Exception_Repository)
    public void anyException() {}

    @Around("anyServiceController()")
    public Object logAroundAllController(ProceedingJoinPoint joinPoint) throws Throwable {

        Logger LOGGER = LoggerFactory.getLogger(joinPoint.getTarget().getClass());

        long start = currentTimeMillis();
        long duration = 0;
        String paramsOutput = null;
        String statutResponse = "[KO]";
        Object result = null;
        String geobusInstanceId = null;

        Object parameterObject = getParameter(joinPoint, "geobusInstanceId");
        if ( Objects.nonNull(parameterObject) ) {
            geobusInstanceId = String.valueOf(parameterObject);
        }

        MDC.put("instanceId", isNotBlank(geobusInstanceId) ? geobusInstanceId : Util.generateUUid(projectName));

        //MDC.put("CUSTOM_METHOD_NAME_KEY", joinPoint.getSignature().getName());

        try {
            MDC.put("actualMethodName", joinPoint.getSignature().getName() + " -> Aspect methode : " ) ;

            String paramsInput = "[";
            if(joinPoint.getArgs()!=null) {
                for (Object oneArg : joinPoint.getArgs()){
                    if(oneArg != null && !oneArg.getClass().getName().contains("HttpServlet")){
                        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                        paramsInput += gson.toJson(oneArg) + ",";
                    }else if(oneArg != null && oneArg.getClass().getName().contains("HttpServlet")){
                        paramsInput += String.valueOf(requestToJson((HttpServletRequest)oneArg)) + ",";
                    }
                }
            }
            paramsInput = paramsInput + "]";

            LOGGER.info(prefixLog + " API START - " + joinPoint.getSignature().getName() + " - Data input : " +  (isNotBlank(paramsInput) ? paramsInput.substring(0, (paramsInput.length() > numberInfoChar ? numberInfoChar : paramsInput.length())) : ""));
            LOGGER.debug(prefixLog + " API RECEIVE - " + (isNotBlank(paramsInput) ? paramsInput.substring(0, (paramsInput.length() > numberDebugChar ? numberDebugChar : paramsInput.length())) : ""));

            MDC.remove("actualMethodName");

            result = joinPoint.proceed();
            statutResponse = "[OK]";

            paramsOutput = "";
            if(result != null && !result.getClass().getName().contains("HttpServlet")){
                Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                paramsOutput = gson.toJson(result);
            }else if(result != null && result.getClass().getName().contains("HttpServlet")){
                paramsOutput = String.valueOf(requestToJson((HttpServletRequest)result));
            }

        } finally {
            MDC.put("actualMethodName", joinPoint.getSignature().getName() + " -> Aspect methode : " ) ;
            duration = currentTimeMillis() - start;

            LOGGER.debug( prefixLog + " API RETURN - " + (isNotBlank(paramsOutput) ? paramsOutput.substring(0, (paramsOutput.length() > numberDebugChar ? numberDebugChar : paramsOutput.length())) : ""));
            if(statutResponse.equals("[KO]")){
                LOGGER.error(prefixLog + " API ERROR - " + joinPoint.getSignature().getName()+ " [" + duration + " ms] " + statutResponse);
            }
            LOGGER.info(prefixLog + " API STOP - " + joinPoint.getSignature().getName()+ " [" + duration + " ms] " + statutResponse + " - Data output: " + (isNotBlank(paramsOutput) ? paramsOutput.substring(0, (paramsOutput.length() > numberInfoChar ? numberInfoChar : paramsOutput.length())) : ""));
            MDC.remove("actualMethodName");
            if (result != null) MDC.remove("instanceId");
        }
        return result;
    }

    @Around("@annotation(fr.eql.matchingEngine.aspect.GLogService)")
    public Object logAroundAllMethods(ProceedingJoinPoint joinPoint) throws Throwable {

        Logger LOGGER = LoggerFactory.getLogger(joinPoint.getTarget().getClass());

        long start = currentTimeMillis();
        long duration = 0;
        String paramsOutput = null;
        Object result = null;
        String statutResponse = "[KO]";

        try {
            MDC.put("actualMethodName", joinPoint.getSignature().getName() + " -> Aspect methode : " ) ;

            String paramsInput = "[";
            if(joinPoint.getArgs()!=null) {
                for (Object oneArg : joinPoint.getArgs()){
                    if(oneArg != null && !oneArg.getClass().getName().contains("HttpServlet")){
                        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                        paramsInput += gson.toJson(oneArg) + ",";
                    }else if(oneArg != null && oneArg.getClass().getName().contains("HttpServlet")){
                        paramsInput += String.valueOf(requestToJson((HttpServletRequest)oneArg)) + ",";
                    }
                }
            }
            paramsInput = paramsInput + "]";

            LOGGER.info(prefixLog + " SERVICE START - " + joinPoint.getSignature().getName() + " - Data input : " +  (isNotBlank(paramsInput) ? paramsInput.substring(0, (paramsInput.length() > numberInfoChar ? numberInfoChar : paramsInput.length())) : ""));

            LOGGER.debug(prefixLog + " SERVICE RECEIVE - " + (isNotBlank(paramsInput) ? paramsInput.substring(0, (paramsInput.length() > numberDebugChar ? numberDebugChar : paramsInput.length())) : ""));

            MDC.remove("actualMethodName");

            result = joinPoint.proceed();
            statutResponse = "[OK]";

            paramsOutput = "";
            if(result != null && !result.getClass().getName().contains("HttpServlet")){
                Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                paramsOutput = gson.toJson(result);
            }else if(result != null && result.getClass().getName().contains("HttpServlet")){
                paramsOutput = String.valueOf(requestToJson((HttpServletRequest)result));
            }

        } finally {
            MDC.put("actualMethodName", joinPoint.getSignature().getName() + " -> Aspect methode : " ) ;
            duration = currentTimeMillis() - start;
            LOGGER.debug(prefixLog + " SERVICE RETURN - " + (isNotBlank(paramsOutput) ? paramsOutput.substring(0, (paramsOutput.length() > numberDebugChar ? numberDebugChar : paramsOutput.length())) : ""));
            if(statutResponse.equals("[KO]")){
                LOGGER.error(prefixLog + " SERVICE ERROR - " + joinPoint.getSignature().getName()+ " [" + duration + " ms] " + statutResponse);
            }
            LOGGER.info(prefixLog + " SERVICE STOP - " + joinPoint.getSignature().getName()+ " [" + duration + " ms] " + statutResponse + " - Data output: " + (isNotBlank(paramsOutput) ? paramsOutput.substring(0, (paramsOutput.length() > numberInfoChar ? numberInfoChar : paramsOutput.length())) : ""));
            MDC.remove("actualMethodName");
        }
        return result;
    }

    @Around("anyEndPointCall()")
    public Object logAroundEndPoint(ProceedingJoinPoint joinPoint) throws Throwable {

        Logger LOGGER = LoggerFactory.getLogger(joinPoint.getTarget().getClass());

        long start = currentTimeMillis();
        long duration = 0;
        Object result = null;
        String paramsOutput = null;
        String statutResponse = "[KO]";

        try {
            MDC.put("actualMethodName", joinPoint.getSignature().getName() + " -> Aspect methode : " ) ;

            String paramsInput = "[";
            if(joinPoint.getArgs()!=null) {
                for (Object oneArg : joinPoint.getArgs()){
                    if(oneArg != null && !oneArg.getClass().getName().contains("HttpServlet")){
                        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                        paramsInput += gson.toJson(oneArg) + ",";
                    }else if(oneArg != null && oneArg.getClass().getName().contains("HttpServlet")){
                        paramsInput += String.valueOf(requestToJson((HttpServletRequest)oneArg)) + ",";
                    }
                }
            }
            paramsInput = paramsInput + "]";

            LOGGER.info(prefixLog + " CALL START - " + joinPoint.getSignature().getName() + " - Data input : " +  (isNotBlank(paramsInput) ? paramsInput.substring(0, (paramsInput.length() > numberInfoChar ? numberInfoChar : paramsInput.length())) : ""));

            LOGGER.debug(prefixLog + " CALL RECEIVE - " + (isNotBlank(paramsInput) ? paramsInput.substring(0, (paramsInput.length() > numberDebugChar ? numberDebugChar : paramsInput.length())) : ""));
            MDC.remove("actualMethodName");

            result = joinPoint.proceed();
            statutResponse = "[OK]";

            paramsOutput = "";
            if(result != null && !result.getClass().getName().contains("HttpServlet")){
                Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                paramsOutput = gson.toJson(result);
            }else if(result != null && result.getClass().getName().contains("HttpServlet")){
                paramsOutput = String.valueOf(requestToJson((HttpServletRequest)result));
            }

        } finally {
            MDC.put("actualMethodName", joinPoint.getSignature().getName() + " -> Aspect methode : " ) ;
            duration = currentTimeMillis() - start;
            LOGGER.debug(prefixLog + " CALL RETURN - " + (isNotBlank(paramsOutput) ? paramsOutput.substring(0, (paramsOutput.length() > numberDebugChar ? numberDebugChar : paramsOutput.length())) : ""));

            if(statutResponse.equals("[KO]")){
                LOGGER.error(prefixLog + " CALL ERROR - " + joinPoint.getSignature().getName()+ " [" + duration + " ms] " + statutResponse);
            }
            LOGGER.info(prefixLog + " CALL STOP - " + joinPoint.getSignature().getName()+ " [" + duration + " ms] " + statutResponse+ " - Data output: " + (isNotBlank(paramsOutput) ? paramsOutput.substring(0, (paramsOutput.length() > numberInfoChar ? numberInfoChar : paramsOutput.length())) : ""));
            MDC.remove("actualMethodName");
        }
        return result;
    }

    private Object getParameter(ProceedingJoinPoint joinPoint, String parameterName) {
        Object valueParameter = null;
        if (Objects.nonNull(joinPoint) && joinPoint.getSignature() instanceof MethodSignature
                && Objects.nonNull(parameterName) ) {
            MethodSignature method = (MethodSignature)joinPoint.getSignature();
            String[] parameters = method.getParameterNames();
            for (int t = 0; t< parameters.length; t++) {
                if( Objects.nonNull(parameters[t]) && parameters[t].equals(parameterName)) {
                    Object[] obj = joinPoint.getArgs();
                    valueParameter = obj[t];
                }
            }
        }
        return valueParameter;
    }

    public JsonObject requestToJson(HttpServletRequest request){
        JsonObject jsonObject = new JsonObject();
        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) { /*report an error*/ }
        if(isNotBlank(jb.toString())){
            jsonObject =  new JsonParser().parse(jb.toString()).getAsJsonObject();
        }
        return jsonObject;
    }

    // Added to get data from application.yml
    // Data in pointcut must be constant can not be from application.yml
    // solution to be continued if not possible implement aop.xml
    /*@Value("${aspect.package.api}")
    List<String> packageToLog;

    String test = "";

    public static final String EXECUTION_PUBLIC_GET = new String("execution(* fr.eql.matchingEngine.exception..*.*(..))");

    @PostConstruct
    public void init() throws Exception {
        int i= 0;
        for(String tt:packageToLog){
            i+=1;
            test += "execution(*" + tt + "..*.*(..))";
            if(i<packageToLog.size()){
                test += " || ";
            }
        }

        setFinalStaticField(LoggingAspect.class, "EXECUTION_PUBLIC_GET", test);

        String mmm = EXECUTION_PUBLIC_GET;
        Field fff = LoggingAspect.class.getDeclaredField("EXECUTION_PUBLIC_GET");
        Object value = fff.get(this);
    }

    private static void setFinalStaticField(Class<?> clazz, String fieldName, Object value)
            throws ReflectiveOperationException {

        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);

        Field modifiers = Field.class.getDeclaredField("modifiers");
        modifiers.setAccessible(true);
        modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, value);
    }*/
}