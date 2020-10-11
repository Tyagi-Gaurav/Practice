package gt.practice.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class ScrollablePractice {

    public static void main(String[] args) {
        String url = "jdbc:mysql://(host=localhost,port=3306,user=cbuser,password=cbpass)/classicmodels";
        String driver = "com.mysql.cj.jdbc.Driver";
        try {
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (Connection conn = DriverManager.getConnection(url)) {
            //printCustomerForward(conn);
            //printCustomerScrollingFromEnd(conn);
            //sumPaymentsPreparedStatement(conn, 2004);
            sumPaymentsCallableStatement(conn, 2004);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sumPaymentsCallableStatement(Connection conn, int year) throws SQLException {
        CallableStatement callableStatement = conn.prepareCall("{CALL classicModels.sumPaymentsByYear(?,?)}");
        callableStatement.setInt(1, year);
        callableStatement.registerOutParameter(2, Types.DOUBLE);
        boolean execute = callableStatement.execute();
        System.out.println(execute);
        if (execute) {
            ResultSet resultSet = callableStatement.getResultSet();
            resultSet.next();
            System.out.println("Result: " + resultSet.getDouble(1));
        }
        else {
            System.out.println("Update Count: " + callableStatement.getUpdateCount());
        }

        callableStatement.close();;
    }

    private static void sumPaymentsPreparedStatement(Connection conn, int year) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("select sum(amount) from payments where year(paymentDate) = ?");
        preparedStatement.setInt(1, year);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            System.out.println("Total sum of payments: " + resultSet.getBigDecimal(1));
        }
        resultSet.close();
        preparedStatement.close();
    }

    private static void printCustomerScrollingFromEnd(Connection conn) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("Select * from customers order by customerNumber",
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.afterLast();
        while (resultSet.previous()) {
            printCustomerData(resultSet);
        }
        resultSet.close();
        preparedStatement.close();
    }

    private static void printCustomerForward(Connection conn) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("Select * from customers order by customerNumber");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            printCustomerData(resultSet);
        }
        resultSet.close();
        preparedStatement.close();
    }

    private static void printCustomerData(ResultSet resultSet) throws SQLException {
        String stringBuilder = "CustomerNumber: " + resultSet.getInt("customerNumber") + "\n" +
                "Customer Name: " + resultSet.getString("customerName") + "\n" +
                "Customer Last Name: " + resultSet.getString("contactLastName") + "\n" +
                "Customer First Name: " + resultSet.getString("contactFirstName") + "\n" +
                "Phone: " + resultSet.getString("phone") + "\n" +
                "Address Line 1: " + resultSet.getString("addressLine1") + "\n" +
                "Address Line 2: " + resultSet.getString("addressLine2") + "\n" +
                "City: " + resultSet.getString("city") + "\n" +
                "State: " + resultSet.getString("state") + "\n" +
                "PostalCode: " + resultSet.getString("postalCode") + "\n" +
                "Country: " + resultSet.getString("country") + "\n" +
                "Sales Rep Employee Number: " + resultSet.getString("salesRepEmployeeNumber") + "\n" +
                "Credit Limit: " + resultSet.getString("creditLimit") + "\n";
        System.out.println(stringBuilder);
    }
}
