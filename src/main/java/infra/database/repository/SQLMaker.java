package infra.database.repository;

import infra.database.option.Option;

public class SQLMaker {
    public static String makeSelectSql(String tableName, Option...options){
        String sql = String.format("SELECT * FROM %s", tableName);

        return makeOptionSQL(sql, options);
    }

    public static String makeInsertSql(String tableName, String ...colNames){
        StringBuilder sql = new StringBuilder("INSERT INTO ");

        sql.append(tableName);

        sql.append(" (");
        for(int i=0; i<colNames.length; i++){
            sql.append(colNames[i]);

            if(i!= colNames.length-1){
                sql.append(", ");
            }
        }
        sql.append(") ");

        sql.append(" VALUES(");
        for(int i=0; i<colNames.length; i++){
            sql.append("?");

            if(i!= colNames.length-1){
                sql.append(", ");
            }
        }
        sql.append(") ");

        System.out.println("sql = " + sql);
        return sql.toString();
    }


    public static String makeUpdateSql(String tableName, String pkColName, String ...colNames){
        StringBuilder sql = new StringBuilder("UPDATE ");
        sql.append(tableName);
        sql.append(" SET ");

        for(int i=0; i< colNames.length; i++){
            sql.append(colNames[i]);
            sql.append("=?");

            if(i!= colNames.length-1){
                sql.append(", ");
            }
        }

        sql.append(" WHERE ");
        sql.append(pkColName);
        sql.append("=?");

        return sql.toString();
    }


    public static String makeDeleteSql(String tableName, String pkColName){
        return String.format("DELETE FROM %s WHERE %s=?", tableName, pkColName);
    }

    public static String makeDeleteAllSql(String tableName){
        return String.format("DELETE FROM %s", tableName);
    }

    private static String makeOptionSQL(String sql, Option...options){
        String and = " AND ";
        String where = " WHERE ";
        StringBuilder query = new StringBuilder(
                sql
        );

        for(int i=0; i<options.length; i++){
            if(i==0){
                query.append(where);
            }

            query.append(options[i].getQuery());

            if(i!=options.length-1){
                query.append(and);
            }
        }

        return query.toString();
    }

}
