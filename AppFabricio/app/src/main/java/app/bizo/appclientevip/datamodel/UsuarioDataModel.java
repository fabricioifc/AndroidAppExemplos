package app.bizo.appclientevip.datamodel;

public class UsuarioDataModel implements DataModelListener {

    public static final String TABELA = "usuarios";

    public static final String ID = "id";
    public static final String NOME = "nome";
    public static final String EMAIL = "email";
    public static final String SENHA = "senha";
    public static final String ACEITOU_TERMOS_USO = "aceitou_termos_uso";

    @Override
    public String gerarTabela() {
        StringBuilder query = new StringBuilder("");
        query.append("CREATE TABLE IF NOT EXISTS " + TABELA).append("(\n");
        query.append(ID + " integer not null primary key AUTOINCREMENT").append(",\n");
        query.append(NOME + " TEXT not null").append(",\n");;
        query.append(EMAIL + " TEXT not null").append(",\n");;
        query.append(SENHA + " TEXT not null").append(",\n");;
        query.append(ACEITOU_TERMOS_USO + " boolean default true").append("\n");;
        query.append(");");

        return query.toString();
    }

    @Override
    public String excluirTabela() {
        StringBuilder query = new StringBuilder("");
        query.append("DROP TABLE IF EXISTS " + TABELA).append(";");

        return query.toString();
    }

}
