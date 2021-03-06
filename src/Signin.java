
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Signin {

	public Signin(Connection con, String date, String status, String trainname, int passengerscount, int tbill)
			throws SQLException {

		Scanner sc = new Scanner(System.in);

		int flagphonenum = 1;
		int flagpassword = 1;
		String phonenum = null;
		String password = null;

		while (flagphonenum == 1) {
			System.out.println("Enter you Phone number");

			phonenum = sc.nextLine();

			if (phonenum.equals("")) {
				System.out.println("Phone num Cannot Be Empty\n");
			} else {

				if (RegularExpression.phonenum(phonenum)) {
					flagphonenum = 0;
				}

				else {
					System.out.println("Invalid from input\n");
				}
			}
		}

		while (flagpassword == 1) {

			System.out.println("Enter your password");

			password = sc.nextLine();

			if (password.equals("")) {
				System.out.println("password Cannot Be Empty\n");
			} else {
				flagpassword = 0;

				int count = SqlQuery.CheckSignin(phonenum, password, con);
				con.commit();

				if (count == 1) {
					System.out.println("User, Found Access Granted!");

					GetEmail obj = new GetEmail();
					String email = obj.getemail(phonenum, password, con);

					PassengerDetails obj1 = new PassengerDetails(passengerscount, phonenum, trainname, date, status,
							email,tbill, con);

				//	Credits ob = new Credits(tbill, email, phonenum, con);

				} else if (count > 1) {
					System.out.println("Duplicate User, Access Denied!");
				} else {
					System.out.println("user doesn't exsist. ");
				}

			}
		}

	}

}
