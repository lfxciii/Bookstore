/*** 	Data Science, Algorithms and Advanced Software Engineering Task 7
 	Ruben Louw, [2019-06-16]
 	Singleton implementation class for low level queries against database.
***/

package BookStoreApp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Data {

	private static Data instance = null;
	static Connection conn = null;
	static Statement stmt = null;
	
	private Data(){
		
	}	
	
	/***
	 * Get singleton instance of object
	 * @return
	 * @throws Exception
	 */
	public static Data getInstance() throws Exception{
		
		if(instance == null){
			 try {
				 
				 instance = new Data();				 
				 // setup connection
				 // if database does not exist, this will connection will fail.
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebookstore?allowMultiQueries=true",
							"myuser", "nuhara");			
				
				// create statement object
				stmt = conn.createStatement();
							
			}catch(Exception ex){
				throw ex;
			}
		}
		return instance;
	}
	

	/***
	 * Handles create, update and delete
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	public int Cud(String query) throws SQLException{		
		int countInserted = stmt.executeUpdate(query);
		System.out.println(countInserted + " records affected\n");
		return countInserted;
	}

	/***
	 * Handles reads lists and singles
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	public ResultSet Select(String query) throws SQLException{		
		return stmt.executeQuery(query);
	}	
	
	
}
