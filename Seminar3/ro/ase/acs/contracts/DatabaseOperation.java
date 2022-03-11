package ro.ase.acs.contracts;

public interface DatabaseOperation {
	void insert(String firstField, String secondField, String thirdField, String firstValue, String secondValue, String thirdValue);
	void select();
}
