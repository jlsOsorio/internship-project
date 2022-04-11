package com.internship.retail_management.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.internship.retail_management.entities.CashRegister;
import com.internship.retail_management.entities.Iva;
import com.internship.retail_management.entities.Product;
import com.internship.retail_management.entities.Store;
import com.internship.retail_management.entities.enums.IvaValues;
import com.internship.retail_management.entities.enums.StoreStatus;
import com.internship.retail_management.repositories.CashRegisterRepository;
import com.internship.retail_management.repositories.IvaRepository;
import com.internship.retail_management.repositories.ProductRepository;
import com.internship.retail_management.repositories.StoreRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

	@Autowired //Injecta a dependência automaticamente com o framework
	private StoreRepository storeRepository;
	
	@Autowired
	private IvaRepository ivaRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CashRegisterRepository cashRegisterRepository;

	@Override
	public void run(String... args) throws Exception {
		
		Store s1 = new Store(null, "Rua de cima", "Porto", "1234-123", "221231212", StoreStatus.ACTIVE);
		Store s2 = new Store(null, "Rua de baixo", "Gaia", "4321-321", "223212121", StoreStatus.INACTIVE);
		
		storeRepository.saveAll(Arrays.asList(s1, s2));
		
		CashRegister c1 = new CashRegister(null, s1);
		CashRegister c2 = new CashRegister(null, s1);
		CashRegister c3 = new CashRegister(null, s2);
		CashRegister c4 = new CashRegister(null, s1);
		CashRegister c5 = new CashRegister(null, s2);
		
		cashRegisterRepository.saveAll(Arrays.asList(c1, c2, c3, c4, c5));
		
		Iva i1 = new Iva(null, IvaValues.NULL);
		Iva i2 = new Iva(null, IvaValues.LOW);
		Iva i3 = new Iva(null, IvaValues.INTERMEDIATE);
		Iva i4 = new Iva(null, IvaValues.NORMAL);
		
		ivaRepository.saveAll(Arrays.asList(i1, i2, i3, i4));
		
		Product p1 = new Product(null, "pao", 10, 0.50, i4);
		Product p2 = new Product(null, "agua", 50, 0.70, i3);
		Product p3 = new Product(null, "manteiga", 10, 0.40, i1);
		Product p4 = new Product(null, "cereais", 20, 1.50, i2);
		
		productRepository.saveAll(Arrays.asList(p1, p2, p3, p4));
		
	}
}
