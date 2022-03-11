package ro.ase.acs.main;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ro.ase.acs.contracts.DatabaseOperation;
import ro.ase.acs.contracts.DatabaseParser;
import ro.ase.acs.orchestrators.Orchestrator;


public class Main {

	public static void main(String[] args) {
		
			Scanner scanner = new Scanner(System.in);
			String databaseType, tableName;
			
			System.out.println("What type of database do you want to use? MongoDB / SQL");
			databaseType = scanner.next();
		
		  	DatabaseParser databaseParser = null;
	        DatabaseOperation databaseOperation = null;
	        
	        try {
	        	
	        	databaseParser = (DatabaseParser) Class.forName("ro.ase.acs.main." + databaseType).getDeclaredConstructor().newInstance();
	            databaseOperation = (DatabaseOperation) Class.forName("ro.ase.acs.main." + databaseType).getDeclaredConstructor().newInstance();
	            
	            Orchestrator orchestrator = new Orchestrator(databaseParser, databaseOperation);
	            
	            System.out.println("What will be the name of the created table?");
	            tableName = scanner.next();
	            
	            orchestrator.execute(tableName);
	            
	        } catch (InstantiationException e) {
	            e.printStackTrace();
	        } catch (IllegalAccessException e) {
	            e.printStackTrace();
	        } catch (InvocationTargetException e) {
	            e.printStackTrace();
	        } catch (NoSuchMethodException e) {
	            e.printStackTrace();
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	    }

}
