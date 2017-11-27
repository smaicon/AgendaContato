package com.codemaicon.agendacontato.database;

/**
 * Created by smaicon on 03/11/2017.
 */

public class ScriptSQL {
    public static String getCreateContato() {

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS CONTATO (\n" +
                "    _id                INTEGER       NOT NULL\n" +
                "                                     PRIMARY KEY AUTOINCREMENT,\n" +
                "    NOME              VARCHAR (100),\n" +
                "    TELEFONE           VARCHAR (14),\n" +
                "    TIPOTELEFONE       VARCHAR (1),\n" +
                "    EMAIL              VARCHAR (255),\n" +
                "    TIPOEMAIL          VARCHAR (1),\n" +
                "    ENDERECO           VARCHAR (255),\n" +
                "    TIPOENDERECO       VARCHAR (1),\n" +
                "    DATASESPECIAIS     DATE,\n" +
                "    TIPODATASESPECIAIS VARCHAR (1),\n" +
                "    GRUPOS             VARCHAR (100) \n" +
                ")");

        return sqlBuilder.toString();
    }

}
