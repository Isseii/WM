pipeline {
    agent any
    stages{
        stage('startDomain'){
                           steps{
                                  sh '''
                                    sudo kill $(sudo lsof -t -i:8080) || echo "8080 is NULL"                                 
                                    /home/student/JavaTools/payara5.2020.5/bin/asadmin start-domain domain1
                                    '''    
                                }
                            }

        
        stage('parallelStage'){           
            parallel{
                stage ('startServer'){
                       steps{
                               sh '''                         
                               cd /home/student/JavaTools/db-derby-10.14.2.0-bin/bin                          
                               sudo ./startNetworkServer  || echo "1527 is already taken" &                                                         
                               '''   
                            }                            
                         }   
                        
                    stage('Build') {
                                  steps{                 
                                        sh '''                                         
                                        mvn clean install  
                                        mvn test
                                        cd /home/student/JavaTools/db-derby-10.14.2.0-bin/bin                                        
                                        sudo expect ./databaseSetup.sh                                                     
                                        /home/student/JavaTools/payara5.2020.5/bin/asadmin deploy --force /home/student/.jenkins/workspace/WMPipeline/target/WM-1.1.war    
                                        '''
                                       }
                                   }
                   
                    }
            }
    }
}
