package login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe responsável por operações simples de autenticação de usuário.
 * Ajustada para rodar com WAMP (MySQL).
 */
public class user {

    // Configuração do banco (ajuste aqui se necessário)
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/test?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DB_USER = "root";      // padrão WAMP
    private static final String DB_PASS = "";          // padrão WAMP costuma ser "" (vazio)

    // Nome do usuário recuperado do banco (encapsulado)
    private String nome = "";

    /**
     * Tenta criar e retornar uma Connection com o banco de dados.
     * @return Connection ativa ou null em caso de falha
     */
    public Connection conectarBD() {
        final String jdbcDriver = "com.mysql.cj.jdbc.Driver";
        try {
            // Carrega o driver (normalmente desnecessário em JDBC 4+, mas mantido por compatibilidade)
            Class.forName(jdbcDriver);
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC não encontrado: " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Verifica se o usuário + senha existem no banco.
     *
     * @param login usuário (não nulo)
     * @param senha senha (não nula)
     * @return true se encontrado, false caso contrário
     */
    public boolean verificarUsuario(String login, String senha) {
        if (login == null || senha == null) {
            System.err.println("login ou senha nulos fornecidos.");
            return false;
        }

        boolean result = false;
        final String sql = "SELECT nome FROM usuarios WHERE login = ? AND senha = ?";

        try (Connection conn = conectarBD()) {
            if (conn == null) {
                return false;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, login);
                ps.setString(2, senha);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        result = true;
                        this.nome = rs.getString("nome");
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro durante verificação de usuário: " + e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Método de teste simples para executar pela linha de comando.
     * Exemplo: java login.User
     */
    public static void main(String[] args) {
        user user = new user();

        // teste: substituir por dados reais
        String loginTeste = "admin";
        String senhaTeste = "123";

        boolean ok = user.verificarUsuario(loginTeste, senhaTeste);
        if (ok) {
            System.out.println("Autenticado! Nome: " + user.getNome());
        } else {
            System.out.println("Usuário/senha inválidos.");
        }
    }
}
