package crypto;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


@Controller
@RequestMapping("/user")

//RSA 복호화후 SHA 암호화 시켜서 DB정보와 일치하는지 확인
public class UserController {
	@RequestMapping(value = "login",method = RequestMethod.POST)
    public String login(@RequestParam("password") String password,@RequestParam("id") String id, Model model, HttpServletRequest httpServletRequest){

        try {
            byte[] encryptPassword  = RSACoder.decryptByPrivateKey(Base64Utils.decodeFromString(password),Base64Utils.decodeFromString((String) httpServletRequest.getServletContext().getAttribute("priKey")));
            model.addAttribute("encryptPassword",password);
            model.addAttribute("password", new String(encryptPassword,"UTF-8"));
            Integer confirm=db_confirm(id,encryptPassword);
            model.addAttribute("confirm",confirm);
            
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "/result.jsp";
    }
	public static String hash(byte[] pw) {
		SHA sha256 = new SHA();
		byte[] arr = sha256.hash(pw);
		String result="";
		for(int i=0; i<arr.length; i++){
            result+=Integer.toString(arr[i] & 0xFF, 16);
        }
		return result;
	}
	public static Integer db_confirm(String id,byte[] pw) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		String server = "localhost:3306"; // MySQL 서버 주소
		String database = "test"; // MySQL DATABASE 이름
		String user_name = "root"; // MySQL 서버 아이디
		String password = "crypto13"; // MySQL 서버 비밀번호
		
		Integer confirm=0;

		// 1.드라이버 로딩
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

		} catch (ClassNotFoundException e) {
			System.err.println(" !! <JDBC 오류> Driver load 오류: " + e.getMessage());
			e.printStackTrace();
		}

		// 2.연결
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + server + "/" + database + "?serverTimezone=UTC&useSSL=false", user_name,password);
			System.out.println("정상적으로 연결되었습니다.");

		} catch (SQLException e) {
			System.err.println("con 오류:" + e.getMessage());
			e.printStackTrace();
		}
		try {
			String pass=hash(pw);
			String query = "SELECT Count(*) FROM user WHERE user_id=\"" + id + "\" AND user_password=\"" + pass+"\"";
			stmt = con.createStatement();
			rs = stmt.executeQuery(query.toString());
			
			while(rs.next()) {
				//JSONObject json=new JSONObject();
				if(rs.getInt(1)==1) {
					confirm=1;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 3.해제
		try {
			if (con != null)
				con.close();
		} catch (SQLException e) {
		}
		
		return confirm;
	}
}
