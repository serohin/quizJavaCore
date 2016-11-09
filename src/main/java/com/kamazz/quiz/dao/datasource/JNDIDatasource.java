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

    public JNDIDatasource() {
        try {
            InitialContext initCtx = new InitialContext();
            Context envCont = (Context) initCtx.lookup(getDatasourceContext());
            this.dataSource = (DataSource) envCont.lookup(getDataBaseName());
        } catch (NamingException  e) {
            //log("Cannot get connection" +e )
        }
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    /*private DataSource getJndiDataSource(){
        DataSource ds = null;
        try {
            InitialContext initCtx = new InitialContext();
            Context envCont = (Context) initCtx.lookup(getDatasourceContext());
            ds = (DataSource) envCont.lookup(getDataBaseName());
        } catch (NamingException  e) {
            //log("Cannot get connection" +e )
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
