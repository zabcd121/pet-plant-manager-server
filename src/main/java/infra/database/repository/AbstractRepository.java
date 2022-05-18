package infra.database.repository;

import infra.database.PooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class AbstractRepository<E> {
    private final DataSource ds = PooledDataSource.getDataSource();

    protected abstract E restoreObject(ResultSet rs) throws SQLException;
    protected abstract List<E> restoreList(ResultSet rs) throws SQLException;

    protected void executeUpdateOrDelete(QueryMaker queryMaker){
        PreparedStatement ps = null;

        try(Connection conn = ds.getConnection()){
            ps = queryMaker.make(conn);
            ps.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }finally{
            try{
                if(ps!=null){
                    ps.close();
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }

    protected E executeFindOne(QueryMaker queryMaker){
        PreparedStatement ps = null;
        ResultSet rs = null;
        E restoredObj = null;

        try(Connection conn = ds.getConnection()){
            ps = queryMaker.make(conn);
            rs = ps.executeQuery();
            restoredObj = restoreObject(rs);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }finally {
            try{
                if(ps!=null){
                    ps.close();
                }

                if(rs!=null){
                    rs.close();
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return restoredObj;
    }

    protected List<E> executeFindList(QueryMaker queryMaker){
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<E> restoredList = null;

        try(Connection conn = ds.getConnection()){
            ps = queryMaker.make(conn);
            rs = ps.executeQuery();
            restoredList = restoreList(rs);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }finally {
            try{
                if(ps!=null){
                    ps.close();
                }

                if(rs!=null){
                    rs.close();
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return restoredList;
    }


    protected long executeInsert(QueryMaker queryMaker){
        PreparedStatement ps = null;
        ResultSet rs = null;
        long id = -1;

        try(Connection conn = ds.getConnection()){
            ps = queryMaker.make(conn);
            rs = ps.getGeneratedKeys();
            id = rs.getLong(1);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }finally {
            try{
                if(ps!=null){
                    ps.close();
                }

                if(rs!=null){
                    rs.close();
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }

        return id;
    }
}