package com.jdc.se.shop.views;

import java.util.function.Consumer;

import com.jdc.online.entity.Category;
import com.jdc.online.entity.Product;
import com.jdc.se.shop.MessageHandler;
import com.jdc.se.shop.ShopException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ProductAdd {
	
    @FXML
    private TextField name;

    @FXML
    private TextField price;
    
    private Category c;
    private Consumer<Product> saveHandler;


	public static void showView(Category c,Consumer<Product> consumer) {
		
		try {
			FXMLLoader loader = new FXMLLoader(ProductAdd.class.getResource("ProductAdd.fxml"));
			Parent root = loader.load();
			
			ProductAdd controller = loader.getController();
			controller.c = c;
			controller.saveHandler = consumer;
			
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void save() {
		
		try {

			if(name.getText().trim().isEmpty()) {
				throw new ShopException("Please enter product name.");
			}
			
			if(price.getText().trim().isEmpty()) {
				throw new ShopException("Please enter Price.");
			}
			
			try {
				Product p = new Product();
				p.setCategory_id(c.getId());
				p.setName(name.getText());
				p.setPrice(Integer.parseInt(price.getText().trim()));
				saveHandler.accept(p);
				name.getScene().getWindow().hide();
				
			} catch (NumberFormatException e) {
				throw new ShopException("Please enter price with digit.");
			}
			
		} catch (ShopException e) {
			MessageHandler.handle(e);
		}
	}
}
