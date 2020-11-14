package com.asist.project;

import org.hibernate.boot.Metadata;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.EnumSet;

/**
 * createdAt: 11/14/2020
 * project: ProjectsPanel
 *
 * @author Alexander Petrushkin
 */
public class DatabaseInitializer {

    private final Metadata metadata;
    private final String scriptFile;

    public DatabaseInitializer(Metadata metadata) {
        this(metadata, "export_d_s_s.sql");
    }

    public DatabaseInitializer(Metadata metadata, String scriptFile) {
        this.metadata = metadata;
        this.scriptFile = scriptFile;
    }

    public void initialize() {
        SchemaExport export = getSchemaExport();
        dropDataBase(export, metadata);
        createDataBase(export, metadata);
    }

    private SchemaExport getSchemaExport() {
        SchemaExport export = new SchemaExport();
        File outputFile = new File(scriptFile);
        String outputFilePath = outputFile.getAbsolutePath();
        export.setDelimiter(";");
        export.setOutputFile(outputFilePath);
        export.setHaltOnError(false);
        return export;
    }

    private void dropDataBase(@NotNull SchemaExport export, Metadata metadata) {
        EnumSet<TargetType> targetTypes = EnumSet.of(TargetType.DATABASE, TargetType.SCRIPT, TargetType.STDOUT);
        export.drop(targetTypes, metadata);
    }

    private void createDataBase(@NotNull SchemaExport export, Metadata metadata) {
        EnumSet<TargetType> targetTypes = EnumSet.of(TargetType.DATABASE, TargetType.SCRIPT, TargetType.STDOUT);
        SchemaExport.Action action = SchemaExport.Action.CREATE;
        export.execute(targetTypes, action, metadata);
    }
}
