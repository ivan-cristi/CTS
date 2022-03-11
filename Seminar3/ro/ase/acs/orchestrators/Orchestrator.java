package ro.ase.acs.orchestrators;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import ro.ase.acs.contracts.DatabaseOperation;
import ro.ase.acs.contracts.DatabaseParser;

public class Orchestrator {
	
	Scanner scanner = new Scanner(System.in);
	String Fields[] = new String[3], Values[] = new String[3];
	
	private final DatabaseParser databaseParser;
    private final DatabaseOperation databaseOperation;

    public Orchestrator(DatabaseParser databaseParser, DatabaseOperation databaseOperation){
        this.databaseParser = databaseParser;
        this.databaseOperation = databaseOperation;
    }

    public void execute(String tableName) {
       
       databaseParser.openConnection();
       databaseParser.createTable(tableName);
       
       System.out.println("What 3 fields will the table have?");
       for(int i = 0; i < 3; i++) {
    	   Fields[i] = scanner.next();
       }
       
       System.out.println("What 3 values will the fields have?");
       for(int i = 0; i < 3; i++) {
    	   Values[i] = scanner.next();
       }
       
       databaseOperation.insert(Fields[0], Fields[1], Fields[2], Values[0], Values[1], Values[2]);
       databaseOperation.select();
       
       databaseParser.closeConnection();
       
    }

}
