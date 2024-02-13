/**
 * 
 */
/**
 * 
 */
module server {
	requires java.rmi;
	requires transitive java.sql;
	requires rcif;
	exports serverpkg to java.rmi;
}