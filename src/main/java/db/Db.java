package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.AulaDto;

public class Db {

	private static Db instance = null;
	private Connection connection = null;

	private String driver;
	private String url;
	private String user;
	private String password;

	private Db() {
		this.confDB();
		this.conectar();
		this.criarTabela();
	}

	public static Db getInstance() {
		if (instance == null) {
			instance = new Db();
		}
		return instance;
	}

	private void confDB() {
		try {
			this.driver = "org.h2.Driver";
			this.url = "jdbc:h2:~/test";
			this.user = "sa";
			this.password = "";
			Class.forName(this.driver);
		} catch (Exception e) {
			// TODO: o que fazer se algo deu errado
			e.printStackTrace();
		}
	}

	// Inicia a conexão com o banco de dados
	private void conectar() {
		try {
			this.connection = DriverManager.getConnection(this.url, this.user, this.password);
		} catch (Exception e) {
			// TODO: o que fazer se algo deu errado
		}
	}

	private void criarTabela() {
		String query = "CREATE TABLE AULA (" + "    ID BIGINT AUTO_INCREMENT PRIMARY KEY," + "    COD_DISCIPLINA INT,"
				+ "    ASSUNTO VARCHAR(255)," + "    DURACAO INT," + "    DATA VARCHAR(20)," + "    HORARIO VARCHAR(20)"
				+ ")";
		try {
			Statement statement = this.connection.createStatement();
			statement.executeUpdate(query);
			this.connection.commit();
		} catch (Exception e) {
			// TODO: o que fazer se algo deu errado
		}
	}

	// Encerra a conexão
	public void close() {
		try {
			this.connection.close();
		} catch (SQLException e) {
			// TODO: o que fazer se algo deu errado
		}
	}

	/*
	 * **************************************************************** CRUD
	 * ****************************************************************
	 */

	// CRUD READ
	public ArrayList<AulaDto> findAll() {
		String query = "SELECT ID, COD_DISCIPLINA, ASSUNTO, DURACAO, DATA, HORARIO FROM AULA;";
		ArrayList<AulaDto> lista = new ArrayList<>();
		/*
		 * Aqui você usa a query acima para obter todos os registros na tabela. Mas,
		 * lembre-se de que você precisa obter cada registro um por um a partir do
		 * result set.
		 */

		try (Statement stmt = this.connection.createStatement(); ResultSet resultSet = stmt.executeQuery(query)) {

			while (resultSet.next()) {
				AulaDto aula = new AulaDto();
				aula.id = Long.toString(resultSet.getLong("ID"));
				aula.codDisciplina = Integer.toString(resultSet.getInt("COD_DISCIPLINA"));
				aula.assunto = resultSet.getString("ASSUNTO");
				aula.duracao = resultSet.getString("DURACAO");
				aula.data = resultSet.getString("DATA");
				aula.horario = resultSet.getString("HORARIO");
				lista.add(aula);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	public AulaDto findById(String id) {
		String query = "SELECT ID, COD_DISCIPLINA, ASSUNTO, DURACAO, DATA, HORARIO FROM AULA " + "WHERE ID = ?";
		/*
		 * Use a query acima para encontrar o registro associado ao id fornecido mas
		 * considere a possibilidade do id não constar no banco. Uma dica. Em vez de
		 * construir inicialmente o DTO a partir do banco, crie um objeto Aula e depois
		 * crie um DTO usando a aula no construtor. Consulte a classe AulaDto para ver
		 * uma razão para isso. Há um atributo de AulaDto necessário para exibição no
		 * navegador que a classe Aula não tem.
		 */

		AulaDto aula = null;
		try (PreparedStatement pstmt = this.connection.prepareStatement(query)) {
			pstmt.setString(1, id);
			ResultSet resultSet = pstmt.executeQuery();

			if (resultSet.next()) {
				aula = new AulaDto();
				aula.id = Long.toString(resultSet.getLong("ID"));
				aula.codDisciplina = resultSet.getString("COD_DISCIPLINA");
				aula.assunto = resultSet.getString("ASSUNTO");
				aula.duracao = resultSet.getString("DURACAO");
				aula.data = resultSet.getString("DATA");
				aula.horario = resultSet.getString("HORARIO");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return aula;
	}

	// CRUD CREATAE
	public void create(AulaDto dto) {
		String query = "INSERT INTO AULA (COD_DISCIPLINA, ASSUNTO, DURACAO, DATA, HORARIO) " + "VALUES (?,?,?,?,?)";
		/*
		 * Crie um PreparedStatement que inclua todos os campos a serem registrados na
		 * tabela. Lembre-se de que a contagem dos parâmetros (?) começa em 1. Observe o
		 * método delete abaixo. Pode ser útil.
		 */

		try (PreparedStatement pstmt = this.connection.prepareStatement(query)) {
			pstmt.setString(1, dto.codDisciplina);
			pstmt.setString(2, dto.assunto);
			pstmt.setString(3, dto.duracao);
			pstmt.setString(4, dto.data);
			pstmt.setString(5, dto.horario);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// CRUD DELETE
	public void deleteAll() {
		String query = "DELETE FROM AULA";
		try {
			Statement st = this.connection.createStatement();
			st.execute(query);
		} catch (Exception e) {
			e.printStackTrace(); // L nesse metodo apenas adicionei o printStackTrace.
		}
	}

	// CRUD DELETE
	public void delete(String id) {
		String query = "DELETE FROM AULA WHERE ID = ?";
		try {
			PreparedStatement pstmt = this.connection.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.execute();
		} catch (Exception e) {
			e.printStackTrace(); // L nesse metodo apenas adicionei o printStackTrace.
		}
	}

	// CRUD UPDATE
	public void update(AulaDto dto) {
		String query = "UPDATE AULA SET " + "COD_DISCIPLINA = ?, ASSUNTO = ?, DURACAO = ?, DATA = ?, HORARIO = ? "
				+ "WHERE ID = ?";
		/*
		 * Use os atributos do DTO para atualizar o dado associado ao id. Use
		 * PreparedStatement, lembrando que a contagem dos parâmetros (?) começa em 1.
		 */

		try (PreparedStatement pstmt = this.connection.prepareStatement(query)) {
			pstmt.setString(1, dto.codDisciplina);
			pstmt.setString(2, dto.assunto);
			pstmt.setString(3, dto.duracao);
			pstmt.setString(4, dto.data);
			pstmt.setString(5, dto.horario);
			pstmt.setString(6, dto.id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * PARA EFEITO DE TESTES
	 */

	public void reset() {
		this.deleteAll();
		this.popularTabela();
	}

	public void popularTabela() {
		AulaDto dto = new AulaDto();

		dto.codDisciplina = "1";
		dto.assunto = "Derivadas";
		dto.duracao = "2";
		dto.data = "2024-04-12";
		dto.horario = "14:00";
		this.create(dto);

		dto.codDisciplina = "3";
		dto.assunto = "Coordenadas Cartesianas";
		dto.duracao = "2";
		dto.data = "2024-04-13";
		dto.horario = "14:00";
		this.create(dto);

		dto.codDisciplina = "4";
		dto.assunto = "O Problema dos Três Corpos";
		dto.duracao = "4";
		dto.data = "2024-04-14";
		dto.horario = "14:00";
		this.create(dto);
	}

}
