package infra.database.repository;

import infra.database.PooledDataSource;
import infra.database.option.Option;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public abstract class AbstractRepository<E> {
    private final DataSource ds = PooledDataSource.getDataSource();

    protected abstract E restoreObject(ResultSet rs) throws SQLException;
    protected abstract List<E> restoreList(ResultSet rs) throws SQLException;

    protected void executeUpdateOrDelete(String sql, ParamsSetter paramsSetter){
        PreparedStatement ps = null;

        try(Connection conn = ds.getConnection()){
            ps = conn.prepareStatement(sql);
            paramsSetter.setParams(ps);
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

    protected E executeFindOne(String sql, ParamsSetter paramsSetter){
        PreparedStatement ps = null;
        ResultSet rs = null;
        E restoredObj = null;

        try(Connection conn = ds.getConnection()){
            ps = conn.prepareStatement(sql);
            paramsSetter.setParams(ps);
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

    protected List<E> executeFindList(String sql){
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<E> restoredList = null;

        try(Connection conn = ds.getConnection()){
            ps = conn.prepareStatement(sql);
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


    protected long executeInsert(String sql, ParamsSetter paramsSetter){
        PreparedStatement ps = null;
        ResultSet rs = null;
        long id = -1;

        try(Connection conn = ds.getConnection()){
            ps = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            );
            paramsSetter.setParams(ps);
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();

            while(rs.next()){
                id = rs.getLong(1);
            }
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