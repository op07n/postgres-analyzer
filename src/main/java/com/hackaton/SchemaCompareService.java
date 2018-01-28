    package com.hackaton;

    import com.hackaton.dao.Column;
    import com.hackaton.dao.Row;
    import com.hackaton.dao.TableSchema;
    import com.hackaton.response.ColumnAction;
    import com.hackaton.response.ColumnSchemaChange;
    import com.hackaton.response.SchemaUpdateStatus;
    import com.hackaton.response.TableSchemaAnalysisResult;
    import org.apache.commons.text.RandomStringGenerator;
    import org.springframework.stereotype.Service;

    import java.util.*;
    import java.util.concurrent.ConcurrentHashMap;
    import java.util.stream.Collectors;

    import static org.apache.commons.text.CharacterPredicates.DIGITS;
    import static org.apache.commons.text.CharacterPredicates.LETTERS;

    @Service
    public class SchemaCompareService {

        private RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('0', 'z')
                .filteredBy(LETTERS, DIGITS).build();

        private ConcurrentHashMap<String, Integer> tableVersionMap = new ConcurrentHashMap<>();

        private ConcurrentHashMap<String, List<TableSchema>> gatheredSchemas = new ConcurrentHashMap<>();

        public int getAndIncCurrentTableVersion(String tableName) {//todo concurrent access
            Integer version = tableVersionMap.putIfAbsent(tableName, 0);
            if (version == null) {
                return 0;
            }
            version++;
            tableVersionMap.put(tableName, version);

            return version;
        }

        public String generateRandomID() {
            return generator.generate(5);
        }

        public boolean saveSchemas(String analysisId, List<TableSchema> schemas) {
            return gatheredSchemas.putIfAbsent(analysisId, schemas) == null;
        }

        public List<TableSchema> getSchemasForAnalysis(String anlaysisId) {
            List<TableSchema> gathered = gatheredSchemas.get(anlaysisId);
            if (gathered == null) {
                return Collections.emptyList();
            }

            return gathered;
        }

        public TableSchemaAnalysisResult performSchemaAnalysis(TableSchema oldSchema, TableSchema newSchema) {
            if (oldSchema.getVersion() > newSchema.getVersion()) {
                return new TableSchemaAnalysisResult(oldSchema.getVersion(), newSchema.getVersion(), oldSchema.getTableName(), SchemaUpdateStatus.VERSION_ERROR);
            }

            if (oldSchema.getVersion() == newSchema.getVersion()) {
                return new TableSchemaAnalysisResult(oldSchema.getVersion(), newSchema.getVersion(), oldSchema.getTableName(), SchemaUpdateStatus.NO_CHANGES);
            }

            List<ColumnSchemaChange> columnChanges = new ArrayList<>();
            Set<String> preservedColumnNames = new HashSet<>();

            List<Row> changedRows = new ArrayList<>();

            Map<String, Column> newColumnMap = newSchema.getColumns().stream().collect(Collectors.toMap(Column::getColumnName, c -> c));
            for (Column oldColumn: oldSchema.getColumns()) {
                 Column newColumn = newColumnMap.get(oldColumn.getColumnName());
                 if (newColumn == null) {
                     columnChanges.add(new ColumnSchemaChange(oldColumn.getColumnName(), ColumnAction.DELETED, oldColumn.getDataType()));
                 } else {
                     if (!newColumn.getDataType().equalsIgnoreCase(oldColumn.getDataType())) {
                         columnChanges.add(new ColumnSchemaChange(oldColumn.getColumnName(), ColumnAction.TYPE_CHANGED, oldColumn.getDataType(), newColumn.getDataType()));
                     }
                     preservedColumnNames.add(oldColumn.getColumnName());
                 }
            }
            List<Column> addedColumns = newSchema.getColumns().stream().filter(c -> !preservedColumnNames.contains(c.getColumnName())).collect(Collectors.toList());

            SchemaUpdateStatus schemaUpdateStatus = SchemaUpdateStatus.UPDATED;
            if (columnChanges.isEmpty()) {
                schemaUpdateStatus = SchemaUpdateStatus.NO_CHANGES;
            }

            return new TableSchemaAnalysisResult(oldSchema.getVersion(), newSchema.getVersion(),
                    oldSchema.getTableName(), schemaUpdateStatus, columnChanges);
        }

    }
