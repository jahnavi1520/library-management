package library.repository;

import java.sql.Connection;

public class RepositoryFactory {
    public static IRepository getRepository(RepositoryType repositoryType, Connection conn) throws Exception {
        if (repositoryType == RepositoryType.InMemory) {
            return new InMemoryRepository();
        } else if (repositoryType == RepositoryType.Database) {
            return new DatabaseRepository(conn);
        }
        throw new Exception("no related repository found");
    }
}
