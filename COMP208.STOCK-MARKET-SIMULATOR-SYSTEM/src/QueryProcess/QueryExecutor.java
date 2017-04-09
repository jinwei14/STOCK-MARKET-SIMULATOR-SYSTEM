package QueryProcess;

import java.sql.SQLException;

import Database.Database;
import Main.queryProcessor;

public class QueryExecutor implements queryProcessor{
	
	private static int userId = 0;
	
	private Database executor;
	
	public QueryExecutor(){
		executor = new Database();
	}
	
	public boolean addPersonalInformation(String username, String password, String discription) {
		userId ++;
		String sql = "insert into User values ('"+userId+"','"+username+"','"+password+
						"','0'"+discription+"')";
		try {
			executor.insert(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean changePersonalInformation() {
		return false;
	}

	@Override
	public boolean viewPersonalInformation() {
		return false;
	}

	@Override
	public boolean deletePersonalInformation() {
		return false;
	}

	public String translateQueries(String o) {
		return null;
	}

	@Override
	public void passQuery() {
		
	}

}
