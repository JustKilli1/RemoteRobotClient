package database;

import java.sql.ResultSet;

public class DBHandler {

    private DBAccessLayer sql;

    public DBHandler(DBAccessLayer sql) {
        this.sql = sql;
    }

    public int getLastId() {
        ResultSet rs = sql.getLastId();
        if(rs == null) return 0;
        try {
            if(!rs.next()) return 0;
            return rs.getInt("id");
        } catch (Exception ex) {
            return 0;
        }
    }

}
