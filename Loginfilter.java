package crud;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;

import org.apache.catalina.servlet4preview.RequestDispatcher;



public class Loginfilter extends HttpFilter implements Filter {
       
   
    public Loginfilter() {
        super();
        
    }

	public void destroy() {
		
	}
	
	ResultSet rs;
	String us[] = new String[3];
	String ps[] = new String[3];
	int key[] = new int[3];
	int count=0;
	boolean ss;
	void conn() throws ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");
		Connection cn=DriverManager.getConnection("jdbc:postgresql://192.168.110.48:5432/plf_training","plf_training_admin","pff123");
		Statement s=cn.createStatement();
		rs=s.executeQuery("select * from validation");
		int i=0;
		count = 0;
		while(rs.next()) {
			System.out.println(i);
			us[i]=rs.getString(1);
			ps[i]=rs.getString(2);
			key[i]=rs.getInt(3);	
			i++;
			count++;
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String user=request.getParameter("username");
		String pass= request.getParameter("password");
		System.out.println(user);
		try {
			conn();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		boolean valid=doauthentication(user,pass);
		if(valid) {
			chain.doFilter(request, response);
		
			
	}
		else
	{
			RequestDispatcher rd=(RequestDispatcher) request.getRequestDispatcher("/login.html");
			rd.forward(request, response);
	}
}
	
	
	boolean doauthentication(String user, String pass) {
		for(int i=0;i<count-1;i++) {
			System.out.println(i);
		  ss= user.equals(us[i]) && pass.equals(ps[i]);
		 }return ss;
	 }
	
	
	public void init(FilterConfig fConfig) throws ServletException {		
	}

}
