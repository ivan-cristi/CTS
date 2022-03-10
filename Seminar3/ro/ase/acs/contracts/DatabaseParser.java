package ro.ase.acs.contracts;

public interface DatabaseParser<T> {
	void openConnection();
	void createTable(T tableName);
	void closeConnection();
}
