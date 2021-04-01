package rs.edu.matgim.zadatak;

import java.sql.*;

public class DB {

    String connectionString = "jdbc:sqlite:src\\main\\java\\Banka.db";
    private Connection connection;

    public void printKomitent() {
        try ( Connection conn = DriverManager.getConnection(connectionString);  Statement s = conn.createStatement()) {

            ResultSet rs = s.executeQuery("SELECT * FROM Komitent");
            while (rs.next()) {
                int IdKom = rs.getInt("IdKom");
                String Naziv = rs.getString("Naziv");
                String Adresa = rs.getString("Adresa");

                System.out.println(String.format("%d\t%s\t%s", IdKom, Naziv, Adresa));
            }

        } catch (SQLException ex) {
          System.out.println("Greska prilikom povezivanja na bazu");
            System.out.println(ex);
        }
    
    }
     public void connect() throws SQLException
    {
        try{
        this.connection=DriverManager.getConnection(this.connectionString);
        }
        catch(SQLException e)
        {
            System.out.println("Greska1");
        }
    }
    public void printActiveRacun() throws SQLException {
         String x = "Select * From Racun Where Stanje = 'A'";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(x);
        while(rs.next())
        {
            int ID = rs.getInt(1);
            int RBr = rs.getInt(2);
            int iznos = rs.getInt(3);
            String Datum = rs.getString(4);
            String Vreme = rs.getString(5);
            
            System.out.println(ID+" "+RBr+" "+iznos+" "+Datum+" "+Vreme+"\n");
        }
    }
    public boolean zadatak(int idRacFrom, int idRacTo, float sum){
         try ( Connection conn = DriverManager.getConnection(connectionString);  Statement s = conn.createStatement()) {       String upit1="SELECT Stanje,DozvMinus FROM RACUN WHERE IdRac=idRacTo";
        String upit="Update Racun Set Stanje=Stanje+? Where IdRac=?";
       PreparedStatement ps=conn.prepareStatement(upit);
       conn.setAutoCommit(false);
       if(!(provera(idRacFrom,sum))) return false;
       ps.setDouble(1, sum);
       ps.setInt(2, idRacFrom);
       ps.executeUpdate();
       PreparedStatement ps2=conn.prepareStatement(upit);
       ps2.setDouble(1, -sum);
       ps2.setInt(2,idRacTo);
       ps2.execute();
       


       conn.commit();
       conn.setAutoCommit(true);
       return true;
         } catch (SQLException ex) {
             System.out.println("Greska!!!");
            System.out.println(ex);
            return false;}
       
          
        
        
     
    }
    }


