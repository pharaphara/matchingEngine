

1/ In the controller the first thing in the method put instanceId into MDC

MDC in Log4j
Let's introduce MDC.

MDC in Log4j allows us to fill a map-like structure with pieces of information that are accessible to the appender when the log message is actually written.


The MDC structure is internally attached to the executing thread in the same way a ThreadLocal variable would be.

And so, the high-level idea is:

to fill the MDC with pieces of information that we want to make available to the appender
then log a message
and finally, clear the MDC

2/ Utilisation de MDC lors d'un multithread
En cours ???

2/ Principe et norme

   Log LEVEL :   ===> Ajouter la définition des niveaux de trace au readme du template API

    ERROR (defaut prod)
       - Uniquement et toujours quand une exception bloquante pour le traitement. i.e. le retour de l'API à l'appelant est une erreur qui par exemple rend un flux KO dans GEST.
       - Doit contenir le nom l'étape (ex: méthode appelée ....)

   - WARNING (default prod)
       - non bloquant pour te traitement
       - une erreur ou un point d'attention important à éventuellement traiter par l'appelant.
         ex: Traitement d'une liste de données reçue pour lequel un retour global ok est fait alors qu'un des éléments est en erreur.

   - INFO :
       - Les données utiles d'entrée et de sortie du traitement (hors infos de sécurité tel qu'un mot de passe)
       - chaque étape de traitement doit faire l'objet d'une ligne indiquant le nom nom de l'étape (à minima le nom de la méthode) avec les données significatives de l'étapes (ex : si on traite une boucle sur des données, indiquer la donnée traitée tel que le nom d'un fichier, l'id d'un client etc ...)

   - DEBUG :
       - Ajouter dans chaque étape les données complètes d'échanges
              - données reçues en input du service
              - données échangées en entrée et retour des appels externes vers d'autres systèmes
              - les données retournées par le service
     Si la volumétries des données est importante un choix devra être fait lors de conception sur les données utiles à tracer. (ex: limiter par exemple à1Mo caracteres, ou mieux aux données les plus utiles) Si les données sont volumineuses doubler cette ligne DEBUG avec un niveau TRACE avec toutes les données.

   - TRACE :
     - Toute information fine jugée pertinente par le développeur pour aider au debug/diagnostique


3/ Message log :
   (Obligatoire à minima)
        - Ajouter un message fixe de début et de fin qu'on trouvera dans toute les api qui servera par exemple à évaluer la performance de l'application :
        Message de debut : START SERVICE  --> de niveau info
        Message de fin : STOP SERVICE [Duree ms] [OK/KO] --> de niveau info

   - Ajouter le nom de service dans le pattern : le nom de service et le nom de la méthode existe maintenant  --> OK

   NomService id INFO GLOG START SERVICE - Method
   NomService id INFO GLOG RECEIVE - [data utiles]
   NomService id INFO GLOG START CALL - targetName [data utiles]
   NomService id INFO GLOG STOP CALL - targetName [DataUtiles] [150 ms]
   NomService id INFO GLOG START SERVICE - targetName [data utiles]
   NomService id INFO GLOG STOP SERVICE - targetName [DataUtiles] [150 ms]
   NomService id INFO GLOG RETURN - [data utiles]
   NomService id INFO GLOG STOP API - Method [350 ms] [OK]

   Chaque ligne RECEIVE, RETURN et CALL doit être accompagnée
   - D'une ligne DEBUG contenant les données utiles (max 2Mo => parametrable)
   - D'une ligne TRACE contenant l'intégralité des données si celles-ci étaient trop volumineuses et n'ont pas intégralement été tracées dans le précédent DEBUG
   
   

   *******************************************************************************************************
   
   Gestion des certificats:
   
   - Ajout du certificat:    curl -k {{variable_certificat}} -o $CERTIF_PATH'/certificat_file.cer'
   - copier le cacert de JDK et le renommer custom_cacert: 
   cd $CERTIF_PATH
   yes | cp -rf {{TOMCAT_CONF.JDK_PATH}}/jre/lib/security/cacerts custom-cacerts
   - Importer le certificat dans le custom-cacert:
   cd $CERTIF_PATH
    keytool -import -trustcacerts -keystore custom-cacerts -storepass changeit -noprompt -alias aliasCertif -file certificat_file.cer
   
   
   