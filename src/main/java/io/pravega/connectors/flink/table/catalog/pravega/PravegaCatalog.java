package io.pravega.connectors.flink.table.catalog.pravega;

import io.pravega.client.ClientConfig;
import io.pravega.client.admin.StreamManager;
import io.pravega.client.admin.impl.StreamManagerImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.table.catalog.*;
import org.apache.flink.table.catalog.exceptions.*;
import org.apache.flink.table.catalog.stats.CatalogColumnStatistics;
import org.apache.flink.table.catalog.stats.CatalogTableStatistics;
import org.apache.flink.table.factories.TableFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.apache.flink.table.descriptors.ConnectorDescriptorValidator.CONNECTOR;

@Slf4j
public class PravegaCatalog extends AbstractCatalog {
    private StreamManager streamManager;
    private String controllerUri;
    private Map<String, String> properties;

    public PravegaCatalog(String controllerUri, String catalogName, Map<String, String> props, String defaultDatabase) {
        super(catalogName, defaultDatabase);

        this.controllerUri = controllerUri;
        this.properties = new HashMap<>();
        for (Map.Entry<String, String> kv : props.entrySet()) {
            properties.put(CONNECTOR + "." + kv.getKey(), kv.getValue());
        }

        log.info("Created Pravega Catalog {}", catalogName);
    }

    @Override
    public void open() throws CatalogException {
        if (streamManager == null) {
            try {
                streamManager = new StreamManagerImpl(ClientConfig.builder().build());
            } catch (Exception e) {
                throw new CatalogException("Failed to create streamManager");
            }
        }
    }

    @Override
    public void close() throws CatalogException {
        if(streamManager!=null) {
            streamManager.close();
            streamManager=null;
            log.info("Close connection to Pravega");
        }
    }

    @Override
    public List<String> listDatabases() throws CatalogException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<TableFactory> getTableFactory() {
        return Optional.empty();
    }

    @Override
    public CatalogDatabase getDatabase(String databaseName) throws DatabaseNotExistException, CatalogException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean databaseExists(String databaseName) throws CatalogException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void createDatabase(String name, CatalogDatabase database, boolean ignoreIfExists) throws DatabaseAlreadyExistException, CatalogException {
        try {
            streamManager.createScope(name);
        } catch (Exception e) {
            if (!ignoreIfExists) {
                throw new DatabaseAlreadyExistException(getName(), name, e);
            }
            else throw new CatalogException(String.format("Failed to create database %s", name), e);
        }
    }

    @Override
    public void dropDatabase(String name, boolean ignoreIfNotExists) throws DatabaseNotExistException, DatabaseNotEmptyException, CatalogException {
        try {
            streamManager.deleteScope(name);
        } catch (Exception e) {
            if (!ignoreIfNotExists) {
                throw new DatabaseNotExistException(getName(), name);
            }
            else throw new CatalogException(String.format("Failed to create database %s", name), e);
        }
    }

    @Override
    public void alterDatabase(String name, CatalogDatabase newDatabase, boolean ignoreIfNotExists) throws DatabaseNotExistException, CatalogException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> listTables(String databaseName) throws DatabaseNotExistException, CatalogException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> listViews(String databaseName) throws DatabaseNotExistException, CatalogException {
        throw new UnsupportedOperationException();
    }

    @Override
    public CatalogBaseTable getTable(ObjectPath tablePath) throws TableNotExistException, CatalogException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean tableExists(ObjectPath tablePath) throws CatalogException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void dropTable(ObjectPath tablePath, boolean ignoreIfNotExists) throws TableNotExistException, CatalogException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void renameTable(ObjectPath tablePath, String newTableName, boolean ignoreIfNotExists) throws TableNotExistException, TableAlreadyExistException, CatalogException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void createTable(ObjectPath tablePath, CatalogBaseTable table, boolean ignoreIfExists) throws TableAlreadyExistException, DatabaseNotExistException, CatalogException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void alterTable(ObjectPath tablePath, CatalogBaseTable newTable, boolean ignoreIfNotExists) throws TableNotExistException, CatalogException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<CatalogPartitionSpec> listPartitions(ObjectPath tablePath) throws TableNotExistException, TableNotPartitionedException, CatalogException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<CatalogPartitionSpec> listPartitions(ObjectPath tablePath, CatalogPartitionSpec partitionSpec) throws TableNotExistException, TableNotPartitionedException, CatalogException {
        throw new UnsupportedOperationException();
    }

