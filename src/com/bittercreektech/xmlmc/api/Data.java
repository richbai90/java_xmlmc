package com.bittercreektech.xmlmc.api;

import com.bittercreektech.xmlmc.Connection;
import com.bittercreektech.xmlmc.Response;
import com.bittercreektech.xmlmc.Request;
import com.bittercreektech.xmlmc.types.Record;

import java.io.IOException;

/**
 * Data Service API class
 */
public class Data extends Service {

    public enum Database {
        SWDATA,
        SW_SYSTEMDB
    }
    /**
     * Populate the required connection and service parameters
     *
     * @param connection represents a connection to the server. {@link Connection}
     */
    public Data(Connection connection) {
        super(connection, "Data");
    }

    /**
     * Add a record into the database. No sql is required as SW abstracts this
     * @param databaseRecord a record to insert
     * @return {@link Request}
     * @throws IOException
     */
    public Response addRecord(Record databaseRecord) throws IOException {
        Request request = generateRequest("addRecord");
        request.setParam("table", databaseRecord.getTableName());
        request.setParam(databaseRecord);
        return invoke(request);
    }

    /**
     * Delete a record from the database
     * @param table table name to delete the record from
     * @param keyValue primary key value to identify the record
     * @return {@link Request}
     * @throws IOException
     */
    public Response deleteRecord(String table, String keyValue) throws IOException {
        Request request = generateRequest("deleteRecord");
        request.setParam("table",table);
        request.setParam("keyValue",keyValue);
        return invoke(request);
    }

    /**
     * get column metadata
     * @param database Either the application database (swdata) or the cache database (sw_systemdb)
     * @param table table to get metadata from
     * @return {@link Response}
     * @throws IOException
     */
    public Response getColumnInfoList(Database database, String table) throws IOException {
        Request request = generateRequest("getColumnInfoList");
        request.setParam("database",database.toString().toLowerCase());
        request.setParam("table",table);
        return invoke(request);
    }

    /**
     * Select * from table where keyColumn = keyValue
     * @param database database to query
     * @param table table name to query
     * @param keyValue key value to filter on
     * @param formatValues whether or not to format values, IE callref becomes F0001234
     * @return {@link Response}
     * @throws IOException
     */
    public Response getRecord(Database database, String table, String keyValue, boolean formatValues) throws IOException {
        Request request = generateRequest("getRecord");
        request.setParam("database",database.toString().toLowerCase());
        request.setParam("table",table);
        request.setParam("keyValue",keyValue);
        request.setParam("formatValues",formatValues ? "true" : "false");
        return invoke(request);
    }

    /**
     * Database defaults to {@link Database#SWDATA}
     * @see Data#getRecord(Database, String, String, boolean)
     */
    public Response getRecord(String table, String keyValue, boolean formatValues) throws IOException {
        Request request = generateRequest("getRecord");
        return getRecord(Database.SWDATA, table, keyValue, formatValues);
    }

    /**
     * Database defaults to {@link Database#SWDATA}
     * formatValues defaults to false
     * @see Data#getRecord(Database, String, String, boolean)
     */
    public Response getRecord(String table, String keyValue) throws IOException {
        Request request = generateRequest("getRecord");
        return getRecord(Database.SWDATA, table, keyValue,false);
    }

    /**
     * Get a list of stored queries available on the system
     * @param folder Identifies the folder where the stored queries are sourced
     * @return {@link Response}
     * @throws IOException
     */
    public Response getStoredQueryList(String folder) throws IOException {
        Request request = generateRequest("getStoredQueryList");
        request.setParam("folder",folder);
        return invoke(request);
    }

    /**
     * Get a list of tables in a given database
     * @param database Database to get the tables in
     * @return {@link Response}
     * @throws IOException
     */
    public Response getTableInfoList(Database database) throws IOException {
        Request request = generateRequest("getTableInfoList");
        request.setParam("database",database.toString().toLowerCase());
        return invoke(request);
    }

    /**
     * Invoke a stored query
     * @param storedQuery Stored query to invoke in the format folder/query
     * @param params list of name=value parameters
     * @return {@link Response}
     * @throws IOException
     */
    public Response invokeStoredQuery(String storedQuery, String ...params) throws IOException {
        Request request = generateRequest("invokeStoredQuery");
        request.setParam("storedQuery",storedQuery);
        String parameters = "";
        for (String parameter :
                params) {
            parameters += parameter + "&";
        }
        if(parameters.length() > 0 && parameters.charAt(parameters.length() - 1) == '&') {
            parameters = parameters.substring(0, parameters.length() - 1);
        }
        request.setParam("parameters",parameters);
        return invoke(request);
    }

    /**
     * Run a data import on the server
     * @param confFileName name of the config xml file
     * @param dataFileName name of the data xml file
     * @return No output parameters
     * @throws IOException
     */
    public Response runDataImport(String confFileName, String dataFileName) throws IOException {
        Request request = generateRequest("runDataImport");
        request.setParam("confFileName",confFileName);
        request.setParam("dataFileName",dataFileName);
        return invoke(request);
    }

    /**
     * Run an arbitrary sql query against either of the two Supportworks databases
     * @param database which database to run the query against
     * @param query the query to run
     * @param formatValues whether to format values. IE callref becomes F001234
     * @return {@link Response}
     * @throws IOException
     */
    public Response sqlQuery(Database database, String query, boolean formatValues) throws IOException {
        Request request = generateRequest("sqlQuery");
        request.setParam("database",database.toString().toLowerCase());
        request.setParam("query",query);
        request.setParam("formatValues",formatValues ? "true" : "false");
        return invoke(request);
    }

    /**
     * @param query Query to run
     * @see Data#sqlQuery(Database, String, boolean)
     * @throws IOException
     */
    public Response sqlQuery(String query) throws IOException {
        return sqlQuery(Database.SWDATA,query,false);
    }

    /**
     * Update a record
     * @param record A Record object reflecting the record with updated values
     * @return {@link Response}
     * @throws IOException
     */
    public Response updateRecord(Record record) throws IOException {
        Request request = generateRequest("updateRecord");
        request.setParam("table",record.getTableName());
        request.setParam(record);
        return invoke(request);
    }

}
