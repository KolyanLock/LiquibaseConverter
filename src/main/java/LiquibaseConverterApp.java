import liquibase.changelog.ChangeLogParameters;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.exception.LiquibaseException;
import liquibase.parser.ChangeLogParser;
import liquibase.parser.ChangeLogParserFactory;
import liquibase.resource.FileSystemResourceAccessor;
import liquibase.resource.ResourceAccessor;
import liquibase.serializer.ChangeLogSerializer;
import liquibase.serializer.ChangeLogSerializerFactory;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LiquibaseConverterApp {
    public static void main(String[] args) throws LiquibaseException {
        File sourcesDir = new File("C:\\Users\\kolya\\IdeaProjects\\LiquibaseConverter"); // path to sources dir
        ResourceAccessor resourceAccessor = new FileSystemResourceAccessor(sourcesDir);

        // It does not work with SQL format :(
        String inputChangeLog = "src/main/resources/sql/example-1.0.h2.sql"; // path to input ChangeLog File
        String  outputChangeLog = "src/main/resources/xml/example-1.0.xml"; //path to output ChangeLog File

        ChangeLogParserFactory parserFactory = ChangeLogParserFactory.getInstance();
        ChangeLogParser parser = parserFactory.getParser(inputChangeLog, resourceAccessor);
        DatabaseChangeLog changeLog = parser.parse(inputChangeLog, new ChangeLogParameters(), resourceAccessor);
        ChangeLogSerializer serializer = ChangeLogSerializerFactory.getInstance()
                .getSerializer(outputChangeLog);
        try (OutputStream outputStream = Files.newOutputStream(Paths.get(outputChangeLog))) {
            serializer.write(changeLog.getChangeSets(), outputStream);
        } catch (IOException e) {
            throw new RuntimeException("Unable to write output file ", e);
        }
    }
}
