package Modelo.DAO;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
public interface DAOAbstracto <K, T> {
    public void add(T e) throws SQLException;

    public T findById(K id) throws SQLException;

    public List<T> findAll() throws SQLException;

    public void update(T e) throws SQLException;

    public void delete(K id) throws SQLException;
    public void exportAllToJson(File file) throws SQLException, IOException;
    public void importAllFromJson(File file) throws SQLException, IOException;
}