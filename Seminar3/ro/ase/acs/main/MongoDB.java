package ro.ase.acs.main;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import ro.ase.acs.contracts.DatabaseOperation;
import ro.ase.acs.contracts.DatabaseParser;

public class MongoDB implements DatabaseParser<String>, DatabaseOperation{
	
	private MongoClient mongoClient;
	private MongoDatabase mongoDb;
	private String tableName;
	private MongoCollection<Document> collection;

	@Override
	public void openConnection() {
		mongoClient = new MongoClient("localhost", 27017);
		mongoDb = mongoClient.getDatabase("test");
	}

	@Override
	public void createTable(String tableName) {
		
		this.tableName = tableName;
		
		collection = mongoDb.getCollection(tableName);
		
		if(collection != null) {
			collection.drop();
		} else {
			
			mongoDb.createCollection(tableName);
			collection = mongoDb.getCollection(tableName);
			
		}
	
	}

	@Override
	public void insert(String firstField, String secondField, String thirdField, String firstValue, String secondValue, String thirdValue) {
		
		Document firstDocument = new Document().append(firstField, firstValue).
				append(secondField, secondValue).append(thirdField, thirdValue);
		
		collection.insertOne(firstDocument);
		
	}
	

	@Override
	public void select() {
		
		FindIterable<Document> result = collection.find();
		for(Document doc : result) {
			System.out.println(doc);
		}
		
	}

	@Override
	public void closeConnection() {
		mongoClient.close();
	}
	
}
