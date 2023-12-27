/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public int cadastrarProduto (ProdutosDTO produto){
        int status;
        conn = new conectaDAO().connectDB();
        try {
            prep = conn.prepareStatement("INSERT INTO produtos VALUES(?,?,?,?)");
            prep.setString(1,null);
            prep.setString(2,produto.getNome());
            prep.setInt(3,produto.getValor());
            prep.setString(4,produto.getStatus());
            status = prep.executeUpdate();
            return status;
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return ex.getErrorCode();
        }
        
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){
        String sql = "SELECT * FROM produtos"; 
        conn = new conectaDAO().connectDB();
        try {
            prep = conn.prepareStatement(sql);            
            resultset = prep.executeQuery();
            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();        
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));
                listagem.add(produto);
            }
            return listagem;
        }        
        catch (Exception e) {
            return null;
        }        
    }
    
    public ProdutosDTO getProduto (int id){
        conn = new conectaDAO().connectDB();
        String sql = "SELECT * FROM produtos WHERE id = ?";
        try {
            prep = this.conn.prepareStatement(sql);
            prep.setInt(1, id);
            resultset = prep.executeQuery();
            ProdutosDTO produto = new ProdutosDTO();
            resultset.next();
            produto.setId(id);
            produto.setNome(resultset.getString("nome"));
            produto.setValor(resultset.getInt("valor"));
            produto.setStatus(resultset.getString("status"));
            return produto;
        } catch (Exception e) {
            System.out.println("erro: " + e.getMessage());
            return null;
        }
    }
    
    public void venderProduto (int id, String status){       
        ProdutosDTO produto = getProduto(id);
        conn = new conectaDAO().connectDB();
        String sql = "UPDATE produtos SET nome=?, valor=?, status=? WHERE id=?";
        try {            
            prep = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);            
            prep.setInt(4, id);            
            prep.setString(1,produto.getNome());
            prep.setInt(2,produto.getValor());
            prep.setString(3, status); 
            prep.execute();            
        } catch (Exception e) {
        System.out.println("Erro ao editar a tabela produtos: " + e.getMessage());
        }
    }
    
    public List<ProdutosDTO> getListaProdutos(String status ){ 
        conn = new conectaDAO().connectDB();
        String sql = "SELECT * FROM produtos WHERE status LIKE ?"; 
        try {
            prep = this.conn.prepareStatement(sql);
            prep.setString(1,"%" + status + "%");
            resultset = prep.executeQuery();
            List<ProdutosDTO> listaProdutos = new ArrayList<>();
            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();        
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));
                listaProdutos.add(produto);
            }
            return listaProdutos;
        }        
        catch (Exception e) {
            return null;
        }
    }
        
}

