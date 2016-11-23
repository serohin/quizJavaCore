package com.kamazz.quiz.dao.datasource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class JNDIDatasource {
    private String datasourceContext;
    private String dataBaseName;
    private DataSource dataSource;


    public String getDatasourceContext() {
        return datasourceContext;
    }

    public void setDatasourceContext(String datasourceContext) {
        this.datasourceContext = datasourceContext;
    }

    public String getDataBaseName() {
        return dataBaseName;
    }

    public void setDataBaseName(String dataBaseName) {
        this.dataBaseName = dataBaseName;
    }

    public DataSource getDataSource() {
        if (null == dataSource) {
            setDataSource();
        }
        return dataSource;
    }

    private void setDataSource() {
        try {
            InitialContext initCtx = new InitialContext();
            Context envCont = (Context) initCtx.lookup(getDatasourceContext());
            this.dataSource = (DataSource) envCont.lookup(getDataBaseName());
        } catch (NamingException | NullPointerException e) {
            //log("Cannot get datasource" +e )
        }
    }


    /*private DataSource getJNDIDataSource(){
        DataSource ds = null;
        try {
            InitialContext initCtx = new InitialContext();
            Context envCont = (Context) initCtx.lookup(getDatasourceContext());
            ds = (DataSource) envCont.lookup(getDataBaseName());
        } catch (NamingException  e) {
            //log("Cannot get datasource" +e )
        }
        return ds;
    }

    private Connection getJNDIConnection() throws SQLException {
        Connection conn = null;
        DataSource ds = getDataSource();
        conn = ds.getConnection();
        return conn;
    }*/


}