    @Override
    public CatalogPartition getPartition(ObjectPath tablePath, CatalogPartitionSpec partitionSpec) throws PartitionNotExistException, CatalogException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean partitionExists(ObjectPath tablePath, CatalogPartitionSpec partitionSpec) throws CatalogException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void createPartition(ObjectPath tablePath, CatalogPartitionSpec partitionSpec, CatalogPartition partition, boolean ignoreIfExists) throws TableNotExistException, TableNotPartitionedException, PartitionSpecInvalidException, PartitionAlreadyExistsException, CatalogException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void dropPartition(ObjectPath tablePath, CatalogPartitionSpec partitionSpec, boolean ignoreIfNotExists) throws PartitionNotExistException, CatalogException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void alterPartition(ObjectPath tablePath, CatalogPartitionSpec partitionSpec, CatalogPartition newPartition, boolean ignoreIfNotExists) throws PartitionNotExistException, CatalogException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> listFunctions(String dbName) throws DatabaseNotExistException, CatalogException {
        throw new UnsupportedOperationException();
    }

    @Override
    public CatalogFunction getFunction(ObjectPath functionPath) throws FunctionNotExistException, CatalogException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean functionExists(ObjectPath functionPath) throws CatalogException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void createFunction(ObjectPath functionPath, CatalogFunction function, boolean ignoreIfExists) throws FunctionAlreadyExistException, DatabaseNotExistException, CatalogException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void alterFunction(ObjectPath functionPath, CatalogFunction newFunction, boolean ignoreIfNotExists) throws FunctionNotExistException, CatalogException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void dropFunction(ObjectPath functionPath, boolean ignoreIfNotExists) throws FunctionNotExistException, CatalogException {
        throw new UnsupportedOperationException();
    }

    @Override
    public CatalogTableStatistics getTableStatistics(ObjectPath tablePath) throws TableNotExistException, CatalogException {
        throw new UnsupportedOperationException();
    }

    @Override
    public CatalogColumnStatistics getTableColumnStatistics(ObjectPath tablePath) throws TableNotExistException, CatalogException {
        throw new UnsupportedOperationException();
    }

    @Override
    public CatalogTableStatistics getPartitionStatistics(ObjectPath tablePath, CatalogPartitionSpec partitionSpec) throws PartitionNotExistException, CatalogException {
        return null;
    }

    @Override
    public CatalogColumnStatistics getPartitionColumnStatistics(ObjectPath tablePath, CatalogPartitionSpec partitionSpec) throws PartitionNotExistException, CatalogException {
        return null;
    }

    @Override
    public void alterTableStatistics(ObjectPath tablePath, CatalogTableStatistics tableStatistics, boolean ignoreIfNotExists) throws TableNotExistException, CatalogException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void alterTableColumnStatistics(ObjectPath tablePath, CatalogColumnStatistics columnStatistics, boolean ignoreIfNotExists) throws TableNotExistException, CatalogException, TablePartitionedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void alterPartitionStatistics(ObjectPath tablePath, CatalogPartitionSpec partitionSpec, CatalogTableStatistics partitionStatistics, boolean ignoreIfNotExists) throws PartitionNotExistException, CatalogException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void alterPartitionColumnStatistics(ObjectPath tablePath, CatalogPartitionSpec partitionSpec, CatalogColumnStatistics columnStatistics, boolean ignoreIfNotExists) throws PartitionNotExistException, CatalogException {
        throw new UnsupportedOperationException();
    }
}
