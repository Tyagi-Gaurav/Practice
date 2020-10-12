package gt.practice.jdbc;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Blob;
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
            //sumPaymentsCallableStatement(conn, 2004);
            updateAllProductImagesAndStoreLocally(conn);
            getAllProductImagesAndStoreLocally(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateAllProductImagesAndStoreLocally(Connection conn) throws SQLException, IOException {
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT productLine, image FROM productlines",
                ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = preparedStatement.executeQuery();

        String[] carImages = new String[] {
                "https://images.unsplash.com/photo-1492144534655-ae79c964c9d7?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
                "https://images.unsplash.com/photo-1552519507-da3b142c6e3d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
                "https://images.unsplash.com/photo-1525609004556-c46c7d6cf023?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
                "https://images.unsplash.com/photo-1514316454349-750a7fd3da3a?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
                "https://images.unsplash.com/photo-1583121274602-3e2820c69888?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
                "https://images.unsplash.com/photo-1553440569-bcc63803a83d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"
        };
        int imageIndex = 0;

        while (resultSet.next()) {
            resultSet.updateBlob(2, getStreamFrom(carImages[imageIndex]));
            imageIndex  = (imageIndex + 1) % carImages.length;
            resultSet.updateRow();
        }

        resultSet.close();
    }

    private static void getAllProductImagesAndStoreLocally(Connection conn) throws SQLException, IOException {
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT productLine, image FROM productlines",
                ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String fileName = String.format("%s/%s.png", "/Users/gauravt/Downloads",resultSet.getString("productLine"));
            Blob image = resultSet.getBlob(2);
            InputStream binaryStream = image.getBinaryStream();
            byte[] b = new byte[1024];
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            int total;
            int size = 1024;
            while ((total = binaryStream.read(b,  0, size)) != -1) {
                byteArray.write(b, 0, total);
                int available = binaryStream.available();
                if (available < size) size = available;
            }

            FileOutputStream fileOutputStream = new FileOutputStream(new File(fileName));
            fileOutputStream.write(byteArray.toByteArray());
            fileOutputStream.flush();
            fileOutputStream.close();
            binaryStream.close();
        }

        resultSet.close();
    }

    public static InputStream getStreamFrom(String url) throws IOException {
        URL u = new URL(url);
        URLConnection urlConnection = u.openConnection();
        return urlConnection.getInputStream();
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
