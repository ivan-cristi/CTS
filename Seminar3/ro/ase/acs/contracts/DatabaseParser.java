package ro.ase.acs.contracts;

public interface DatabaseParser {
	void openConnection();
	void createTable(String tableName);
	void closeConnection();
}
