package play;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import play.dao.ConnectionManager;

public class InitListener implements ServletContextListener {

    public void contextDestroyed(ServletContextEvent arg0)  { 
         ConnectionManager.close();
    }

    public void contextInitialized(ServletContextEvent arg0)  { 
         System.out.println("Initialisation...");
         
         ConnectionManager.open();
         
         System.out.println("Done");
    }
	
}
